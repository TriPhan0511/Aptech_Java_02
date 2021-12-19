package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
	public static void main(String[] args)
	{
//		Creates a DatabaseHandler for handling the tasks within the database
		DatabaseHandler dbHandler = new DatabaseHandler();
		
		try (Connection conn = dbHandler.getConnection())
		{
			/* A. Students */
////			1. Fetches the information, score of each subject, average scores,
////				and ranking of a specific student
////			String studentID = "s001";
//			String studentID = "s005";
////			String studentID = "s100";
//			Student student = dbHandler.fetchAStudent(conn, studentID);
//			if (student == null)
//			{
//				System.out.println("\nNo student has id " + studentID);
//			}
//			else
//			{
//				student.showMe();
//			}
			
////			2. Fetches the information, score of each subject, average scores, 
////				and ranking of all students from the database.
//			List<Student> students = dbHandler.fetchStudents(conn);
//			dbHandler.showStudentList(students);
			
//			3. Create a new student
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





























