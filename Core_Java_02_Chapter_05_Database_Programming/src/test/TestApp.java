package test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import query.Console;

public class TestApp 
{
	public static void main(String[] args)
	{
//		try (Connection conn = getConnection())
//		{
//			System.out.println("Login Successfull");
//		}
//		catch (SQLException e) 
//		{
//			System.out.println("SQLExeption error!!!");
//			for (Throwable t : e)
//			{
//				t.printStackTrace();
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		
		while (true)
		{
			try (Connection conn = getConnection())
			{
				System.out.println("\nLogin Successfull.\n");
				
				String command;
				PreparedStatement stat;
				ResultSet result;
				
				command = "SELECT * FROM  Books";
				stat = conn.prepareStatement(command);
				result = stat.executeQuery();
				while (result.next())
				{
					System.out.println(result.getString(1));
				}
				break;
			}
			catch (SQLException e) 
			{
				System.out.println("\nLogin failed for user 'sa'. Please re-enter.");
			}
			catch (IOException e)
			{
				System.out.println("\nThere was a problem reading the database configuration file.");
				break;
			}
		}
	}
	
	private static Connection getConnection()
	 throws SQLException, IOException
	{
		Properties props = new Properties();
		String url;
		String user;
		String password;
		
		try (InputStream in = Files.newInputStream(Paths.get("database2.properties")))
//		try (InputStream in = Files.newInputStream(Paths.get("database3.properties")))
		{
			props.load(in);
		}
		url = props.getProperty("jdbc.url");
		user = Console.nextString("Enter your account (e.g. sa): ");
		password = Console.nextString("Enter you password (e.g. 123456789): ");
		return DriverManager.getConnection(url, user, password);
	}
}

























