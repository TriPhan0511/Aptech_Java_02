package test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This program tests that the database and 
 * the JDBC driver are correctly configured.
 * @version 1.0 2021-12-07
 * @author Tri Phan
 *
 */
public class TestDB 
{
	public static void main(String[] args)
	{
		try
		{
			runTest();
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
	 * Runs a test by creating a table, adding a value,
	 * showing the table contents, and removing the table.
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void runTest()
		throws SQLException, IOException
	{
		try
		(
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
		)
		{
			stat.executeUpdate("CREATE TABLE Greetings (Message CHAR(20)");
			stat.executeUpdate("INSERT INTO Greetings VALUES ('Hello, World!')");
			
			try
			(
				ResultSet result = stat.executeQuery("SELECT * FROM Greetings");
			)
			{
				if (result.next())
				{
					System.out.println(result.getString(1));
				}
			}
			
			stat.executeUpdate("DROP TABLE Greetings");
		}
	}
	
	/**
	 * Gets a connection from the properties specified in the file database.properties.
	 * @return the database connection.
	 */
	public static Connection getConnection()
		throws SQLException, IOException
	{
		var props = new Properties();
		try
		(
//				InputStream in = Files.newInputStream(Paths.get("database.properties"))
			InputStream in = Files.newInputStream(Paths.get("database.properties"))
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





























