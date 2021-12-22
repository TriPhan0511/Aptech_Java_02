package version02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SubjectTester_2 
{
	public static void main(String[] args)
	{
		SubjectHandler subjectHandler = new SubjectHandler();
		DatabaseHandler dbHandler = new DatabaseHandler();
		
		try (Connection conn = dbHandler.getConnection())
		{
			char choice;
			do
			{
//				Menu options
				System.out.println("\n***** Subject Handler Tester *****\n");
				System.out.println("1. Create a new subject.");
				System.out.println("2. List all of subjects.");
				System.out.println("3. Search a subject based on an entered subject code.");
				System.out.println("4. Search subjects based on an entered subject name.");
				System.out.println("5. Update a subject with a new name based on an entered subject code.");
				System.out.println("6. Update a subject with a new name based on an entered subject name.");
				System.out.println("7. Delete a subject based on an entered subject code.");
				System.out.println("8. Delete a subject based on an entered name.");
				System.out.println("9. Quit the program.");
				choice = Console.nextChar("\nEnter your choice: ");
				
				switch (choice)
				{
				case '1':
					subjectHandler.createASubjectFromUserInput(conn);
					break;
				case '2':
					subjectHandler.showAllSubjects(conn);
					break;
				case '3':
					subjectHandler.searchASubjectBasedOnAnEnteredSubjectCode(conn);
					break;
				case '4':
					subjectHandler.searchSubjectsBasedOnAnEnteredSubjectName(conn);
					break;
				case '5':
					subjectHandler.updateASubjectBasedOnAnEnteredSubjectCode(conn);
					break;
				case '6':
					subjectHandler.updateSubjectsBasedOnAnEnteredSubjectName(conn);
					break;
				case '7':
//					TODO
					break;
				case '8':
//					TODO
					break;
				case '9':
					break;
				default:
					System.out.println("\nPlease enter a number between 1 and 9.");
				}
			} while (choice != '9');
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





















