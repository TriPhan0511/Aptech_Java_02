package test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This program tests that the database and 
 * the JDBC driver are correctly configured.
 * @version 1.0 2021-12-07
 * @author Tri Phan
 *
 */
public class TestDB_DIY 
{
	public static void main(String[] args)
	{
		try
		{
			testDB();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Runs a test by creating a table, adding a value,
	 * showing the table contents, and removing the table.
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void testDB()
		throws SQLException, IOException
	{
		String command;
		try
		(
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
		)
		{
			
			command = "DROP TABLE IF EXISTS Greetings2";
			stat.executeUpdate(command);
			command = "CREATE TABLE Greetings2 (Message char(100))";
			stat.executeUpdate(command);
			command = "INSERT INTO Greetings2 (Message) "
					+ "VALUES ('Aloha, world!'), "
					+ "('Good morning, sir!'), "
					+ "('See you soon.')";
			stat.executeUpdate(command);
			
			command = "SELECT * FROM Greetings2";
			try
			(
				ResultSet result = stat.executeQuery(command);
			)
			{
				while (result.next())
				{
					System.out.println(result.getString(1));
				}
			}
			
			command = "DROP TABLE IF EXISTS Greetings2";
			stat.executeUpdate(command);
		}
	}
	
	/**
	 * Gets a connection from the properties specified in the file database.properties.
	 * @return the database connection.
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






















