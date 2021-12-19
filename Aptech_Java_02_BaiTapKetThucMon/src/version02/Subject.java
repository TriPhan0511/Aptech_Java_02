package version02;

public class Subject 
{
	private String code;
	private String name;
	private double score;
	
	public Subject(String code, String name, double score)
	{
		this.code = code;
		this.name = name;
		this.score = score;
	}
	
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
	
	public void showMe()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Subject Code: ");
		builder.append(this.code);
		builder.append(", Subject Name: ");
		builder.append(this.name);
		builder.append(", Score: ");
		builder.append(score);
		System.out.println(builder.toString());
	}
}































