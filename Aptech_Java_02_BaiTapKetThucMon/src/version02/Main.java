package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main 
{
	public static void main(String[] args)
	{
		DatabaseHandler dbHandler = new DatabaseHandler();
		try (Connection conn = dbHandler.getConnection())
		{
//			Fetch students from the database and show their basic information
			List<Student> students = dbHandler.fetchStudents(conn);
			dbHandler.showStudentList(students);
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
}
