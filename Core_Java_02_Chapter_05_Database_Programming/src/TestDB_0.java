import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class TestDB_0 
{
	private static final String DATABASE_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Students_PhanPhuTri";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "123456789";
	
	public static void main(String[] args)
	{
		try 
		{
			runTest();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(String dbURL, 
			String username, String password)
			throws SQLException
	{
		return DriverManager.getConnection(dbURL, username, password);
	}
	
	private static void runTest() 
			throws SQLException
	{
		try
		(
			Connection conn = getConnection(DATABASE_URL, USERNAME, PASSWORD);
			Statement stat = conn.createStatement();
		)
		{
			try 
			(
				ResultSet resultSet = stat.executeQuery("SELECT * FROM Students")
			)
			{
				/*
				 * if (resultSet.next()) { System.out.println(resultSet.getInt(1));
				 * System.out.println(resultSet.getString(2)); }
				 */
				while (resultSet.next())
				{
					System.out.printf("%d %s\n",
							resultSet.getInt(1),
							resultSet.getString(2));
					
				}
			}
		}
	}
}




























