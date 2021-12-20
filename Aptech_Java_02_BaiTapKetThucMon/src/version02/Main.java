package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main 
{
	private static List<String> existingSubjectCodes = new ArrayList<>();
	
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
			
//			-------------------------------------------------------------------------
			
			/* B. Subjects */
			
//			Fill list of existing subject codes
			existingSubjectCodes = dbHandler.fetchSubjectCodes(conn);		
//			Test
			System.out.println("\nExisting subject codes in the database:");
			dbHandler.showStringList(existingSubjectCodes);
			
////			1. CREATES a Subject object and save it to the database
//			dbHandler.insertASubjectFromUserInput(conn, existingSubjectCodes);
////			Test
//			Subject subject = dbHandler.fetchASubjectFromUserInput(conn);
//			if (subject == null)
//			{
//				System.out.println("\nThere is no subject has code you entered.");
//			}
//			else
//			{
//				subject.showMe();
//			}
			
////		2.1 RETRIEVES a Subject object from the database
//		String subjectCode = "su001";
////		String subjectCode = "s100";
//		Subject subject = dbHandler.fetchASubject(conn, subjectCode);
//		if (subject == null)
//		{
//			System.out.println("\nNo subject has code " + subjectCode);
//		}
//		else
//		{
//			subject.showMe();
//		}
		
//			2.2 RETRIEVES a Subject object based on a subject code entered by the user
//		Subject subject = dbHandler.fetchASubjectFromUserInput(conn);
//		if (subject == null)
//		{
//			System.out.println("\nThere is no subject has code you entered.");
//		}
//		else
//		{
//			subject.showMe();
//		}
			
////		2.3. RETRIEVES all Subject objects from the database
//		List<Subject> subjects = dbHandler.fetchSubjects(conn);
//		dbHandler.showSubjectList(subjects);
			
////			3.1 UPDATES a record in the Subjects table in the database
//			dbHandler.updateASubjectRecord(conn, "su001", "Spring");
			
////			3.2 UPDATES a record based on a subject code and 
////			a subject name entered by the user
//			dbHandler.updateASubjectBasedOnUserInput(conn, existingSubjectCodes);
			
////			4.1 DELETES a record in the Subjects table
////			String s = "su002";
////			String s = "su100";
//			String s = "xml";
//			dbHandler.deleteASubjectRecord(conn, s);
			
//			4.2 DELETES all records in the Subjects table
			
			
			
////			Test
//			List<Subject> subjects = dbHandler.fetchSubjects(conn);
//			System.out.println();
//			dbHandler.showSubjectList(subjects);
//			
//
//			
////			Test
//			subjects = dbHandler.fetchSubjects(conn);
//			System.out.println();
//			dbHandler.showSubjectList(subjects);			
			
			
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





























