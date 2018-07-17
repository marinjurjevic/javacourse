package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstration program for peforming various operations using Stream API.
 * 
 * @author Marin Jurjevic
 *
 */
public class StudentDemo {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = parseLines(lines);

		long broj = records.stream()
				.filter(r -> r.getMidTermExamPoints() + r.getFinalExamPoints() + r.getPracticePoints() > 25).count();
		System.out.println("Broj studenata s brojem ukupnih bodova vecim od 25: " + broj);

		long broj5 = records.stream().filter(r -> r.getGrade() == 5).count();
		System.out.println("Broj odlikaša: " + broj5);

		List<StudentRecord> odlikasi = records.stream().filter(r -> r.getGrade() == 5).collect(Collectors.toList());
		 printResults(odlikasi);

		List<StudentRecord> odlikasiSortirano = records.stream().filter(r -> r.getGrade() == 5)
				.sorted(StudentRecord.BY_ALL_POINTS.reversed()).collect(Collectors.toList());
		// printResults(odlikasiSortirano);

		List<String> nepolozeniJMBAGovi = records.stream().filter(r -> r.getGrade() < 2)
				.sorted((r1, r2) -> -(r1.getJmbag().compareTo(r2.getJmbag()))).map(r -> r.getJmbag())
				.collect(Collectors.toList());
		// printResults(nepolozeniJMBAGovi);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(r -> r.getGrade()));

		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, r -> 1, Integer::sum));
		// printResults(mapaPoOcjenama2.entrySet());

		Map<Boolean, List<StudentRecord>> passingFailing = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getGrade() > 1));

	}

	/**
	 * Helper method to parse lines to instance of student records
	 * 
	 * @param lines
	 *            lines to be parsed
	 * @return list of student records object
	 */
	private static List<StudentRecord> parseLines(List<String> lines) {
		List<StudentRecord> records = new LinkedList<>();

		for (String line : lines) {
			String[] s = line.split("\t");
			records.add(new StudentRecord(s[0], s[1], s[2], Double.parseDouble(s[3]), Double.parseDouble(s[4]),
					Double.parseDouble(s[5]), Integer.parseInt(s[6])));
		}

		return records;
	}

	/**
	 * Goes through given list of results and prints out each result.
	 * 
	 * @param results
	 *            instance of Iterable
	 */
	private static <T> void printResults(Iterable<T> results) {
		for (T r : results) {
			System.out.println(r.toString());
		}
	}
}
