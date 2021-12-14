package query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
			+ " WHERE Publishers.Name = ?"
			+ " AND Authors.Name = ?";
	
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
			Statement stat = conn.createStatement();
			ResultSet result;
			result = stat.executeQuery(allQuery);
			while (result.next())
			{
				System.out.println(result.getString(1));
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
	
	private static Connection getConnection()
	 throws SQLException, IOException
	{
		var props = new Properties();
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



















































