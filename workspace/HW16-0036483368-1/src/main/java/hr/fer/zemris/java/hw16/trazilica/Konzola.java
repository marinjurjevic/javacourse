package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.java.hw16.model.DocVector;

/**
 * Main program for simulation of a simple search engine.
 * @author Marin Jurjevic
 *
 */
public class Konzola {
	
	/**
	 * Path to stop words file.
	 */
	private static final String STOP_WORDS = "stoprijeci.txt";
	
	/**
	 * Set containing all stop words extracted from provided file.
	 */
	private static Set<String> stopWords;
	
	/**
	 * Map containing whole dictionary, dictionary word is key, and it's index in vector representation is value.
	 */
	private static Map<String, Integer> dictionary = new HashMap<>();
	
	/**
	 * Map containning all idf components for all words in dictionary
	 */
	private static Map<String, Double> idfComp = new HashMap<>();
	
	/**
	 * Map containing all documents, it is used in booting phase.
	 */
	private static Map<Path, List<String>> documents = new HashMap<>();
	
	/**
	 * Document vectors are representation of documents.
	 */
	private static List<DocVector> vectors = new LinkedList<>();
	
	/**
	 * List of vectors last executed query generated.
	 */
	private static List<DocVector> results = new LinkedList<>();
	
	/**
	 * Number of documents found.
	 */
	private static int numberOfDocuments;
	
	/**
	 * counter for words index in vector components.
	 */
	private static int index;
	
