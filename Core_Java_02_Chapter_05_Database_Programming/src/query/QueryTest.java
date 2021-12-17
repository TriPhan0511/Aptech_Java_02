package query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class QueryTest 
{
	private static final String allQuery = 
			"SELECT Price, Title"
			+ " FROM Books";
	
//	private static final String authorPublisherQuery = 
//			"SELECT Books.Price, books.Title"
//			+ " FROM Books, BooksAuthors, Authors, Publishers"
//			+ " WHERE Books.Publisher_Id = Publishers.Publisher_Id"
//			+ " AND Books.ISBN = BooksAuthors.ISBN"
//			+ " AND Authors.Author_Id = BooksAuthors.Author_Id"
//			+ " AND Publishers.Name = ?"
//			+ " AND Authors.Name = ?";
	
	private static final String authorPublisherQuery =
			"SELECT Books.Price, Books.Title"
			+ " FROM BooksAuthors"
			+ " INNER JOIN Authors ON Authors.Author_Id = BooksAuthors.Author_Id"
			+ " INNER JOIN Books ON Books.ISBN = BooksAuthors.ISBN"
			+ " INNER JOIN Publishers ON Publishers.Publisher_Id = Books.Publisher_Id"
			+ " WHERE Authors.Name = ?"
			+ " AND Publishers.Name = ?";
	
	private static final String authorQuery = 
			"SELECT Books.Price, Books.Title"
			+ "	FROM BooksAuthors"
			+ "	INNER JOIN Books ON Books.ISBN = BooksAuthors.ISBN"
			+ "	INNER JOIN Authors ON Authors.Author_Id = BooksAuthors.Author_Id"
			+ "	WHERE Authors.Name = ?";
	
	private static final String publisherQuery = 
			"SELECT Books.Price, Books.Title"
			+ " FROM Books"
			+ " INNER JOIN Publishers ON Publishers.Publisher_Id = Books.Publisher_Id"
			+ " WHERE Publishers.Name = ?";
	
	private static final String priceUpdate = 
			"UPDATE Books"
			+ " SET Price = Price + ?"
			+ " WHERE Publisher_Id IN (SELECT Publisher_Id"
			+ " FROM Publishers WHERE Name = ?)";
	
	private static List<String> authors = new ArrayList<>();
	private static List<String> publishers = new ArrayList<>();
	
	public static void main(String[] args)
	{
		try (Connection conn = getConnection())
		{
			authors.add("Any");
			publishers.add("Any");
			
			try (Statement stat = conn.createStatement())
			{
				
//				Fill the authors array list
				var query = "SELECT Name FROM Authors";
				try (ResultSet result = stat.executeQuery(query))
				{
					while (result.next())
					{
						authors.add(result.getString("Name"));
					}
				}
				
//				Fill the publishers array list
				query = "SELECT Name FROM Publishers";
				try (ResultSet result = stat.executeQuery(query))
				{
					while (result.next())
					{
						publishers.add(result.getString("Name"));
					}
				}
				
				var done = false;
				while (!done)
				{
					String input = 
							Console.nextString("Q)uery C)hange prices E)xit: ").toUpperCase();
					if (input.equals("Q"))
					{
						executeQuery(conn);
					}
					else if (input.equals("C"))
					{
						changePrices(conn);
					}
					else
					{
						done = true;
					}
				}
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
	 * Executes the selected query.
	 * 
	 * @param conn The database connection.
	 */
	private static void executeQuery(Connection conn)
		throws SQLException
	{
		String author = select("Author:", authors);
		String publisher = select("Publisher:", publishers);
		PreparedStatement stat;
		
		if (!author.equals("Any") && !publisher.equals("Any"))
		{
			stat = conn.prepareStatement(authorPublisherQuery);
			stat.setString(1, author);
			stat.setString(2, publisher);
		}
		else if (!author.equals("Any") && publisher.equals("Any"))
		{
			stat = conn.prepareStatement(authorQuery);
			stat.setString(1, author);
		}
		else if (author.equals("Any") && !publisher.equals("Any")) 
		{
			stat = conn.prepareStatement(publisherQuery);
			stat.setString(1, publisher);
		}
		else
		{
			stat = conn.prepareStatement(allQuery);
		}
		
		try (ResultSet result = stat.executeQuery())
		{
			while (result.next())
			{
				System.out.println("yes");
				System.out.println(result.getString(1) + ", " + result.getString(2));
			}
		}
	}
	
	/**
	 * Executes an update statement to change prices.
	 * 
	 * @param conn The database connection.
	 */
	private static void changePrices(Connection conn)
	 throws SQLException
	{
		String publisher = select("Publishers:", publishers.subList(1, publishers.size()));
		double priceChange = Console.nextDouble("Change prices by: ");
		PreparedStatement stat = conn.prepareStatement(priceUpdate);
		stat.setDouble(1, priceChange);
		stat.setString(2, publisher);
		int r = stat.executeUpdate();
		System.out.println(r + " records updated.");
	}
	
	/**
	 * Asks the user to select a string.
	 * 
	 * @param prompt The prompt to display.
	 * @param options The options from which the user can choose.
	 * @return The option that the user choose.
	 */
	private static String select(String prompt, List<String> options)
	{
		while (true)
		{
			System.out.println(prompt);
			for (int i = 0; i < options.size(); i++)
			{
				System.out.printf("%2d) %s%n", i + 1, options.get(i));
			}
			int sel = Console.nextInt("Enter a number (between 1 and " + options.size() + "): ");
			if (sel > 0 && sel <= options.size())
			{
				return options.get(sel - 1);
			}
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



