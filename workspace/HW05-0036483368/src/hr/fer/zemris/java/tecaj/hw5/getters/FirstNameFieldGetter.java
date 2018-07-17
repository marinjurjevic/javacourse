package hr.fer.zemris.java.tecaj.hw5.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents a field getter that returns first name from the student record.
 * 
 * @author Marin Jurjevic
 *
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	/**
	 * Returns first name of given record.
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
