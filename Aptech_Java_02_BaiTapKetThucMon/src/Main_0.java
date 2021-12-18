import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Practice Test 
 * (Module: JP2 - Application Development Fundamentals-II)
 * 
 * @version 1.0 2021-12-16
 * @author Tri Phan
 *
 */
public class Main_0 
{
	public static void main(String[] args)
	{
		try (Connection conn = getConnection())
		{
//			Requirement 1: Account validation.
			try (Statement stat = conn.createStatement())
			{
				String accountQuery = "SELECT Username, Password FROM Accounts";
				ResultSet result = stat.executeQuery(accountQuery);
//				Because the is only one record in the Accounts table in the database,
//				so we will get only one account and one password from the ResultSet.
				String account = "";
				String password = "";
				while (result.next()) 
				{
					account = result.getString("Username");
					password = result.getString("Password");
				}

//				Validate account and password
				String inputAccount;
				String inputPassword;
				boolean valid = false;
				while (!valid)
				{
					inputAccount = Console.nextString("\nEnter your account (e.g. triphan): ");
					inputPassword = Console.nextString("Enter your password (e.g. 123456789): ");
					if (inputAccount.equalsIgnoreCase(account) && inputPassword.equals(password))
					{
						System.out.println("Login successful.");
						valid = true;
					}
					else
					{
						System.out.println("Login failed. Please re-login.");
					}
				}
			}
				
//				Requirement 2: Add new student, display the content in the tables...
				char choice;
				do
				{
//					Menu options
					System.out.println("\n+++++ Welcome to Student Management Application! +++++\n");
					System.out.println("1. Add a new student");
					System.out.println("2. Add or update scores for existing students");		
					System.out.println("3. Show the basic information of students");
					System.out.println("4. Show the students' information, their scores and rankings.");
					System.out.println("5. Exit");
					choice = Console.nextChar("\nEnter your choice: ");
					switch (choice)
					{
					case '1':
						addStudent(conn);
						break;
					case '2':
						addOrUpdateScoreForExistingStudent(conn);
						break;
					case '3':
						showStudents(conn);
						break;
					case '4':
						showStudentsAndRankings(conn);
						break;
					case '5':
						break;
						default:
							System.out.println("Wrong choice. Please enter a number between 1 and 5");
					}
				} while (choice != '5');
		}
		catch (SQLException e)
		{
			for (Throwable t : e)
			{
				t.printStackTrace();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new record to the Student table.
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void addStudent(Connection conn)
	 throws SQLException
	{
		// Fill the list of student ids for validation later.
		List<String> studentIDList = new ArrayList<>();
		fillListOfStudentIDs(conn, studentIDList); 
		
		String tempStudentID;
		while (true)
		{
			tempStudentID = Console.nextString("\nEnter the student id: ");
			tempStudentID = tempStudentID.toLowerCase();
			
//			Validate the student ID:
//			If the length of the student id entered by the user is greater than 5 (because data type of 
//			studentID field in the database is char(5)), or if that student id exists in the Students table, 
//			ask the user enter another student id.
			if (tempStudentID.length() > 5)
			{
				System.out.println("\nThe length of ID should be less than or equal 5.\n"
						+ "Please re-enter a 5-character ID.");
			}
			else if (studentIDList.indexOf(tempStudentID) != -1) 
			{
				System.out.println("\nThis student id " + tempStudentID + " exists in the Students table.\n"
						+ "Please re-enter another id.");
			}
			else
			{
				break;
			}
		}
		String name = Console.nextString("Enter the student name: ");
		String dob = Console.nextDate("Enter the date of birth (yyyy-mm-dd): ");
		String phoneNumber = Console.nextString("Enter the phone number: ");
		String query = 
				"INSERT INTO Students"
				+ " ("
				+ " StudentID,"
				+ " StudentName,"
				+ " DateOfBirth,"
				+ " PhoneNumber"
				+ " )"
				+ " VALUES (?, ?, ?, ?)";
		PreparedStatement stat = conn.prepareStatement(query);
		stat.setString(1, tempStudentID);
		stat.setString(2, name);
		stat.setString(3, dob);
		stat.setString(4, phoneNumber);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("\nA new student added.");
		}

//		Ask user for adding scores for this new student
		char choice;
		while (true)
		{
			choice = Console.nextChar("\nDo you want to add score for this new student (y/n)? ");
			if (choice == 'y' || choice == 'Y')
			{
				addScoreForNewStudent(conn, tempStudentID);
			}
			else
			{
				break;
			}
		}
	}
	
	/**
	 * Gets data from user input from the console,
	 * and add a new record to the Scores table.
	 * 
	 * @param conn
	 */
	private static void addOrUpdateScoreForExistingStudent(Connection conn)
			throws SQLException
	{
//		Gets student id and subject code from the user input from the console
		String[] array = getStudentIDAndSubjectCodeFromUserInput(conn);
		String tempStudentID = array[0];
		String tempSubjectCode = array[1];

		insertOrUpdateRecordToScoresTable(conn, tempStudentID, tempSubjectCode);
	}	
	
	/**
	 * Insert or update a record in the Scores table in the database.
	 * 
	 * @param conn
	 * @param tempStudentID
	 * @param tempSubjectCode
	 * @throws SQLException
	 */
	private static void insertOrUpdateRecordToScoresTable(Connection conn,
			String tempStudentID, String tempSubjectCode)
			throws SQLException
	{
//		Fills list of scores for validation later
		List<String> scores = new ArrayList<>();
		fillListOfScores(conn, scores);
		
//		Check whether a record containing (tempStudentID, tempSubjectCode) exists in the Scores table.
//		If the record exists in the Scores table, ask the user update this record or not.
//		If the record does not exist in the Scores table, add a new record containing 
//		 (tempStudentID, tempSubjectCode, score) to Scores table.
		if (scores.indexOf((tempStudentID + " " + tempSubjectCode).toLowerCase()) != -1)
		{
			char wantToUpdate = Console.nextChar(
					"The record (" 
					+ tempStudentID 
					+ ", " 
					+ tempSubjectCode 
					+ ") exists in the Scores table.\n"
					+ "Do you want to update the record (y/n)? "
					);
			if (wantToUpdate == 'y' || wantToUpdate == 'Y')
			{
//				Update the Scores table
				double score;
				while (true)
				{
					score = Console.nextDouble("Enter new score: ");
					if (score < 0 || score > 10) // Validate the score
					{
						System.out.println("\nThe score should be between 0 and 10.");
						continue;
					}
					else
					{
						break;
					}
				}
				updateScore(conn, tempStudentID, tempSubjectCode, score);				
			}
			else
			{
				System.out.println("\nThe record was not be updated.");
			}
		}
		else
		{
//			Insert a new record to the Scores table
			double score;
			while (true)
			{
				score = Console.nextDouble("Enter the score: ");
				if (score < 0 || score > 10) // Validate the score
				{
					System.out.println("\nThe score should be between 0 and 10.");
					continue;
				}
				else
				{
					break;
				}
			}
			addScore(conn, tempStudentID, tempSubjectCode, score);
		}
	}
	
	
	
	
	
	/**
	 * Add a new record to the Scores table.
	 * 
	 * @param conn The database connection.
	 * @param studentID A string represents a student id.
	 * @param subjectCode A string represents a subject code.
	 * @param score A double represents a score.
	 * @throws SQLException
	 */
	private static void addScore(Connection conn, 
			String studentID, String subjectCode, double score)
			throws SQLException
	{
		String query = "{ call usp_Insert_Score(?,?,?) }";
		CallableStatement stat = conn.prepareCall(query);
		stat.setString(1, studentID);
		stat.setString(2, subjectCode);
		stat.setDouble(3, score);
		int affectRow = stat.executeUpdate();
		if (affectRow != 0)
		{
			System.out.println("\nA score added.");
		}
	}
	
	/**
	 * Updates a record in the Scores table.
	 * 
	 * @param conn
	 * @param studentID
	 * @param subjectCode
	 * @param score
	 * @throws SQLException
	 */
	private static void updateScore(Connection conn, 
			String studentID, String subjectCode, double score)
			throws SQLException
	{
		String query = "{ call usp_Update_Score(?,?,?) }";
		CallableStatement stat = conn.prepareCall(query);
		stat.setString(1, studentID);
		stat.setString(2, subjectCode);
		stat.setDouble(3, score);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("\nA record updated.");
		}
	}
	
	/**
	 * Gets student id and subject code from the user input from the console.
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private static String[] getStudentIDAndSubjectCodeFromUserInput(Connection conn)
		throws SQLException
	{
		String tempStudentID;
		String tempSubjectCode;
		
//		Creates an array to contain studentID and subjectCode
		String[] array = new String[2];
		
//		Fill the list of student ids for validation later
		List<String> studentIDs = new ArrayList<>();
		fillListOfStudentIDs(conn, studentIDs);
		
//		Fill the list of subject codes for validation later
		List<String> subjectCodes = new ArrayList<>();
		fillListOfSubjectCodes(conn, subjectCodes);
		
//		Fill the list of subjects for showing the details of subjects
		List<String> subjects = new ArrayList<>();
		fillListOfSubjects(conn, subjects);
		
//		Gets studentID from user 
		while (true)
		{
			tempStudentID = Console.nextString("\nEnter the student ID: ");
			if (studentIDs.indexOf(tempStudentID) == -1)
			{
				System.out.println("\nThe id " 
									+ tempStudentID 
									+ " does not exist in the Students table.\n"
									+ "Please re-enter another id.");
			}
			else
			{
				break;
			}
		}
		
//		Gets subjectCode from user
		System.out.println("Choose a subject code from the following list:\n");
		System.out.printf("%-6s%s\n", "Code", "Subject Name");
		showList(subjects);
		
		while (true)
		{
			tempSubjectCode = Console.nextString("\nEnter the subject code: ");
			if (subjectCodes.indexOf(tempSubjectCode) == -1)
			{
				System.out.println("\nYour subject code you entered is invalid.\n"
										+ "Please re-enter another subject code.");
			}
			else
			{
				break;
			}
		}
		
//		Adds student id and subject code have just been entered by the user to the array
//		and returns the array.
		array[0] = tempStudentID;
		array[1]= tempSubjectCode;
		return array;
	}
	
	/**
	 * Add a new record to the Scores table
	 * 
	 * @param conn The database connetion.
	 * @param studentID A student id.
	 * @throws SQLException
	 */
	private static void addScoreForNewStudent(Connection conn, String studentID)
		throws SQLException
	{
		String tempSubjectCode;
		
//		Fill the list of subject codes for validation later
		List<String> subjectCodes = new ArrayList<>();
		fillListOfSubjectCodes(conn, subjectCodes);		
		
//		Fill the list of subjects for showing the details of subjects
		List<String> subjects = new ArrayList<>();
		fillListOfSubjects(conn, subjects);
		
//		Gets subjectCode from the user
		System.out.println("Choose a subject code from the following list:\n");
		System.out.printf("%-6s%s\n", "Code", "Subject Name");
		showList(subjects);
		
		while (true)
		{
			tempSubjectCode = Console.nextString("\nEnter the subject code: ");
			tempSubjectCode = tempSubjectCode.toLowerCase();
			if (subjectCodes.indexOf(tempSubjectCode) == -1)
			{
				System.out.println("\nYour subject code you entered is invalid.\n"
										+ "Please re-enter another subject code.");
			}
			else
			{
				break;
			}
		}
		
//		Insert or update based on the combination of student id and subject code
		insertOrUpdateRecordToScoresTable(conn, studentID, tempSubjectCode);
	}
	
	/**
	 * Prints out the basic information of students to the console. 
	 * 
	 * @param conn The database connection.
	 */
	private static void showStudents(Connection conn)
			throws SQLException
	{
		String query = "{call usp_Display_Students}"; 
		CallableStatement stat = conn.prepareCall(query);
		ResultSet result = stat.executeQuery();
		String s;
		String out;
		System.out.println("\nBasic information of students");
		while (result.next())
		{
			System.out.println("\nID: " + result.getString("StudentID"));
			System.out.println("Name: " + result.getString("StudentName"));
			
			s = result.getString("DateOfBirth");
			out = s== null ? "No information" : s;
			System.out.println("Date of birth: " + out);
			s = result.getString("PhoneNumber");
			out = s== null ? "No information" : s;
			System.out.println("Phone Number: " + out);
		}
	}
	
	/**
	 * Prints out the students' information, their scores and rankings.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private static void showStudentsAndRankings(Connection conn)
		throws SQLException
	{
		String query = "{call usp_Display_Students_And_Rankings}"; 
		CallableStatement stat = conn.prepareCall(query);
		ResultSet result = stat.executeQuery();
		String s;
		String out;
		System.out.println("\nBasic information of students, their scores and rankings");
		while (result.next())
		{
			System.out.println("\nID: " + result.getString("StudentID"));
			System.out.println("Name: " + result.getString("StudentName"));
			s = result.getString("DateOfBirth");
			out = s== null ? "No information" : s;
			System.out.println("Date of birth: " + out);
			s = result.getString("PhoneNumber");
			out = s== null ? "No information" : s;
			System.out.println("Phone Number: " + out);
			s = result.getString("AverageScore");
			
			System.out.println("\n**** Score and ranking *****");
			out = s== null ? "No information" : s;
			System.out.println("Average Score: " + out);
			s = result.getString("Ranking");
			out = s== null ? "No information" : s;
			System.out.println("Ranking: " + out);
		}
	}
	
	/**
	 * Prints out the content of a array list to the console.
	 * 
	 * @param list
	 */
	private static void showList(List<String> list)
	{
		for (String item : list)
		{
			System.out.println(item);
		}
	}
	
	/**
	 * Fill an array list of subject codes.
	 * 
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	private static void fillListOfSubjectCodes(Connection conn, List<String> list)
		throws SQLException
	{
		List<String> subjectList = new ArrayList<>();
		fillListOfSubjects(conn, subjectList);
		for (String item : subjectList)
		{
			item = item.substring(0, item.indexOf(" "));
			list.add(item);
		}
	}
	
	/**
	 * Fill an array list of scores
	 * 
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	private static void fillListOfScores(Connection conn, List<String> list)
		throws SQLException
	{
		String query = "SELECT StudentCode, SubjectCode FROM Scores";
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(query);
		while (result.next())
		{
			list.add(
					result.getString("StudentCode").trim() 
					+ " " 
					+ result.getString("SubjectCode").trim()); 
		}
	}
	
	/**
	 * Fill an array list of subjects
	 * 
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	private static void fillListOfSubjects(Connection conn, List<String> list)
		throws SQLException
	{
		String query = "SELECT SubjectCode, SubjectName FROM Subjects";
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(query);
		String record;
		while (result.next())
		{
			record = result.getString("SubjectCode").trim() + " " + result.getString("SubjectName").trim();
			list.add(record);
		}
	}
	
	/**
	 * Fill an array list of student ids.
	 * 
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	private static void fillListOfStudentIDs(Connection conn, List<String> list)
			throws SQLException
	{
		String query = "SELECT StudentID FROM Students"; 
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(query);
		while (result.next())
		{
			list.add(result.getString("StudentID").trim());
		}
	}
	
	/**
	 * Gets a connection from the properties specified in the file database.properties.
	 * 
	 * @return The database connection.
	 * @throws SQLException
	 * @throws IOException
	 */
	private static Connection getConnection()
	 throws SQLException, IOException
	{
		Properties props = new Properties();
		String drivers;
		String url;
		String user;
		String password;
		
		try (InputStream in = Files.newInputStream(Paths.get("database.properties")))
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
