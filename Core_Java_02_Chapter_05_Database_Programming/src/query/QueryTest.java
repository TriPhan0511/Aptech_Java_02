package query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryTest 
{
	public static void main(String[] args)
	{
		try
		(
			Connection conn = getConnection();
			ResultSet result = getBooks(conn);
		)
		{
			int count = 0;
			while (result.next())
			{
				count++;
				System.out.println("\n*** Book " + count + " ***");
				System.out.println("Title: " + result.getString("title"));
				System.out.println("ISBN: " + result.getString("isbn"));
				System.out.println("Publisher ID: " + result.getString("publisher_id"));
				System.out.println("Price: " + result.getDouble("price"));
				
			}
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
	 * Gets records from Books table.
	 * 
	 * @param conn A database connection.
	 * @return A ResultSet objects which 
	 * consists of records from Books table.
	 * @throws SQLException
	 * @throws IOException
	 */
	private static ResultSet getBooks(Connection conn)
		throws SQLException, IOException
	{
		Statement stat = conn.createStatement();
		String command = 
				"SELECT Title, ISBN, Publisher_Id, Price"
				+ "	FROM Books";
		return stat.executeQuery(command);
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

