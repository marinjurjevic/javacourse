package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.util.Comparator;

/**
 * <tt>StudentRecord</tt> represents a simple model of an object keeping info
 * about single student on a single collegium.
 * 
 * @author Marin Jurjevic
 *
 */
public class StudentRecord {

	/** Student jmbag */
	private final String jmbag;
	
	/** Student first name */
	private final String lastName;
	
	/** Student last name */
	private final String firstName;
	
	/** Student's number of points on mid term exam */
	private final double midTermExamPoints;
	
	/** Student's number of points on final exam */
	private final double finalExamPoints;
	
	/** Student's number of points gained on practical activities */
	private final double practicePoints;
	
	/** Student's final grade */
	private final int grade;

	/**
	 * Implementation of comparator that compares student records based on
	 * sum of all points gained during the course
	 */
	public static final Comparator<StudentRecord> BY_ALL_POINTS = new Comparator<StudentRecord>() {
		public int compare(StudentRecord o1, StudentRecord o2) {
			double sum1 = o1.SumOfPoints();
			double sum2 = o2.SumOfPoints();
			if (sum1 > sum2) {
				return 1;
			} else if (sum1 < sum2) {
				return -1;
			} else {
				return 0;
			}
		};
	};

	/**
	 * Creates new instance of StudentRecord.
	 * 
	 * @param jmbag
	 *            student unique identifier
	 * @param lastName
	 *            students last name
	 * @param firstName
	 *            students first name
	 * @param midTermExamPoints
	 *            points on mid term exam
	 * @param finalExamPoints
	 *            points on final exam
	 * @param practicePoints
	 *            points from practical activities
	 * @param grade
	 *            final grade on this collegium
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, Double midTermExamPoints,
			Double finalExamPoints, Double practicePoints, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midTermExamPoints = midTermExamPoints;
		this.finalExamPoints = finalExamPoints;
		this.practicePoints = practicePoints;
		this.grade = grade;
	}

	/**
	 * Returns sum of all points
	 * @return sum of all points
	 */
	public double SumOfPoints() {
		return finalExamPoints + midTermExamPoints + practicePoints;
	}

	/**
	 * Returns student's JMBAG.
	 * @return student's JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name.
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns number of points on mid term exam
	 * @return number of points on mid term exam
	 */
	public Double getMidTermExamPoints() {
		return midTermExamPoints;
	}

	/**
	 * Returns number of points on final exam
	 * @return number of points on final exam
	 */
	public Double getFinalExamPoints() {
		return finalExamPoints;
	}

	/**
	 * Returns number of points on practical activities
	 * @return number of points on practical activities
	 */
	public Double getPracticePoints() {
		return practicePoints;
	}

	/**
	 * Returns final grade on this course.
	 * @return final grade on course
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" + midTermExamPoints + "\t" + finalExamPoints + "\t"
				+ practicePoints + "\t" + grade + " (\u03A3 = " + SumOfPoints() + ")";
	}

}
