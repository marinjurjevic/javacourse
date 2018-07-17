package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashTable;
import hr.fer.zemris.java.tecaj.hw5.utilities.IFilter;

/**
 * Student database class which keeps track of all existing student records.
 * 
 * @author Marin Jurjevic
 *
 */
public class StudentDatabase {

	/**
	 * private list of student records
	 */
	private List<StudentRecord> studentRecords;

	/**
	 * private index of student records for fast retrieval
	 */
	private SimpleHashTable<String, StudentRecord> index;

	/**
	 * Creates new student database by retrieving info from list of strings as
	 * existing data.
	 * 
	 * @param data
	 *            info about students
	 * @throws IllegalArgumentException
	 *             if data is null
	 */
	public StudentDatabase(List<String> data) {
		if (data == null) {
			throw new IllegalArgumentException("Data can not be null");
		}

		studentRecords = new LinkedList<StudentRecord>();
		index = new SimpleHashTable<>();

		for (String line : data) {
			StudentRecord sr = processData(line);
			studentRecords.add(sr);
			index.put(sr.getJmbag(), sr);
		}
	}

	/**
	 * Returns corresponding student record. If record does not exists, null is
	 * returned.
	 * 
	 * @param jmbag
	 *            jmbag of searched student record
	 * @return student record in database under this jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Filters records in the database by given filter function. All filtered
	 * student records are returned in new list.
	 * 
	 * @param filter
	 *            filter function to filter records
	 * @return list of filtered student records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filtered = new LinkedList<>();

		for (StudentRecord sr : studentRecords) {
			if (filter.accepts(sr)) {
				filtered.add(sr);
			}
		}

		return filtered;
	}

	/**
	 * Process one line of data and creates new student record.
	 * 
	 * @param line
	 *            one line of data that consists student info
	 * @return new instance of <code>StudentRecord</code>
	 */
	private static StudentRecord processData(String line) {
		String[] args = line.split("\t");

		String jmbag = args[0];
		String lastName = args[1];
		String firstName = args[2];
		short grade = Short.parseShort(args[3]);

		return new StudentRecord(jmbag, lastName, firstName, grade);
	}
}
