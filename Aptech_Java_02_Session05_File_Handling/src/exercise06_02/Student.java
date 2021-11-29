package exercise06_02;

public class Student 
{
	private String studentID;
	private String name;
	private double mediumScore;
	
	public Student(String studentID, String name, double mediumScore)
	{
		this.studentID = studentID;
		this.name = name;
		this.mediumScore = mediumScore;
	}
	
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
