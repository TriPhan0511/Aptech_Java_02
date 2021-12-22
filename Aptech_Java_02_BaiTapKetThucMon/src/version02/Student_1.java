package version02;

import java.time.LocalDate;
import java.util.List;

public class Student_1 
{
	private String id;
	private String name;
	private LocalDate dob;
	private String phoneNumber;
	
	private List<Subject> subjects;
	
	public Student_1() {}
	
	public Student_1(String id, String name,
			LocalDate dob, String phoneNumber)
	{
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
	}
	
	public Student_1(String id, String name,
			LocalDate dob, String phoneNumber, List<Subject> subjects)
	{
		this(id, name, dob, phoneNumber);
		this.subjects = subjects;
	}
	
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
	
	public void setSubjects(List<Subject> subjects)
	{
		this.subjects = subjects;
	}
	
	/**
	 * Prints out the basic information of a Student object to the console.
	 */
	public void showMe()
	{
		String dob;
		String phoneNumber;
		String notKnown = "Not known";
		StringBuilder buildder = new StringBuilder();
		buildder.append("\nID: ");
		buildder.append(getID());
		buildder.append(", Name: ");
		buildder.append(getName());
		buildder.append(",\n");
		dob = getDOB() == null ? notKnown : getDOB().toString();
		buildder.append("Date of Birth: ");
		buildder.append(dob);
		phoneNumber = getPhoneNumber() == null ? notKnown : getPhoneNumber(); 
		buildder.append(", Phone Number: ");
		buildder.append(phoneNumber);
		System.out.println(buildder.toString());
		
		if (subjects == null)
		{
			System.out.println("This student hasn't any score.");
		}
		else
		{
			for (Subject item : subjects)
			{
//				System.out.println(item);
				item.showMeWithoutScore();
			}
		}
	}
}
























