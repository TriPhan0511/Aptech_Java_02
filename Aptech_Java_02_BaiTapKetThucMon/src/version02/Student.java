package version02;

import java.time.LocalDate;
import java.util.List;

public class Student 
{
//	Private instance fields
	private String id;
	private String name;
	private LocalDate dob;
	private String phoneNumber;
	
	private List<Subject> subjects;
	
	private double averageScore;
	private String ranking;
	
//	Default constructor
	public Student() {}
	
//	A parameterized constructor
	public Student(String id, String name,
			LocalDate dob, String phoneNumber)
	{
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
	}
	
//	A parameterized constructor
	public Student(String id, String name,
			LocalDate dob, String phoneNumber, List<Subject> subjects)
	{
		this(id, name, dob, phoneNumber);
		this.subjects = subjects;
	}
	
//	A parameterized constructor
	public Student(String id, String name, LocalDate dob,
			String phoneNumber, List<Subject> subjects,
			double averageScore, String ranking)
	{
		this(id, name, dob, phoneNumber, subjects);
		this.averageScore = averageScore;
		this.ranking = ranking;
	}
	
//	Getters
	public String getID()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}
	
	public LocalDate getDOB()
	{
		return this.dob;
	}
	
	public List<Subject> getSubjects()
	{
		return this.subjects;
	}
	
	public double getAverageScore()
	{
		return this.averageScore;
	}
	
	public String getRanking()
	{
		return this.ranking;
	}
	
//	Setters
	public void setSubjects(List<Subject> subjects)
	{
		this.subjects = subjects;
	}
	
	public void setAverageScore(double averageScore)
	{
		this.averageScore = averageScore;
	}
	
	public void setRanking(String ranking)
	{
		this.ranking = ranking;
	}
	
	/**
	 * Prints out the basic information of a Student object to the console.
	 */
	public void showMe()
	{
		String dob;
		String phoneNumber;
		String strNotKnown = "Not known";
		StringBuilder builder;
		
		builder = new StringBuilder();
		builder.append("\nID: ");
		builder.append(getID());
		builder.append(", Name: ");
		builder.append(getName());
		builder.append(",\n");
		dob = getDOB() == null ? strNotKnown : getDOB().toString();
		builder.append("Date of Birth: ");
		builder.append(dob);
		phoneNumber = getPhoneNumber() == null ? strNotKnown : getPhoneNumber(); 
		builder.append(", Phone Number: ");
		builder.append(phoneNumber);
		System.out.println(builder.toString());
		
		if (subjects == null || subjects.size() == 0)
		{
			System.out.println("This student hasn't any score.");
		}
		else
		{
			for (Subject item : subjects)
			{
				item.showMeWithoutScore();
			}
		}
		
		builder = new StringBuilder();
		builder.append("Average Score: ");
		
		if (this.averageScore < 0)
		{
			builder.append(strNotKnown);
		}
		else
		{
			builder.append(this.averageScore);
		}
		
		
		builder.append(", Ranking: ");
		if (this.ranking.length() == 0)
		{
			builder.append(strNotKnown);
		}
		else
		{
			builder.append(this.ranking);
		}
		System.out.println(builder.toString());
	}
}
























