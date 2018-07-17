package hr.fer.zemris.java.tecaj.hw5.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents a field getter that returns last name from the student record.
 * 
 * @author Marin Jurjevic
 *
 */
public class LastNameFieldGetter implements IFieldValueGetter {

	/**
	 * Returns last name of the student record
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
