package exercises;

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
import java.sql.Types;
import java.util.Properties;

public class Main 
{
	public static void main(String[] args)
	{
		try (Connection conn = getConnection())
		{
			char choice;
			do
			{
				System.out.println("\nStudents Management Tester");
				System.out.println("1. Display all students' information.");
				System.out.println("2. Edit a student's information.");
				System.out.println("3. Delete a student from the database.");
				System.out.println("4. Display all student's information\n"
						+ "    via calling a stored procedure");
				System.out.println("5. Edit a student's information\n"
						+ "    via calling a stored procedure");
				System.out.println("6. Search for students based on a student id \n"
						+ "    or a full name via calling a stored procedure");
				System.out.println("7. Quit.");
				choice = Console.nextChar("\nEnter your choice: ");
				switch (choice)
				{
					case '1':
						printAllStudents(conn);
						break;
					case '2':
						updateAStudent(conn);
						break;
					case '3':
						deleteAStudent(conn);
						break;
					case '4':
						getAllStudents(conn);
						break;
					case '5':
						updateAStudentViaStoredProcedure(conn);
						break;
					case '6':
						searchForStudentsBasedOnIDOrFullname(conn);
						break;
					case '7':
						break;
					default:
						System.out.println("Wrong choice. Please re-enter.");
				}
			} while (choice != '7');
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
	 * Search students based on his/her student id or full name 
	 * 	via calling a stored procedure contained in the database.
	 * 
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void searchForStudentsBasedOnIDOrFullname(Connection conn)
		throws SQLException
	{
		String input = Console.nextString("\nEnter a student id or a full name: ");
		String command = "{call usp_Search_Students(?)}";
		CallableStatement stat = conn.prepareCall(command);
		stat.setString(1, input);
		ResultSet result = stat.executeQuery();
		if (!result.isBeforeFirst())
		{
			System.out.println("No students have id or fullname " + input);
		}
		else
		{
			while (result.next())
			{
				System.out.println("\nStudent ID: " + result.getString("idstud"));
				System.out.println("Full Name: " + result.getString("fullname"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Birthday: " + result.getDate("birthday"));
			}
		}
	}
	
	/**
	 * Update the information of a student based on his/her id
	 * 	via calling a stored procedure contained in the database.
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void updateAStudentViaStoredProcedure(Connection conn)
		throws SQLException
	{
		String tempID = Console.nextString(
				"\nEnter the id of student you want to edit the information (st**): ");
		System.out.println("\nThe current information of the student whose id is "
				+ tempID + ": ");
		printAStudent(conn, tempID);
		String tempFullName = Console.nextString("\nEnter a new full name: ");
		String tempGender = Console.nextString("Enter a new gender (male/female): ");
		String tempBirthday = Console.nextString("Enter a new birthday (yyyymmdd): ");
		String command = "{call usp_Update_Student(?,?,?,?)}";
		CallableStatement stat = conn.prepareCall(command);
		stat.setString(1, tempID);
		stat.setString(2, tempFullName);
		stat.setString(3, tempGender);
		stat.setString(4, tempBirthday);
		int affectedRow = stat.executeUpdate();
		if (affectedRow == 0)
		{
			System.out.println("No student has id " + tempID);
		}
		else
		{
			System.out.println("\nThe student has id " 
					+ tempID
					+ "was updated.\nDetails:");
			printAStudent(conn, tempID); // Display the data to see the result
			
		}
	}
	
	/**
	 * Gets the information of all students via calling 
	 * 	a stored procedure contained in the database.
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void getAllStudents(Connection conn)
		throws SQLException
	{
		String command = "{call usp_Student_List}";
		CallableStatement stat = conn.prepareCall(command);
		ResultSet result = stat.executeQuery();
		showStudentInfomation(result);
	}
	
//	----------------------------------------------------------------------------------------------------
	
	/**
	 * Deletes a student based on his/her id.
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void deleteAStudent(Connection conn)
		throws SQLException
	{
		String tempID = Console.nextString(
				"Enter the id of student you want to delete (st**): ");
		String command = 
				"DELETE FROM Students"
				+ "	WHERE IDStud = ?";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, tempID);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("The student has id " + tempID + " was deleted.");
		}
		else
		{
			System.out.println("No student has id " + tempID);
		}
	}
	
	/**
	 * Prints out the information of a student to the console.
	 * 
	 * @param conn The database connection.
	 * @param id
	 * @throws SQLException
	 */
	private static void printAStudent(Connection conn, String id)
		throws SQLException
	{
		String command = 
				"SELECT IDStud,"
				+ "	IDSci,"
				+ "	Fullname,"
				+ "	Gender,"
				+ "	Birthday"
				+ "	FROM Students"
				+ "	WHERE IDStud = ?";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, id);
		ResultSet result = stat.executeQuery();
		if (!result.isBeforeFirst())
		{
			System.out.println("No student has id " + id);
		}
		else
		{
			while (result.next())
			{
				System.out.println("\nStudent ID: " + result.getString("idstud"));
				System.out.println("Full Name: " + result.getString("fullname"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Birthday: " + result.getDate("birthday"));
			}
		}
	}
	
	/**
	 * Update the information of a student based on his/her id.
	 * 
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	private static void updateAStudent(Connection conn)
		throws SQLException
	{
		String tempID = Console.nextString(
				"\nEnter the id of student you want to edit the information (st**): ");
		System.out.println("\nThe current information of the student whose id is "
				+ tempID + ": ");
		printAStudent(conn, tempID);
		String tempFullName = Console.nextString("\nEnter a new full name: ");
		String command = 
				"UPDATE Students"
				+ "	SET Fullname = ?,"
				+ "	WHERE IDStud = ?";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, tempFullName);
		stat.setString(4, tempID);
		int affectedRow = stat.executeUpdate();
//		String command = 
//				"UPDATE Students"
//				+ "	SET Fullname = ?,"
//				+ "	Gender = ?,"
//				+ "	Birthday = ?"
//				+ "	WHERE IDStud = ?";
//		PreparedStatement stat = conn.prepareStatement(command);
//		stat.setString(1, tempFullName);
//		stat.setString(2, tempGender);
//		stat.setString(3, tempBirthday);
//		stat.setString(4, tempID);
//		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("\nThe information of student whose id is "
					+ tempID
					+ " was updated.\nDetails:");
			printAStudent(conn, tempID); // Display the data to see the result
		}
		else
		{
			System.out.println("No student has id " + tempID);
		}
	}
	
	/**
	 * Prints out the information of all students to the console.
	 * 
	 * @param conn The database connection.
	 * @param result
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void printAllStudents(Connection conn)
		throws SQLException
	{
		String command = "SELECT st.IDStud,"
				+ "	st.Fullname,"
				+ "	st.Gender,"
				+ "	st.Birthday,"
				+ "	sc.ScienceName,"
				+ "	ma.Mark,"
				+ "	su.SubjectName"
				+ " FROM Students st"
				+ " INNER JOIN Sciences sc ON sc.IDSci = st.IDSci"
				+ " INNER JOIN Marks ma ON ma.IDStud = st.IDStud"
				+ " INNER JOIN Subjects su ON su.IDSub = ma.IDSub";
		ResultSet result = conn.createStatement().executeQuery(command);
		if (!result.isBeforeFirst())
		{
			System.out.println("No information.");
		}
		else
		{
			while (result.next())
			{
				System.out.println("\nStudent ID: " + result.getString("idstud"));
				System.out.println("Full Name: " + result.getString("fullname"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Birthday: " + result.getDate("birthday"));
				System.out.println("Science Name: " + result.getString("sciencename"));
				System.out.println("Subject Name: " + result.getString("subjectname"));
				System.out.println("Mark: " +result.getDouble("mark"));
			}
		}
	}
	
	/**
	 * Prints out the information of a student to the console.
	 * 
	 * @param result
	 * @throws SQLException
	 */
	private static void showStudentInfomation(ResultSet result)
		throws SQLException
	{
		if (!result.isBeforeFirst())
		{
			System.out.println("No records");
		}
		else
		{
			while (result.next())
			{
				System.out.println("\nStudent ID: " + result.getString("idstud"));
				System.out.println("Full Name: " + result.getString("fullname"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Birthday: " + result.getDate("birthday"));
				System.out.println("Science Name: " + result.getString("sciencename"));
				System.out.println("Subject Name: " + result.getString("subjectname"));
				System.out.println("Mark: " +result.getDouble("mark"));
			}
		}
	}
	
	/**
	 * Gets a connection from the properties specified in 
	 * 	the file database.properties.
	 * 
	 * @return The database connection.
	 * @throws SQLException
	 * @throws IOException
	 */
	private static Connection getConnection()
			throws SQLException, IOException
	{
		var props = new Properties();
		try
		(
			InputStream in = Files.newInputStream(Paths.get("database.properties"));
		)
		{
			props.load(in);
		}
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
		{
			System.setProperty("jdbc.drivers", drivers);
		}
		String url = props.getProperty("jdbc.url");
		String user = props.getProperty("jdbc.user");
		String password = props.getProperty("jdbc.password");
		return DriverManager.getConnection(url, user, password);
	}
}

























