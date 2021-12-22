package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectTester 
{
	private static DatabaseHandler dbHandler = new DatabaseHandler();
	private static SubjectHandler subjectHandler = new SubjectHandler();
	private static List<String> existingSubjectCodes = new ArrayList<>();
	
	public static void main(String[] args)
	{
		try (Connection conn = dbHandler.getConnection())
		{
			/* B. Subjects */
						
			//			Fill list of existing subject codes
						existingSubjectCodes = dbHandler.fetchSubjectCodes(conn);		
			//			Test
						System.out.println("\nExisting subject codes in the database:");
						dbHandler.showStringList(existingSubjectCodes);
						
//			//			1. CREATES a Subject object and save it to the database
//						subjectHandler.createASubjectFromUserInput(conn, existingSubjectCodes);
//			//			Test
//						Subject subject = subjectHandler.fetchASubjectFromUserInput(conn);
//						if (subject == null)
//						{
//							System.out.println("\nThere is no subject has code you entered.");
//						}
//						else
//						{
//							subject.showMe();
//						}
						
//			//		2.1 READS a Subject object from the database
////					String subjectCode = "su001";
//					String subjectCode = "s100";
//					Subject subject = subjectHandler.readASubject(conn, subjectCode);
//					if (subject == null)
//					{
//						System.out.println("\nNo subject has code " + subjectCode);
//					}
//					else
//					{
//						subject.showMe();
//					}
					
////						2.2 READS a Subject object based on a subject code entered by the user
//					Subject subject = subjectHandler.readASubjectFromUserInput(conn);
//					if (subject == null)
//					{
//						System.out.println("\nThere is no subject has code you entered.");
//					}
//					else
//					{
//						subject.showMe();
//					}
						
//						2.3 READS all Subject objects based on a specific subject name
////					String subjectName = "xml";
////					String subjectName = "java";
//					String subjectName = "java 1";
//					List<Subject> subjects = subjectHandler.readSubjectsBasedOnAName(conn, subjectName);
//					subjectHandler.showSubjectList(subjects);						
						
//			//		2.4 READS all Subject objects from the database
//					List<Subject> subjects = subjectHandler.readAllSubjects(conn);
//					subjectHandler.showSubjectList(subjects);
						
//						----------------------------------------------------------------------------------
						
////						3.1 UPDATES a Subject object
////						Test
//						subjectHandler.showSubjectList(subjectHandler.readAllSubjects(conn));
////						Gets an existing Subject object for testing update functionality
//						Subject subject = subjectHandler.readASubject(conn, "su001");
////						Test
//						System.out.print("\nThe object has just gotten:");
//						
////						Update the gotten Subject object
//						subjectHandler.updateASubject(conn, subject, "HIBERNATE");
////						Test
//						System.out.print("\nThe object has just gotten:");
//						subject.showMe();
////						Test
//						System.out.println("\nAfter updated:");
//						subjectHandler.showSubjectList(subjectHandler.readAllSubjects(conn));
						
////						3.2 UPDATES a Subject object based on a subject code and subject name 
////						entered by the user
//						subjectHandler.updateASubjectBasedOnUserInput(conn);
						
						
						
//						--------------------------------------------------------------------------------
						
////						Testing the functionalities creating and and saving a Subject object
//						String subjectCode = "su001";
////						String subjectCode = "su004";
						
//						Subject subject = new Subject(subjectCode, "NEW SUBJECT");
//						if (!subjectHandler.saveASubject(conn, subject))
//						{
//							char c = Console.nextChar("\nDo you want to update the name of the subject\n"
//														+ "whose subject code is "
//														+ subjectCode + " (y/n)? ");
//							if (c == 'y' || c =='Y')
//							{
//								String tempSubjectName = 
//										Console.nextString("\nEnter a new name fo the subject: ");
//								subjectHandler.updateASubject(conn, subject, tempSubjectName);
//							}
//						}
						
//						Subject subject = subjectHandler.createASubject(conn, subjectCode, "NEW SUBJECT");
//						if (subject == null)
//						{
//							System.out.println("subject is null");
//						}
//						else
//						{
//							subject.showMe();
//						}
						
//						--------------------------------------------------------------------------------
						
////						4.1 DELETES a Subject object
////						Test
//						System.out.println("\n(Test)Existing Subject objects:");
//						subjectHandler.showSubjectList(subjectHandler.readAllSubjects(conn));	
//						
//						String subjectCode = "su001"; 
////						String subjectCode = "su100"; 
////						Subject subject = new Subject(subjectCode, "ABC");
////						subjectHandler.saveASubject(conn, subject);
//						Subject subject = subjectHandler.readASubject(conn, subjectCode);
//						
////						Deletes the subject
//						subjectHandler.deleteASubject(conn, subject);
////						Set null to the variable "subject" to ensure deleting the reference 
////						to the deleted object.
//						subject = null;
//						
////						Test
//						System.out.println("\n(Test)Existing Subject objects:");
//						subjectHandler.showSubjectList(subjectHandler.readAllSubjects(conn));
						

//						
			//			4.2 DELETES all records in the Subjects table
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




























