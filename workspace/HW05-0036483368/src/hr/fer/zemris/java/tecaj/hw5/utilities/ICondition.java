package hr.fer.zemris.java.tecaj.hw5.utilities;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * ICondition is functional interface that enables records to be compared under
 * given set of rules.
 * 
 * @author Marin Jurjevic
 *
 */

@FunctionalInterface
public interface ICondition {

	/**
	 * Compares this student record against arbitrary number of conditions.
	 * 
	 * @param record
	 *            student record whose info will be compared
	 * @return true if this record satisfies conditions, false otherwise
	 */
	public boolean compare(StudentRecord record);

}
