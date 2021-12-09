package query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorQueryTest 
{
	public static void main(String[] args)
	{
		char choice;
		try
		(
			Connection conn = getConnection();
		)
		{
			do
			{
				System.out.println("\nAuthor Query Test");
				System.out.println("1. Add a new author.");
				System.out.println("2. Search an author based on id.");
				System.out.println("3. Update an author.");
				System.out.println("4. Delete an author.");
				System.out.println("5. Quit");
				choice = Console.nextChar("\nEnter your choice: ");
				switch (choice)
				{
					case '1':
						addAnAuthor(conn);
						break;
					case '2':
						getAuthorByID(conn);
						break;
					case '3':
						updateAnAuthor(conn);
						break;
					case '4':
						deleteAnAuthor(conn);
						break;
					case '5':
						break;
					default:
						System.out.println("Wrong choice. Please re-enter.");
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
	 * Adds a new author record.
	 * 
	 * @param conn A database connection.
	 * @throws SQLException
	 */
	private static void addAnAuthor(Connection conn)
		throws SQLException
	{
		String id = Console.nextString("Enter an id: ");
		String name = Console.nextString("Enter a name: ");
		String fname = Console.nextString("Enter a first name: ");
		String command = 
				"INSERT INTO Authors (Author_Id, Name, Fname)"
				+ "	VALUES (?, ?, ?)";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, id);
		stat.setString(2, name);
		stat.setString(3, fname);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("\nAn author record added.\n"
					+ "Details of new record:");
			getAuthorByID(conn, id); // Display the data to see the result
		}
	}
	
	/**
	 * Gets an author record based on its id.
	 * 
	 * @param conn A database connection
	 * @param id A string represents the id of an author.
	 * @throws SQLException
	 */
	private static void getAuthorByID(Connection conn, String id)
			throws SQLException
	{
		String authorQuery = 
				"SELECT Author_Id, Name, Fname"
						+ "	FROM Authors"
						+ "	WHERE Author_Id = ?"; 
		
		PreparedStatement stat = conn.prepareStatement(authorQuery);
		stat.setString(1, id);
		printAuthor(stat.executeQuery());
	}
	
	/**
	 * Gets an author record based on its id which entered from the user.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private static void getAuthorByID(Connection conn)
		throws SQLException
	{
		String id = Console.nextString("\nEnter author id you want to search: ");
		getAuthorByID(conn, id);
	}
	
	/**
	 * Updates an author record.
	 * 
	 * @param conn A database connection.
	 * @throws SQLException
	 */
	private static void updateAnAuthor(Connection conn)
		throws SQLException
	{
		String id = Console.nextString("\nEnter the id of the author you want to edit: ");
		String tempName = Console.nextString("Enter a new name: ");
		String tempFName = Console.nextString("Enter a new first name: ");
		String command = 
				"UPDATE Authors"
				+ "	SET Name = ?, Fname = ?"
				+ "	WHERE Author_Id = ?";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, tempName);
		stat.setString(2, tempFName);
		stat.setString(3, id);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("\nThe author record has id " + id + " is updated.\n"
					+ "Details of updated record:");
			getAuthorByID(conn, id); // Display the data to see the result 
		}
		else
		{
			System.out.println("No author has id " + id);
		}
	}
	
	/**
	 * Deletes an author record based on its id.
	 * 
	 * @param conn A database connection.
	 * @throws SQLException
	 */
	private static void deleteAnAuthor(Connection conn)
		throws SQLException
	{
		String id = Console.nextString("\nEnter the id of the author whom you want to delete: ");
		String command = 
				"DELETE FROM Authors"
				+ "	WHERE Author_Id = ?";
		PreparedStatement stat = conn.prepareStatement(command);
		stat.setString(1, id);
		int affectedRow = stat.executeUpdate();
		if (affectedRow != 0)
		{
			System.out.println("The author record which has id " + id + " was deleted.");
		}
		else
		{
			System.out.println("No author has id " + id);
		}
	}

	/**
	 * Prints out Author record(s) to the console.
	 * @param result A ResultSet object.
	 * @throws SQLException
	 */
	private static void printAuthor(ResultSet result)
		throws SQLException
	{
		if (!result.isBeforeFirst())
		{
			System.out.println("There are no author records.");
		}
		else
		{
			while (result.next())
			{
				System.out.println("\nAuthor's ID: " + result.getString("author_id"));
				System.out.println("Author's Name: " + result.getString("name"));
				System.out.println("Author's First Name: " + result.getString("fname"));
			}
		}
		
	}
	
	/**
	 * Gets a connection from the properties specified
	 * in the file database.properties.
	 * @return The database connection
	 * @throws IOException
	 * @throws SQLException
	 */
	private static Connection getConnection()
			throws SQLException, IOException
	{
		var jdbcDetailsFile = "database.properties"; 
		var props = new Properties();
		try
		(
			InputStream in = Files.newInputStream(Paths.get(jdbcDetailsFile));
		)
		{
			props.load(in);
		}
		String url = props.getProperty("jdbc.url");
		String user = props.getProperty("jdbc.user");
		String password = props.getProperty("jdbc.password");
		return DriverManager.getConnection(url, user, password);
		
	}
}

