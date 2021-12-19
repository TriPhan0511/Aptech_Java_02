package version02;

import java.util.List;

public class Student_0 
{
	private String id;
	private String name;
	private String dob;
	private String phoneNumber;
	
	private List<Subject> subjects;
	
	public Student_0() {}
	
	public Student_0(String id, String name,
			String dob, String phoneNumber)
	{
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
	}
	
	public Student_0(String id, String name, String dob, String phoneNumber, List<Subject> subjects)
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
	
	public String getDOB()
	{
		return this.dob;
	}
	
	public List<Subject> getSubjects()
	{
		return this.subjects;
	}
	
	public String toString()
	{
		StringBuilder buildder = new StringBuilder();
		buildder.append("ID: ");
		buildder.append(getID());
		buildder.append(", Name: ");
		buildder.append(getName());
		buildder.append(", Date of Birth: ");
		buildder.append(getDOB());
		buildder.append(", Phone Number: ");
		buildder.append(getPhoneNumber());
		return buildder.toString();
	}
}
