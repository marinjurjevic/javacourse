package hr.fer.zemris.java.tecaj.hw5.utilities;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Filters student records with specified condition.
 * 
 * @author Marin Jurjevic
 *
 */
public interface IFilter {

	/**
	 * Evaluates given student record.
	 * 
	 * @param record
	 *            student record to evaluate
	 * @return true if condition is satisfied, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}