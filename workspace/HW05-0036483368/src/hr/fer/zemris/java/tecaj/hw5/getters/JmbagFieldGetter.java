package hr.fer.zemris.java.tecaj.hw5.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents a field getter that returns jmbag from the student record.
 * 
 * @author Marin Jurjevic
 *
 */
public class JmbagFieldGetter implements IFieldValueGetter {

	/**
	 * Returns jmbag of given student record.
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
