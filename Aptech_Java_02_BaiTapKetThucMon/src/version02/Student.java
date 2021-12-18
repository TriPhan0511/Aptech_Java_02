package version02;

public class Student 
{
	private String id;
	private String name;
	private String phoneNumber;
	
	public Student() {}
	
	public Student(String id, String name, String phoneNumber)
	{
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
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
}
