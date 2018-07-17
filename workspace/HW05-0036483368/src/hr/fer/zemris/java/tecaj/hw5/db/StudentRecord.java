package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * StudentRecord represents record of a single student. Instances of this class
 * are immutable.
 * 
 * @author Marin Jurjevic
 *
 */
public class StudentRecord {

	/**
	 * student jmbag is his identification number
	 */
	private final String jmbag;

	private final String lastName;
	private final String firstName;

	/**
	 * final grade at the end of current year
	 */
	private final short finalGrade;

	/**
	 * Creates new student record for given student. Student is is identified
	 * with a unique identifier JMBAG.
	 * 
	 * @param jmbag
	 *            unique student identifier
	 * @param lastName
	 *            student's last name
	 * @param firstName
	 *            student's first name
	 * @param finalGrade
	 *            student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, short finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return student's final grade
	 */
	public short getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Compares two StudentRecord objects. They are equal only if their jmbag
	 * info is equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
