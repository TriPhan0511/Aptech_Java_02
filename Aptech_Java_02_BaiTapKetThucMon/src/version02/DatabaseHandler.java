package version02;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseHandler 
{
	/**
	 * Gets a list of Student objects from the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Student> fetchStudents(Connection conn)
		throws SQLException
	{
		Student tempStudent;
		List<Student> students = new ArrayList<>();
		List<String> studentRecords = fetchStudentRecords(conn);
		int firstAsterisk;
		int secondAsterisk;
		int thirdAsterisk;
		String tempID;
		String tempName;
		String dobString;
		LocalDate tempDOB;
		String phoneNumber;
		for (String item : studentRecords)
		{
			firstAsterisk = item.indexOf("*");
			secondAsterisk = item.indexOf("*", firstAsterisk + 1);
			thirdAsterisk = item.lastIndexOf("*");
			tempID = item.substring(0, firstAsterisk);
			tempName = item.substring(firstAsterisk + 1, secondAsterisk);
			dobString = item.substring(secondAsterisk + 1, thirdAsterisk);
			try
			{
				tempDOB = LocalDate.parse(dobString);
			}
			catch (DateTimeParseException e)
			{
				tempDOB = LocalDate.parse("1980-11-05");
			}
			phoneNumber = item.substring(thirdAsterisk + 1);
			tempStudent = new Student(tempID, tempName, tempDOB, phoneNumber);
			students.add(tempStudent);
			
		}
		return students;
	}
	
	/**
	 * Fetch the content of the Student table in the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private List<String> fetchStudentRecords(Connection conn)
		throws SQLException
	{
		List<String> list = new ArrayList<>();
		String query = 
				"SELECT"
				+ " StudentID,"
				+ " StudentName,"
				+ " DateOfBirth,"
				+ " PhoneNumber"
				+ " FROM Students";
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(query);
		StringBuilder builder;
		String s;
		while (result.next())
		{
			builder = new StringBuilder();
			s = result.getString("StudentID");
			builder.append(s.trim());
			builder.append("*");
			s = result.getString("StudentName");
 			if (s == null)
			{
				builder.append("Not known");
			}
			else
			{
				builder.append(s.trim());
			}
 			builder.append("*");
 			s = result.getString("DateOfBirth");
			if (s == null)
			{
				builder.append("Not known");
			}
			else
			{
				builder.append(s.trim());
			}
			builder.append("*");
			s = result.getString("PhoneNumber");
			if (s == null)
			{
				builder.append("Not known");
			}
			else
			{
				builder.append(s.trim());
			}
			list.add(builder.toString());
		}
		
		return list;
	}
	
	/**
	 * Show the content of a list containing Student objects.
	 * @param students
	 */
	public void showStudentList(List<Student> students)
	{
		for (Student item : students)
		{
			System.out.println(item);
		}
	}
	
	/**
	 * Show the content of a list containing String objects.
	 * @param list
	 */
	private void showStringList(List<String> list)
	{
		for (String item : list)
		{
			System.out.println(item);
		}
	}
	
	/**
	 * Gets a connection from the properties specified in the file database.properties.
	 * @return The database connection.
	 * @throws SQLException
	 * @throws IOException
	 */
	public Connection getConnection()
	 throws SQLException, IOException
	{
		Properties props = new Properties();
		String drivers;
		String url;
		String user;
		String password;
		
		try (InputStream in = Files.newInputStream(Paths.get("database_version_02.properties")))
		{
			props.load(in);
		}
		
		drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
		{
			System.setProperty("jdbc.drivers", drivers);
		}
		
		url = props.getProperty("jdbc.url");
		user = props.getProperty("jdbc.user");
		password = props.getProperty("jdbc.password");
		
		return DriverManager.getConnection(url, user, password);
	}
}
