package hr.fer.zemris.java.tecaj.hw6;

import java.io.File;

public class Lister {

	public static void main(String[] args) {
		File root = new File(args[0]);
		//Lister(root,0);
		ispisiStablo(root,0);
	}
	
	private static void ispisiStablo(File dir, int level){
		ispisi(dir,level);
		
		File[] djeca = dir.listFiles();
		if(djeca == null){
			return; // preskacem
		}
		
		for(File file: djeca){
			if(file.isFile()){
				ispisi(file, level+1);
			}else if(file.isDirectory()){
				ispisiStablo(file, level + 1);
			}
		}
	}
	
	private static void ispisi(File file, int level){
		if (level == 0){
			System.out.println(file.getAbsolutePath());
		}else{
			System.out.printf("%" + (2*level) + "s%s%n","", file.getName());
		}
	}
}
