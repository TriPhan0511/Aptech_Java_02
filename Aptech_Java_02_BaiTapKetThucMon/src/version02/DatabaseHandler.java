package version02;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
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
	 * Deletes a record in the Subjects table based on a subject code. 
	 * @param conn
	 * @param subjectCode
	 * @throws SQLException
	 */
	public void deleteASubjectRecordBasedOnSubjectCode(Connection conn, String subjectCode)
		throws SQLException
	{
		String query = "{ call usp_Delete_A_Subject_Based_On_Subject_Code(?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, subjectCode);
			stat.executeUpdate();
		}
	}
	
	/**
	 * Updates a subject name of a record in the Subjects table in the database.
	 * @param conn
	 * @param subjectCode
	 * @param newSubjectName
	 * @throws SQLException
	 */
	public void updateASubjectRecord(
		Connection conn, String subjectCode, String newSubjectName)
		throws SQLException
	{
		String query = "{ call usp_Update_A_Subject(?, ?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, subjectCode);
			stat.setString(2, newSubjectName);
			stat.executeUpdate();
		}
	}
	
	/**
	 * Fetches a list of strings represent subject codes
	 * 	and subject names from the Subjects table in the database.
	 * @param conn The database connection.
	 * @param subjectName A string represents a subject name.
	 * @return A list of strings.
	 * @throws SQLException
	 */
	public List<String> fetchSubjectCodesAndSubjectNamesBasedOnName(
			Connection conn, String subjectName)
			throws SQLException
	{
		List<String> list = new ArrayList<>();
		String tempItem;
		String tempSubjectCode;
		String tempSubjectName;
		String query = 
				"{ call usp_Display_SubjectCodes_And_SubjectNames_Based_On_A_SubjectName(?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, subjectName);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					tempSubjectCode = result.getString("SubjectCode").trim();
					tempSubjectName = result.getString("SubjectName").trim();
					tempItem = tempSubjectCode + "-" + tempSubjectName;
					list.add(tempItem);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Fetches a string represents a subject code and a subject name
	 * 	from the Subjects table in the database.
	 * @param conn The database connection.
	 * @param subjectName A string represents a subject name.
	 * @return
	 * @throws SQLException
	 */
	public List<String> fetchSubjectCodesBasedOnASubjectName(
			Connection conn, String subjectName)
		throws SQLException
	{
		List<String> list = new ArrayList<>();
		String query = 
				"{ call usp_Display_SubjectCodes_Based_On_A_Subject_Name(?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, subjectName);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					list.add(result.getString("SubjectCode"));
				}
			}
		}
		return list;
	}
	
	/**
	 * Fetches a list of strings which represents subject records 
	 * 	from the Subjects table in the database. 
	 * @param conn The database connection.
	 * @return A string list containing subject records
	 * @throws SQLException
	 */
	public List<String> fetchAllSubjectRecords(Connection conn)
		throws SQLException
	{
		List<String> subjectRecords = new ArrayList<>();
		String tempSubjectCode;
		String tempSubjectName;
		String query = "SELECT SubjectCode, SubjectName FROM Subjects";
		try 
		(
			Statement stat = conn.createStatement();
			ResultSet result = stat.executeQuery(query);
		)
		{
			while (result.next())
			{
				tempSubjectCode = result.getString("SubjectCode").trim();
				tempSubjectName = result.getString("SubjectName").trim();
				subjectRecords.add(tempSubjectCode + "-" + tempSubjectName);
			}
		}
		return subjectRecords;
	}
	
	/**
	 * Fetches a subject from the Subjects table in the database.
	 * @param conn
	 * @param subjectCode
	 * @return
	 * @throws SQLException
	 */
	public List<String> fetchSubjectRecords(Connection conn, String subjectCode)
		throws SQLException
	{
		List<String> list = new ArrayList<>();
		String query = "{ call usp_Display_SubjectName(?) }";
		try (CallableStatement stat = conn.prepareCall(query);) 
		{
			stat.setString(1, subjectCode);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					list.add(subjectCode);
					list.add(result.getString("SubjectName"));
				}
			}
		}
		return list;
	}
	
	public Subject fetchASubjectRecord(Connection conn, String subjectCode)
			throws SQLException
	{
		Subject tempSubject = null;
		String query = "{ call  }";
		
		return tempSubject;
	}
	
	/**
	 * Fetches all of the subject codes in the Subjects table from the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<String> fetchSubjectCodes(Connection conn)
		throws SQLException
	{
		List<String> list = new ArrayList<>();
		String query = "SELECT SubjectCode FROM Subjects";
		try 
		(
			Statement stat = conn.createStatement();
			ResultSet result = stat.executeQuery(query);
		)
		{
			while (result.next())
			{
				list.add(result.getString("SubjectCode").trim());
			}
		}
		return list;
	}
	
	/**
	 * Insert a new record into the Subjects table from two Strings.
	 * @param conn
	 * @param subjectCode
	 * @param subjectName
	 * @throws SQLException
	 */
	public void insertASubjectRecord(
		Connection conn, String subjectCode, String subjectName)
		throws SQLException
	{
		String query = 
				"INSERT INTO Subjects (SubjectCode, SubjectName)"
				+ " VALUES (?, ?)";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, subjectCode);
			stat.setString(2, subjectName);
			stat.executeUpdate();
		}
	}
	
	/**
	 * Fetches a student with his basic information, scores,
	 * 	average score,and ranking from the database.
	 * @param conn
	 * @param studentID
	 * @return
	 * @throws SQLException
	 */
	public Student fetchAStudent(Connection conn, String studentID)
		throws SQLException
	{
		Student tempStudent;
		String averageScoreAndRanking;
		double tempAverageScore;
		String tempRanking;
		 
		averageScoreAndRanking = fetchAverageScoreAndRanking(conn, studentID);
//		Two patterns of the string averageScoreAndRanking: 
//		Average Score: 4.5, Ranking: Fail
//		or Average Score: , Ranking:
		int firstColonPos = averageScoreAndRanking.indexOf(":");
		int commaPos = averageScoreAndRanking.indexOf(",");
		int seconColonPos = averageScoreAndRanking.indexOf(":", commaPos);
		try
		{
			tempAverageScore = Double.parseDouble(
					averageScoreAndRanking.substring(firstColonPos + 1, commaPos));

		}
		catch (NumberFormatException e)
		{
			tempAverageScore = -1.0;
		}
		tempRanking = averageScoreAndRanking.substring(seconColonPos + 2);
		
		tempStudent = fetchAStudentAndHisScores(conn, studentID);
		if (tempStudent != null)
		{
			tempStudent.setAverageScore(tempAverageScore);
			tempStudent.setRanking(tempRanking);
		}
		return tempStudent;
	}
	
	/**
	 * Fetches a list of Student objects from Students table 
	 * 	in the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Student> fetchStudents(Connection conn)
			throws SQLException
	{
		List<Student> students = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		String query;
		
		try (Statement stat = conn.createStatement())
		{
			query = "SELECT StudentID FROM Students";
			try (ResultSet result = stat.executeQuery(query))
			{
				while (result.next())
				{
					ids.add(result.getString("StudentID"));
				}
			}
		}
		
		for (String id : ids)
		{
			students.add(fetchAStudent(conn, id));
		}
		return students;
		
	}
	
	/**
	 * Fetches the average score and ranking of a specific student 
	 *  from the tables Students and Rankings in the database.
	 * @param conn
	 * @param studentID
	 * @return
	 * @throws SQLException
	 */
	private String fetchAverageScoreAndRanking(Connection conn, String studentID)
		throws SQLException
	{
		StringBuilder builder= new StringBuilder();
		String tempAverageScore;
		String tempRanking;
		String query = "{ call usp_Display_Information_And_Ranking_Of_A_Student(?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, studentID);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					builder.append("Average Score: ");
					tempAverageScore = result.getString("AverageScore") == null 
							? "" : result.getString("AverageScore");
					builder.append(tempAverageScore);
					builder.append(", Ranking: ");
					tempRanking = result.getString("Ranking") == null 
							? "" : result.getString("Ranking").trim();
					builder.append(tempRanking);
				}
				return builder.toString();
			}
		}
	}
	
	/**
	 * Fetches the information and scores of a specific student from the database.
	 * @param conn
	 * @param studentID
	 * @return
	 * @throws SQLException
	 */
	private Student fetchAStudentAndHisScores(Connection conn, String studentID)
		throws SQLException
	{
		Student tempStudent = fetchAStudentAndHisBasicInformation(conn, studentID);
		if (tempStudent != null)
		{
			List<Subject> subjects = fetchSubjectsAndScores(conn, studentID);
			tempStudent.setSubjects(subjects);
		}
		return tempStudent;
	}
	
	/**
	 * Fetches scores of a specific student from the tables Scores,
	 *  Students, and Subjects from the database.
	 * @param conn The database connection.
	 * @param studentID A string represents a student id.
	 * @return A list
	 * @throws SQLException
	 */
	private List<Subject> fetchSubjectsAndScores(Connection conn, String studentID) 
		throws SQLException
	{
		List<Subject> list = new ArrayList<>();
		String tempSubjectCode;
		String tempSubjectName;
		double tempScore;
		String query = "{ call usp_Display_Scores_Of_A_Student(?) }";
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, studentID);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					tempSubjectCode = result.getString("SubjectCode").trim();
					tempSubjectName = result.getString("SubjectName").trim();
					tempScore = Double.parseDouble(result.getString("Score"));   
					list.add(new Subject(tempSubjectCode, tempSubjectName, tempScore));
				}
			}
		}
		return list;
	}
	
	/**
	 * Fetches a record from the Student table in the database,
	 * and initialize a Student object from that record. 
	 * @param conn The database connection.
	 * @param studentID A string represents a student id.
	 * @return A Student object.
	 * @throws SQLException
	 */
	private Student fetchAStudentAndHisBasicInformation(Connection conn, String studentID)
		throws SQLException
	{
		String query = "{ call usp_Display_A_Student(?) }";
		Student tempStudent = null;
		String tempID;
		String tempName;
		String strDOB;
		String tempPhoneNumber;
		try (CallableStatement stat = conn.prepareCall(query))
		{
			stat.setString(1, studentID);
			try (ResultSet result = stat.executeQuery())
			{
				while (result.next())
				{
					tempID = result.getString("StudentID");
					tempName = result.getString("StudentName");
					strDOB = result.getString("DateOfBirth");
					tempPhoneNumber = result.getString("PhoneNumber");
					tempStudent = createAStudent(tempID, tempName, strDOB, tempPhoneNumber);
							
				}
				return tempStudent;
			}
		}
	}
	
	/**
	 * Show the content of a list containing Student objects.
	 * @param students
	 */
	public void showStudentList(List<Student> students)
	{
		if (students.size() == 0)
		{
			System.out.println("\nCurrently there is no student record in the database.");
		}
		else
		{
			System.out.println("\n*** Detail information of all students in the database ***");
			for (Student item : students)
			{
				item.showMe();
			}
		}
	}
	
	/**
	 * Show the content of a list containing String objects.
	 * @param list
	 */
	public void showStringList(List<String> list)
	{
		if (list.size() == 0)
		{
			System.out.println("\nCurrently there is no item in the list.");
		}
		else
		{
			for (String item : list)
			{
				System.out.println(item);
			}
		}
	}
	
	/**
	 * Creates a Student object from four Strings
	 * @param id A string represents a student id.
	 * @param name A string represents a student name.
	 * @param strDOB A string represents a 
	 * @param phoneNumber
	 * @return A String object.
	 */
	private Student createAStudent(
			String id, String name, String strDOB, String phoneNumber)
	{
		Student tempStudent;
		LocalDate dateDOB;
		try
		{
			dateDOB = LocalDate.parse(strDOB);
		}
		catch (DateTimeParseException | NullPointerException e)
		{
			dateDOB = null;
		}
		tempStudent = new Student(id, name, dateDOB, phoneNumber);
		return tempStudent;
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
