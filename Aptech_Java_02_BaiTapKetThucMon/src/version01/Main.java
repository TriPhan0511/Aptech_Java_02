package version01;
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

public class Main 
{
//	Create a list for containing student ids 
//	fetched from the Student table in the database.
	private static List<String> studentIDs = new ArrayList<>();
	
//	Creates a list for containing subject codes and 
//	subject names fetched from the database
	private static List<String> subjects = new ArrayList<>();
	
//	Creates a list for containing subjectCodes fetched from the database
	private static List<String> subjectCodes = new ArrayList<>();
	
//	Creates a list for containing scores fetched from the database
	private static List<String> scores = new ArrayList<>();
	
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
			

			
//			Requirement 2: Add new student, display the content in the tables...
//			Fill the lists: studentIDs, subjectCodes
			fillListOfStudentIDs(conn, studentIDs);
			fillListOfSubjects(conn, subjects);
			fillListOfSubjectCodes(conn, subjectCodes);
			fillListOfCombinationOfStudentCodeAndSubjectCode(conn, scores);
			char choice;
			do
			{
//				Menu options
				System.out.println("\n----- Welcome to Student Management Application! -----\n");
				System.out.println("1. Add a new student");
				System.out.println("2. Add new or edit scores for existing students");		
				System.out.println("3. Show the personal information, score\n"
						+ "	and ranking of a specific student in the database.");
				System.out.println("4. Show the personal information, scores\n"
						+ "	and rankings of all students in the database.");
				System.out.println("5. Exit");
				choice = Console.nextChar("\nEnter your choice: ");
				switch (choice)
				{
				case '1':
					addStudent(conn, 
							studentIDs, subjectCodes, subjects, scores);
					break;
				case '2':
					addOrUpdateScoreForExistingStudent(conn, 
							scores, studentIDs, subjectCodes, subjects);
					break;
				case '3':
					searchAStudent(conn, studentIDs);
					break;
				case '4':
					showStudents(conn, studentIDs);
					break;
				case '5':
					System.out.println("\nGoodbye!");
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
	 * Gets student id and subject code from the user input from the console.
	 * @param conn The database connection.
	 * @param studentIDs An list of student ids which used for checking 
	 * 	an entered id exists in the Student table or not.
	 * @param subjectCodes An list of subject codes which used for validating 
	 * 	an entered subject code.
	 * @param subjects A list of combination of subject code and subject name.
	 * @return An array containing student id and subject code entered by the user.
	 * @throws SQLException
	 */
	private static String[] getStudentIDAndSubjectCodeFromUserInput(Connection conn, 
			List<String> studentIDs, List<String> subjectCodes, List<String> subjects)
		throws SQLException
	{
		String tempStudentID;
		String tempSubjectCode;
		
//		Creates an array to contain studentID and subjectCode
		String[] array = new String[2];
		
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
	 
	 * 
	 * @param conn
	 */
	
	/**
	 * Gets data from user input from the console,
	 * and add a new record to the Scores table. 
	 * @param conn The database connection
	 * @param scores
	 * @param studentIDs
	 * @param subjectCodes
	 * @param subjects
	 * @throws SQLException
	 */
	private static void addOrUpdateScoreForExistingStudent(Connection conn, 
				List<String> scores, List<String> studentIDs, List<String> subjectCodes, List<String> subjects)
			throws SQLException
	{
//		Gets student id and subject code from the user input from the console
		String[] array = getStudentIDAndSubjectCodeFromUserInput(conn, studentIDs, subjectCodes, subjects);
		String tempStudentID = array[0];
		String tempSubjectCode = array[1];
		
		insertOrUpdateRecordToScoresTable(conn, tempStudentID, tempSubjectCode, scores);
	}
	
	/**
	 * Add a new record to the Scores table
	 * @param conn
	 * @param studentID
	 * @param subjectCodes
	 * @param subjects
	 * @param scores
	 * @throws SQLException
	 */
	private static void addScoreForNewStudent(Connection conn, String studentID,
			List<String> subjectCodes, List<String> subjects, List<String> scores)
		throws SQLException
	{
		String tempSubjectCode;
		
//		Gets subjectCode from the user
		System.out.println("Choose a subject code from the following list:\n");
		System.out.printf("%-6s%s\n", "Code", "Subject Name");
		showList(subjects);
		
//		Gets a subject code from the user input and validate it
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
		insertOrUpdateRecordToScoresTable(conn, studentID, tempSubjectCode, scores);
	}
	
	/**
	 * Insert or update a record in the Scores table in the database.
	 * @param conn
	 * @param tempStudentID
	 * @param tempSubjectCode
	 * @param scores
	 * @throws SQLException
	 */
	private static void insertOrUpdateRecordToScoresTable(Connection conn,
			String tempStudentID, String tempSubjectCode, List<String> scores)
			throws SQLException
	{
//		Check whether a record containing (tempStudentID, tempSubjectCode) exists in the Scores table.
//		If the record exists in the Scores table, ask the user update this record or not.
//		If the record does not exist in the Scores table, add a new record containing 
//		 (tempStudentID, tempSubjectCode, score) to Scores table.
		if (scores.indexOf((tempStudentID + " " + tempSubjectCode).toLowerCase()) != -1)
		{
//			Show the score based on student id and subject code have just entered
			showScoreBasedOnStudentIDAndSubjectCode(conn, tempStudentID, tempSubjectCode);
			
			char wantToUpdate = Console.nextChar("\nDo you want to update the record (y/n)? ");
			if (wantToUpdate == 'y' || wantToUpdate == 'Y')
			{
//				Update the Scores table
				double score;
//				Gets a new score from the user input and validate it
				while (true)
				{
					score = Console.nextDouble("Enter new score: ");
					if (score < 0 || score > 10) // A score should be between 0 and 10
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
//			Gets a new score from the user input and validate it
			while (true)
			{
				score = Console.nextDouble("Enter the score: ");
				if (score < 0 || score > 10)  // A score should be between 0 and 10
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
	 * @param conn
	 * @param studentID
	 * @param subjectCode
	 * @param score
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
//			Update the local list containing combinations of student code and subject code
			fillListOfCombinationOfStudentCodeAndSubjectCode(conn, scores);
		}
	}
	
	/**
	 * Updates a record in the Scores table.
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
	 * Add a new record to the Student table.
	 * @param conn
	 * @param studentIDs
	 * @param subjectCodes
	 * @param subjects
	 * @param scores
	 * @throws SQLException
	 */
	private static void addStudent(Connection conn,
			List<String> studentIDs, List<String> subjectCodes,
			List<String> subjects, List<String> scores)
	 throws SQLException
	{
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
			else if (studentIDs.indexOf(tempStudentID) != -1) 
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
//			Updates the local list of student ids
			fillListOfStudentIDs(conn, studentIDs);
		}

//		Ask the user for adding scores for this new student
		char choice;
		while (true)
		{
			choice = Console.nextChar("\nDo you want to add score for this new student (y/n)? ");
			if (choice == 'y' || choice == 'Y')
			{
				addScoreForNewStudent(conn, tempStudentID, subjectCodes, subjects, scores);
			}
			else
			{
				break;
			}
		}
	}
	
	/**
	 * Prints out the personal information (id, name, date of birth, phone number),
	 *  average score, ranking, and score of each subject of students in a list.
	 * @param conn
	 * @param studentIDs
	 * @throws SQLException
	 */
	private static void showStudents(Connection conn, List<String> studentIDs)
		throws SQLException
	{
		if (studentIDs.size() == 0)
		{
			System.out.println("\nThere are not any students in the Student table.");
		}
		else
		{
			System.out.println("\nThe information of all students in the database:");
			for (String studentID : studentIDs)
			{
				showAStudent(conn, studentID);
			}
		}
		
	}
	
	/**
	 * Gets a student id from user input and if that id exists in the Students table,
	 * prints out the personal information, average score, ranking and score of each subject
	 * of the student who has that id. 
	 * @param conn
	 * @param studentIDs
	 * @throws SQLException
	 */
	private static void searchAStudent(Connection conn, List<String> studentIDs)
		throws SQLException
	{
		String tempStudentID = Console.nextString("\nEnter a student id: ");
		System.out.println("\nSearching information of student whose id is "
							+ tempStudentID
							+ "..."
							+ "\nResult:");
		if (studentIDs.indexOf(tempStudentID) == -1)
		{
			System.out.println("\nNo student has id " + tempStudentID);
		}
		else
		{
			showAStudent(conn, tempStudentID);
		}
	}
	
	/**
	 * Prints out the personal information (id, name, date of birth, phone number),
	 *  average score, ranking, and score of each subject of a specific student. 
	 * @param conn
	 * @param studentID
	 * @throws SQLException
	 */
	private static void showAStudent(Connection conn, String studentID) 
		throws SQLException
	{
		showAverageScoreAndRankingOfAStudent(conn, studentID);
		showScoresOfAStudent(conn, studentID);
	}
	
	/**
	 * Prints out the scores of a specific student.
	 * @param conn
	 * @param studentID
	 * @throws SQLException
	 */
	private static void showScoresOfAStudent(Connection conn, String studentID)
			throws SQLException
	{
		String query = "{ call usp_Display_Scores_Of_A_Student(?) }";
		CallableStatement stat = conn.prepareCall(query);
		stat.setString(1, studentID);
		ResultSet result = stat.executeQuery();
		System.out.println("\n**** Score of each subject *****");
//		Check if whether any returned record or not
		if (!result.isBeforeFirst())
		{
			System.out.println("This student hasn't any score yet!");
		}
		else
		{
			while (result.next())
			{
				System.out.printf("Subject: %s (Code: %s), Score: %s\n",
						result.getString("SubjectName"),
						result.getString("SubjectCode"),
						result.getString("Score"));
			}
		}
	}
	
	private static void showScoreBasedOnStudentIDAndSubjectCode(Connection conn,
			String studentID, String subjectCode)
			throws SQLException
	{
		String query = "{ call usp_Display_Scores_Based_On_Student_ID_And_Subject_Code(?,?) }";
		CallableStatement stat = conn.prepareCall(query);
		stat.setString(1, studentID);
		stat.setString(2, subjectCode);
		ResultSet result = stat.executeQuery();
//		System.out.println("\n**** Score of each subject *****");
		System.out.printf("\n**** Score of the subject whose id is %s *****\n", subjectCode);
//		Check if whether any returned record or not
		if (!result.isBeforeFirst())
		{
			System.out.println("This student hasn't any score of subject whose code is " + subjectCode);
		}
		else
		{
			while (result.next())
			{
				System.out.printf("Subject: %s, Score: %s\n",
						result.getString("SubjectName"),
						result.getString("Score"));
			}
		}
	}
	
	/**
	 * Prints out the average score and ranking of a specific student.
	 * @param conn
	 * @param studentID
	 * @throws SQLException
	 */
	private static void showAverageScoreAndRankingOfAStudent(Connection conn, String studentID)
		throws SQLException
	{
		String query = 
				"{ call usp_Display_Information_And_Ranking_Of_A_Student(?) }";
		CallableStatement stat = conn.prepareCall(query);
		stat.setString(1, studentID);
		ResultSet result = stat.executeQuery();
		String s;
		String out;
		while (result.next())
		{
			System.out.println("\n**** Personal Information *****");
			System.out.println("ID: " + result.getString("StudentID"));
			System.out.println("Name: " + result.getString("StudentName"));
			s = result.getString("DateOfBirth");
			out = s== null ? "No information" : s;
			System.out.println("Date of birth: " + out);
			s = result.getString("PhoneNumber");
			out = s== null ? "No information" : s;
			System.out.println("Phone Number: " + out);
			
			System.out.println("\n***** Average score and ranking *****");
			s = result.getString("AverageScore");
			if (s == null)
			{
				System.out.println("This student hasn't average score and ranking yet!");
			}
			else
			{
				System.out.println("Average Score: " + s);
				System.out.println("Ranking: " + result.getString("Ranking"));
			}
		}
	}
	
	/**
	 * Fill list of student ids.
	 * @param conn The database connection.
	 * @param list A list of student ids.
	 * @throws SQLException
	 */
	private static void fillListOfStudentIDs(Connection conn, List<String> list)
		throws SQLException
	{
		String query =
				"SELECT StudentID FROM Students";
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(query);
		while (result.next())
		{
			list.add(result.getString("StudentID").trim());
		}
	}
	
	/**
	 * Fill an array list of subject codes.
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
	 * Fill an array list of subjects
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
	 * Fill an array list of scores
	 * @param conn
	 * @param list
	 * @throws SQLException
	 */
	private static void fillListOfCombinationOfStudentCodeAndSubjectCode(
										Connection conn, List<String> list)
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
	 * Prints out the content of a array list to the console.
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
	 * Gets a connection from the properties specified in the file database.properties.
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


















