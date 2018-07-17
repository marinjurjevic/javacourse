package hr.fer.zemris.java.tecaj.hw5.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * <code>IFieldValueGetter</code> enables retrieving values from student record.
 * It has only one method which returns only one field value.
 * 
 * @author Marin Jurjevic
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

	public String get(StudentRecord record);
}