	/**
	 * Entry point of program. 
	 * @param args path to folder containing all documents used in this search engine.
	 */
	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide path to documents.");
			return;
		}else{
			if(!Files.isDirectory(Paths.get(args[0]))){
				System.out.println("You have to provide path to the folder containing documents.");
				return;
			}
		}
		
		loadStopWords();
		try{
			loadDictionary(Paths.get(args[0]));
			calculateVectors();
		}catch(IOException e){
			
		}
		System.out.println("Dictionary size: " + dictionary.size());
		
		start();
	}
	
	/**
	 * Starts query engine. At this point all vectors are initialized and application is ready to process commands.
	 */
	private static void start(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("\nEnter command> ");
			String entry = sc.nextLine();
			if(entry.trim().equalsIgnoreCase("exit")){
				break;
			}
			
			if(entry.trim().isEmpty()){
				System.out.println("Please write command!");
				continue;
			}
			List<String> parsed = parseUserEntry(entry);
			
			String cmd = parsed.get(0);
			parsed.remove(0);
			if(cmd.equals("query")){
				if(parsed.isEmpty()){
					System.out.println("No such words in dictionary.");
					continue;
				}
				executeQuery(parsed);
				printResults();
			}else if(cmd.equals("type")){
				if(parsed.isEmpty()){
					System.out.println("Please specify type.");
					continue;
				}
				int index = 0;
				try{
					index = Integer.parseInt(parsed.get(0));
				}catch(NumberFormatException e){
					System.out.println("Type must be a number");
					continue;
				}
				if(index < 0 || index > results.size() -1){
					System.out.println("Invalid index. Please see results for available types.");
					continue;
				}
				
				printDocument(results.get(index).getDocumentPath());
				
			}else if(cmd.equals("results")){
				printResults();
			}else{
				System.out.println("Uknown command!");
			}
		}
		
		sc.close();
	}
	
	/**
	 * Parses user entry and returns parsed content.
	 * @param entry user entry from standard input
	 * @return list of processed words
	 */
	private static List<String> parseUserEntry(String entry){
		List<String> content = new LinkedList<>();
		String[] words = entry.split(" ");
		
		content.add(words[0]);
		
		
		if (words[0].equalsIgnoreCase("query")) {
			for (int i = 1; i < words.length; i++) {
				String word = words[i].trim().toLowerCase();
				if (dictionary.containsKey(word)) {
					content.add(word);
				}
			}
		}else{
			if(words.length>1){
				content.add(words[1]);
			}
		}
		
		return content;
	}
	
	/**
	 * Loads all stop words from predefined text file.
	 */
	private static void loadStopWords(){
		stopWords = new HashSet<>();
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream( new FileInputStream(STOP_WORDS)),"UTF-8"))){
			
			String line;
			while((line = br.readLine()) != null){
				stopWords.add(line);
			}
		}catch (IOException e) {
		}
	}
	
	/**
	 * Loads all words from all documents in specified parent folder into the dictionary. Dictionary uses map as implementation, keeping
	 * words as key and their index as value. Index represents index in vector representation of documents.
	 * @param root parent folder of all documents to be used in this query engine.
	 * @throws IOException thrown if an error occurs while reading documents
	 */
	private static void loadDictionary(Path root) throws IOException{
		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				numberOfDocuments++;
				
				List<String> text = Files.readAllLines(file);
				List<String> words = new LinkedList<>();
				
				for(String line : text){
					char[] chars = line.toCharArray();
					String word = "";
					
					for(int i = 0; i<chars.length; i++){
						if(Character.isLetter(chars[i])){
							word+=chars[i];
						}else{
							word = word.toLowerCase();
							if(!word.isEmpty() && !stopWords.contains(word)){
								words.add(word);								
							}
							word="";
						}
					}
					
					// last word
					word = word.toLowerCase();
					if(!word.isEmpty() && !stopWords.contains(word)){
						words.add(word);
					}
				}
				
				documents.put(file.toAbsolutePath(), words);
				
				for(String word : words){
					if(!dictionary.containsKey(word)){
						dictionary.put(word, index++);
					}
				}

				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	/**
	 * Process all documents and calculates their vector representations.
	 */
	private static void calculateVectors(){
		for(Map.Entry<Path, List<String>> doc1 :   documents.entrySet()){
			DocVector vector = new DocVector(doc1.getKey(), dictionary.size());
			
			// tf component
			for(String w1 : doc1.getValue()){
				int counter = 0;
				for(String w2 : doc1.getValue()){
					if(w1.equals(w2)){
						counter++;
					}
				}
				
				// idf component
				int otherDoc = 1;
				for(Map.Entry<Path, List<String>> doc2 :   documents.entrySet()){
					if(doc1.getKey() == doc2.getKey()){
						continue;
					}
					
					if(doc2.getValue().contains(w1)){
						otherDoc++;
					}
					
				}
				
				idfComp.put(w1, Math.log(numberOfDocuments/otherDoc));
				
				vector.setFactor(dictionary.get(w1), counter*Math.log(numberOfDocuments/otherDoc));
			}
			
			vectors.add(vector);
		}
		
		// garbage
		documents = null;
	}
	
	/**
	 * Executes query which user requested on standard input.
	 * @param words keywords used in this query.
	 */
	private static void executeQuery(List<String> words){
		DocVector queryVector = new DocVector(null, dictionary.size());
		
		System.out.print("Query is: [");
		boolean first = true;
		for(String w : words){
			if(first){
				first = false;
				System.out.print(w);
			}else{
				System.out.print(", " + w);
			}
			
			queryVector.setFactor(dictionary.get(w), idfComp.get(w));
		}
		System.out.println("]");
		
		for(DocVector v : vectors){
			double sim = DocVector.scalarProduct(v, queryVector)/ (v.getMagnitude()*queryVector.getMagnitude());
			if(sim!=0){
				v.setQuerySim(sim);
				results.add(v);
			}
		}
		Collections.sort(results);
	}
	
	/**
	 * Prints results based on last executed query by user.
	 */
	private static void printResults(){
		System.out.println("Best " + results.size() + " results:");
		int i = 0;
		for(DocVector v : results){
			System.out.printf("[%2d](%.4f) %s%n", i++, v.querySim(), v.getDocumentPath() );
		}
	}
	
	/**
	 * Prints specified document from drive.
	 * @param file path to the document file 
	 */
	private static void printDocument(Path file){
		try {
			List<String> text = Files.readAllLines(file);
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("Document: " + file);
			System.out.println("-----------------------------------------------------------------------------------\n");
			for(String line : text){
				System.out.println(line);
			}
			System.out.println("-----------------------------------------------------------------------------------\n");
		} catch (IOException e) {
		}
	}
}
