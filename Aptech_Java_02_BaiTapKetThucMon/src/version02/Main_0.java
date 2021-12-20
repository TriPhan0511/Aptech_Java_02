package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main_0 
{
	public static void main(String[] args)
	{
//		Creates a DatabaseHandler for handling the tasks within the database
		DatabaseHandler dbHandler = new DatabaseHandler();
		
		try (Connection conn = dbHandler.getConnection())
		{
////			1. Fetches students from the database and show their basic information
//			List<Student> students = dbHandler.fetchStudents(conn);
//			dbHandler.showStudentList(students);
			
////			2. Fetches a student based on a student id (basic)
//			String studentID = "s001";
////			String studentID = "s005";
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
			
////			3. Fetches the scores of a specific student
//			String studentID = "s001";
//			List<Subject> subjects = dbHandler.fetchScores(conn, studentID);
//			dbHandler.showSubjectList(subjects);

////			4. Fetches the information and scores of a specific student
//			String studentID = "s001";
//			Student student = dbHandler.fetchAStudentAndHisScores(conn, studentID);
//			student.showMe();
			
////			5. Fetches the information, score of each subject, average scores,
////			and ranking of a specific student
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
//			
//			System.out.println();
//			String s = dbHandler.fetchAverageScoreAndRanking(conn, studentID);
//			System.out.println(s);
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





























