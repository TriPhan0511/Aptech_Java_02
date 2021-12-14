package query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueryTest_DIY 
{
	private static List<String> authors;
	private static List<String> publishers;
	
	public static void main(String[] args)
	{
		try(Connection conn = getConnection())
		{
//			publishers = getAllPublishers(conn);
//			authors = getAllAuthors(conn);
//			System.out.println("\nPusblishers:");
//			printList(publishers);
//			System.out.println("\nAuthors:");
//			printList(authors);
			
//			ResultSet result;
//			result = getPriceAndTitlte(conn);
//			showResultSet(result);
			
			updatePrice(conn);
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
	
	private static void updatePrice(Connection conn)
	 throws SQLException
	{
		String publisherName;
		String command;
		PreparedStatement stat;
		
		publisherName = Console.nextString("Enter a publisher name: ");
		command = "UPDATE Books"
				+ " SET Price = Price + 1"
				+ " WHERE Publisher_Id IN (SELECT Publisher_Id"
				+ "  FROM Publishers WHERE Name = ?)";
		stat = conn.prepareStatement(command);
		stat.setString(1, publisherName);
		int affectedRows = stat.executeUpdate();
		System.out.println("Affect rows: " + affectedRows);
	}
	
	/**
	 * Gets a publisher name and an author name from user input,
	 *  then base on them, gets the books' title and price.
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private static ResultSet getPriceAndTitlte(Connection conn)
		throws SQLException
	{
		String publisherName;
		String authorName;
		String command;
		ResultSet result;
		PreparedStatement stat;
		
		publisherName = Console.nextString("Enter a publisher name (e.g. o'reilly): ");
		authorName = Console.nextString("Enter an author name (e.g. flanagan): ");
//		command = "SELECT Books.Price, Books.Title"
//				+ " FROM BooksAuthors"
//				+ " INNER JOIN Authors ON Authors.Author_Id = BooksAuthors.Author_Id"
//				+ " INNER JOIN Books ON Books.ISBN = BooksAuthors.ISBN"
//				+ " INNER JOIN Publishers ON Publishers.Publisher_Id = Books.Publisher_Id"
//				+ " WHERE Publishers.Name = ?"
//				+ " AND Authors.Name = ?";
		command = "SELECT Books.Price, books.Title"
				+ " FROM Books, BooksAuthors, Authors, Publishers"
				+ " WHERE Books.Publisher_Id = Publishers.Publisher_Id"
				+ " AND Books.ISBN = BooksAuthors.ISBN"
				+ " AND Authors.Author_Id = BooksAuthors.Author_Id"
				+ " AND Publishers.Name = ?"
				+ " AND Authors.Name = ?";
		stat = conn.prepareStatement(command);
		stat.setString(1, publisherName);
		stat.setString(2, authorName);
		result = stat.executeQuery();
		return result;
	}
	
	/**
	 * Prints out the content in a list of String objects.
	 * 
	 * @param list A list of String objects.
	 */
	private static void printList(List<String> list)
	{
		if (list == null)
		{
			System.out.println("There is not any publisher.");
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
	 * Gets name of all authors in the database.
	 * 
	 * @param conn The database connection.
	 * @return A list contains name of publishers.
	 * @throws SQLException
	 */
	private static List<String> getAllAuthors(Connection conn)
			throws SQLException
	{
		List<String> publishers = new ArrayList<>();
		String command = "SELECT Name"
				+ " FROM Authors";
		PreparedStatement stat = conn.prepareStatement(command);
		ResultSet result = stat.executeQuery();
		while (result.next())
		{
			publishers.add(result.getString(1));
		}
		return publishers;
	}
	
	/**
	 * Gets name of all publishers in the database.
	 * 
	 * @param conn The database connection.
	 * @return A list contains name of publishers.
	 * @throws SQLException
	 */
	private static List<String> getAllPublishers(Connection conn)
		throws SQLException
	{
		List<String> publishers = new ArrayList<>();
		String command = "SELECT Name"
				+ " FROM Publishers";
		PreparedStatement stat = conn.prepareStatement(command);
		ResultSet result = stat.executeQuery();
		while (result.next())
		{
			publishers.add(result.getString(1));
		}
		return publishers;
	}
	
	/**
	 * Prints a result set.
	 * 
	 * @param result
	 * @throws SQLException
	 */
	private static void showResultSet(ResultSet result)
		throws SQLException
	{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		for (int i = 1; i <= columnCount; i++)
		{
			if (i > 1)
			{
				System.out.print(", ");
			}
			System.out.print(metaData.getColumnLabel(i));
		}
		System.out.println();
		
		while (result.next())
		{
			for (int i = 1; i <= columnCount; i++)
			{
				if (i > 1)
				{
					System.out.print(", ");
				}
				System.out.print(result.getString(i));
			}
			System.out.println();
		}
	}

	/**
	 * Gets a connection from the properties specified in the file database.properties
	 * 
	 * @return The database connection.
	 * @throws IOException
	 * @throws SQLException
	 */
	private static Connection getConnection()
		throws IOException, SQLException
	{
		var props = new Properties();
		try(InputStream in = Files.newInputStream(Paths.get("database.properties")))
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


