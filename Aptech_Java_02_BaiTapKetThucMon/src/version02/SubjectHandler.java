package version02;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectHandler 
{
	private static DatabaseHandler dbHandler = new DatabaseHandler();
	
	/* Specific operations for SubjectTester class */
	
	/**
	 * Gets a subject name entered by the user and search subjects
	 *  base on the entered subject name.
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	public void searchSubjectsBasedOnAnEnteredSubjectName(Connection conn)
		throws SQLException
	{
		List<Subject> foundSubjects = readSubjectsBasedOnAnEnteredName(conn);
		int size = foundSubjects.size();
//		if (foundSubjects.size() == 0)
		if (size == 0)
		{
			System.out.println("\n*** Result: Not found.");
		}
		else
		{	
			System.out.printf("\n*** Result: Found %d subject(s):\n", size);
			showSubjectList(foundSubjects);
		}
	}
	
	/**
	 * Gets a subject code entered by the user and search for a subject
	 *  base on the entered subject code.
	 * If found, prints out the subject code and subject name of found subject.
	 * If not found, prints outs the message which represents not found.
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	public void searchASubjectBasedOnAnEnteredSubjectCode(Connection conn)
		throws SQLException
	{
		Subject subject = readASubjectBasedOnAnEnteredSubjectCode(conn);
		if (subject == null)
		{
			System.out.println("\n*** Result: Not found.");
		}
		else
		{
			System.out.println("\n*** Result: Found subject:");
			subject.showMeWithoutScore();
		}
	}
	
	/**
	 * Print out all of subjects to the console.
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	public void showAllSubjects(Connection conn)
		throws SQLException
	{
		List<Subject> subjects = readAllSubjects(conn);
		showSubjectList(subjects);
	}
	
//	---------------------------------------------------------------------------
	
	/* CRUD (Create, Read, Update, Delete) operations */
	
	/**
	 * Deletes a Subject object.
	 * @param conn The database connection.
	 * @param subject A Subject object.
	 * @throws SQLException
	 */
	public void deleteASubject(Connection conn, Subject subject)
		throws SQLException
	{
		if (subject == null)
		{
			System.out.println("\nNo subject was deleted because\n"
					+ "the subject you want to delete does not exist.");
		}
		else
		{
			String subjectCode = subject.getCode();
			String subjectName = subject.getName();
			
//			Deletes a record in the Subjects table
			dbHandler.deleteASubjectRecordBasedOnSubjectCode(conn, subject.getCode());
			
//			Note: Deletes the Subject object
//			On the calling method we must set null to the variable
//			that was referencing to this Subject object. 
			
			System.out.printf("\nThe subject (code: %s, name: %s) was deleted.\n",
								subjectCode, subjectName);
		}
	}
	
	/**
	 * Updates the name of Subject objects based on an entered subject name.
	 * @param conn The database connection.
	 * @throws SQLException
	 */
	public void updateSubjectsBasedOnAnEnteredSubjectName(Connection conn)
		throws SQLException
	{
//		Gets a subject name from user input
		String tempSubjectName = Console.nextString("\nEnter the name of subject you want to update: ");
		String tempNewSubjectName;
		List<Subject > subjects = readSubjecstBasedOnASubjectName(conn, tempSubjectName);
		if (subjects.size() == 0)
		{
			System.out.println("\nCurrently there is no subject has name " + tempSubjectName);
		}
		else
		{
			tempNewSubjectName = Console.nextString("\nEnter a new subject name: ");
			for (Subject item: subjects)
			{
				updateASubject(conn, item, tempNewSubjectName);
			}
		}
	}
	
	/**
	 * Updates the name of a Subject object based on an entered subject code.
	 * @param conn
	 * @throws SQLException
	 */
	public void updateASubjectBasedOnAnEnteredSubjectCode(Connection conn)
		throws SQLException
	{
		String tempSubjectCode;
		String tempSubjectName;
		Subject tempSubject = null;
		
//		Gets a subject code from user input
		while (true)
		{
			tempSubjectCode = Console.nextString("\nEnter a subject code: ");
			if (tempSubjectCode.length() > 5)
			{
				System.out.println("\nThe subject code should be equal to or less than 5.");
				System.out.println("Please re-enter another subject code.");
			}
			else
			{
				break;
			}
		}
		
//		Based on the entered subject code, gets a Subject object
//		then check if that Subject object exists or not.
//		If the Subject object exists, ask the user for a new subject name and update the object.
//		If the Subject object does not exist, ask the user for adding a new Subject object.
		tempSubject = readASubjectBasedOnASubjectCode(conn, tempSubjectCode);
		if (tempSubject != null)
		{
			System.out.println("\nCurrent details of the subject you want to update:");
			System.out.println("code: " + tempSubject.getCode() 
								+ ", name: " + tempSubject.getName());
			tempSubjectName = 
					Console.nextString("\nEnter a new name for the subject you want to update: ");
			updateASubject(conn, tempSubject, tempSubjectName);
		}
		else
		{
			char choice = Console.nextChar("\nNo subject has code " 
											+ tempSubjectCode 
											+ ".\nDo you want to create a new subject (y/n)? ");
			if (choice == 'y' || choice == 'Y')
			{
				tempSubjectName = Console.nextString("\nEnter a subject name: ");
				saveASubject(conn, new Subject(tempSubjectCode, tempSubjectName));
			}
		}
	}
	/**
	 * Updates the name of a Subject object.
	 * @param conn
	 * @param subject
	 * @throws SQLException
	 */
	public void updateASubject(
			Connection conn, Subject subject, String newSubjectName)
		throws SQLException
	{
		String subjectCode = subject.getCode();
//		Updates the subject name of the record in the Subjects table.
		dbHandler.updateASubjectRecord(conn, subjectCode, newSubjectName);
		
//		Updates the name the current Subject object
		subject.setName(newSubjectName);

		System.out.println("\nA subject was updated.");
		System.out.println("Details: code: " + subject.getCode() 
		+ ", name: " + subject.getName());
	}
	
	/**
	 * Fetches all records from the Subjects table and initialize Subject objects.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Subject> readAllSubjects(Connection conn)
		throws SQLException
	{
		List<Subject> subjects = new ArrayList<>();
		List<String> subjectRecords = dbHandler.fetchAllSubjectRecords(conn);
		int dashPos;
		String tempSubjectCode;
		String tempSubjectName;
		for (String item : subjectRecords)
		{
//				A sample of a subject record in the subjectRecords list: "su001-XML"
			dashPos = item.indexOf("-");
			tempSubjectCode = item.substring(0, dashPos);
			tempSubjectName = item.substring(dashPos + 1);
			subjects.add(new Subject(tempSubjectCode, tempSubjectName));
		}
		return subjects;
	}
	
	/**
	 * Gets a subject name from the user input, then base on that subject name,
	 *  fetches Subject objects from the Subjects table in the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Subject> readSubjectsBasedOnAnEnteredName(Connection conn)
		throws SQLException
	{
		String tempSubjectName = 
				Console.nextString("\nEnter a subject name(try entering java): ");
		return readSubjectsBasedOnAName(conn, tempSubjectName);
	}
	
	/**
	 * Fetches the Subject objects have same subject name
	 *  based on a specific subject name.
	 * @param conn
	 * @param subjectName
	 * @return
	 * @throws SQLException
	 */
	public List<Subject> readSubjectsBasedOnAName(
			Connection conn, String subjectName)
		throws SQLException
	{
		List<Subject> subjects = new ArrayList<>();
		String tempSubjectCode;
		String tempSubjectName;
		int dashPos;
		
		List<String> subjectCodesAndSubjectNames = 
				dbHandler.fetchSubjectCodesAndSubjectNamesBasedOnName(conn, subjectName);
		for (String item: subjectCodesAndSubjectNames)
		{
//			A sample of an item: "su002-Java 1"
			dashPos = item.indexOf("-");
			tempSubjectCode = item.substring(0, dashPos);
			tempSubjectName = item.substring(dashPos +1);
			subjects.add(new Subject(tempSubjectCode, tempSubjectName));
		}
		
		return subjects;
	}
	
	/**
	 * Fetches Subject objects have a specific subject name.
	 * @param conn The database connection.
	 * @param subjectName A string represents a specific subject name.
	 * @return
	 * @throws SQLException
	 */
	public List<Subject> readSubjecstBasedOnASubjectName(Connection conn, String subjectName)
		throws SQLException
	{
		List<Subject> list = new ArrayList<>();
		Subject tempSubject;
		List<String> subjectCodes = dbHandler.fetchSubjectCodesBasedOnASubjectName(conn, subjectName);
		for (String item: subjectCodes)
		{
			tempSubject = new Subject(item, subjectName);
			list.add(tempSubject);
		}
		return list;
	}
	
	/**
	 * Gets a subject code from user input, then base on that subject code,
	 * fetches a Subject object from the Subjects table in the database.
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Subject readASubjectBasedOnAnEnteredSubjectCode(Connection conn)
		throws SQLException
	{
		String tempSubjectCode = 
				Console.nextString("\nEnter a subject code(try entering su001): ");
		return readASubjectBasedOnASubjectCode(conn, tempSubjectCode);
	}
	
	/**
	 * Fetches a record from a Subjects table based on a subject code
	 *  and initialize a Subject object.
	 * @param conn The database connection.
	 * @param subjectCode A string represents a subject code.
	 * @return A Subject object.
	 * @throws SQLException
	 */
	public Subject readASubjectBasedOnASubjectCode(Connection conn, String subjectCode)
		throws SQLException
	{
		Subject tempSubject = null;
		List<String> list = dbHandler.fetchSubjectRecords(conn, subjectCode);
		if (list.size() > 0)
		{
			tempSubject = new Subject(list.get(0), list.get(1));
		}
		return tempSubject;
	}
	
	/**
	 * Gets data from user input, next creates a Subject object from 
	 *  that data, then saves that Subject object into the database.
	 * @param conn The database connection.
	 * @return A Subject object.
	 */
	public Subject createASubjectFromUserInput(Connection conn)
	{
		List<String> existingSubjectCodes;
		Subject tempSubject;
		String tempSubjectCode;
		String temmpSubjectName;
		
		try
		{
//			Fetches all of the subject codes from the Subjects table.
			existingSubjectCodes = dbHandler.fetchSubjectCodes(conn);
			
//			Validate the entered subject code
//			A subject code must have the length is equal to or less than 5,
//			and it does not exist in the Subjects table in the database.
			while (true)
			{
				 tempSubjectCode = Console.nextString("\nEnter a subject code: ");
				 if (tempSubjectCode.length() > 5)
				 {
					 System.out.println("\nThe subject code length should be equal to or less than 5.");
					 System.out.println("Please re-enter another subject code.");
				 }
				 else if (existingSubjectCodes.indexOf(tempSubjectCode) != -1) 
				 {
					 System.out.println("\nThe subject code you entered exists in the Subjects table in the database.");
					 System.out.println("Please re-enter another subject code.");
				 }
				 else
				 {
					 break;
				 }
			}
			temmpSubjectName = Console.nextString("Enter a subject name: ");
			tempSubject = new Subject(tempSubjectCode, temmpSubjectName);
			saveASubject(conn, tempSubject);			
		}
		catch (SQLException e)
		{
			tempSubject = null;
		}
		return tempSubject;
	}
	
	/**
	 * Creates a new Subject object from two Strings, 
	 * 	then save the newly created object to the database,
	 * 	finally, returns the newly created object.
	 * @param conn
	 * @param subjectCode
	 * @param subjectName
	 * @return
	 * @throws SQLException
	 */
	public Subject createASubject(Connection conn, String subjectCode, String subjectName)
	{
//		Creates a Subject object
		Subject subject = new Subject(subjectCode, subjectName);			
//		Save the newly created object to the database
		if (saveASubject(conn, subject))		
		{
			return subject;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Save a Subject object to the database.
	 * @param conn
	 * @param subject
	 * @throws SQLException
	 */
	public boolean saveASubject(Connection conn, Subject subject)
	{
		boolean saved = false;
		try
		{
			dbHandler.insertASubjectRecord(conn, subject.getCode(), subject.getName());
			System.out.println("\nA new subject was saved.");
			System.out.println("Details: Subject code: " + subject.getCode() 
								+ ", Subject name: " + subject.getName());
			saved = true;
		}
		catch (SQLException e)
		{
			System.out.println("\nCan not save the subject because a subject with\n"
					+ "this subject code existing in the database.");
		}
		return saved;
	}
	
	/**
	 * Show the content of a list containing Subject objects.
	 * @param subjects
	 */
	public void showSubjectList(List<Subject> subjects)
	{
		int counter = 0;
		int size = subjects.size();
		if (size == 0)
		{
			System.out.println("\nCurrently there is no subject record in the database.");
		}
		else
		{
			String s1 = size == 1 ? "is" : "are";
			String s2 = size == 1 ? "subject" : "subjects";
			System.out.printf("\nCurrently there %s %d %s:\n",
								s1, size, s2);
			for (Subject item : subjects)
			{
				counter++;
				System.out.printf("%d. ", counter);
				item.showMeWithoutScore();
			}
		}
	}
}
