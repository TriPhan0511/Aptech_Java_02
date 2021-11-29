package exercise06;

/**
 * This class represents a student.
 * @version 1.0 2021-11-29
 * @author Tri Phan
 *
 */
public class Student 
{
	private String studentID;
	private String name;
	private double mediumScore;
	
	/**
	 * A parameterized constructor
	 * @param studentID A String represents a student's id.
	 * @param name A String represents a student's name.
	 * @param mediumScore A double represents a student's medium score.
	 */
	public Student(String studentID, String name, double mediumScore)
	{
		this.studentID = studentID;
		this.name = name;
		this.mediumScore = mediumScore;
	}
	
//	Getters
	public String getStudentID()
	{
		return this.studentID;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public double getMediumScore()
	{
		return this.mediumScore;
	}
}
