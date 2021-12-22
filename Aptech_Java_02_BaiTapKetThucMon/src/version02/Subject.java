package version02;

public class Subject 
{
//	Private instance variables
	private String code;
	private String name;
	private double score;
	
//	A parameterized constructor
	public Subject(String code, String name)
	{
		this.code = code;
		this.name = name;
	}
	
//	A parameterized constructor
	public Subject(String code, String name, double score)
	{
		this.code = code;
		this.name = name;
		this.score = score;
	}
	
//	Getters
	public String getCode()
	{
		return this.code;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public double getScore()
	{
		return this.score;
	}
	
//	Setter
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Prints out the information of a Subject object to the console.
	 */
	public void showMe()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("\nSubject Code: ");
		builder.append(this.code);
		builder.append(", Subject Name: ");
		builder.append(this.name);
		builder.append(", Score: ");
		builder.append(score);
		System.out.println(builder.toString());
	}
	
	/**
	 *  Prints out the information of a Subject object without the score field 
	 *   to the console.
	 */
	public void showMeWithoutScore()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Subject Code: ");
		builder.append(this.code);
		builder.append(", Subject Name: ");
		builder.append(this.name);
		System.out.println(builder.toString());
	}
}































