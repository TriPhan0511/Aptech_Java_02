package exercise06;

import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/* Sample text file format:
   s1
   Alex Ferguson
   5.5
   s2
   Brad Pitt
   9.5
 */

/**
 * This class has some static methods which are used to
 * save Student's information to a text file, 
 * and read student's information from a text file.
 * @version 1.0 2021-11-28
 * @author Tri Phan
 */
public class StudentFileHandler 
{
//	private static final String FILE_PATH = "Students.txt";
	
	/**
	 * Save a student's information to the text file named "Students.txt"
	 * @param studentID A student's id.
	 * @param name A student's name.
	 * @param mediumScore A student's medium score.
	 * @param studentList A list of students.
	 * @param filePath A string represents a text file.
	 */
	public static void saveRecords(List<Student> studentList, String filePath) 
	{
//		Uses "try-with-resources"
		try
		(
			FileWriter studentFile = new FileWriter(filePath);
			PrintWriter writer = new PrintWriter(studentFile);
		)
		{
			for (Student s : studentList)
			{
				writer.println(s.getStudentID());
				writer.println(s.getName());
				writer.println(s.getMediumScore());
			}
		}
		catch (IOException e)
		{
			System.out.println("There was a problem when writing to the text file.");
		}
	}
	
	/**
	 * Reads students' information from the text file named "Students.txt",
	 * next parse that information to Student objects,
	 * then store that Student objects to a studentList.
	 * @param studentList A list which is used to store Student objects.
	 * @param filePath A string represents a text file.
	 */
//	public static void readRecords(List<Student> studentList)
	public static void readRecords(List<Student> studentList, String filePath)
	{
		String tempStudentID;
		String tempName;
		String tempStringMediumScore;
		double tempDoubleMediumScore;
		
//		Uses "try-with-resources"
		try
		(
			FileReader studentFile = new FileReader(filePath);
			BufferedReader studentStream = new BufferedReader(studentFile);
		)
		{
//			Reads the first line of the file.
			tempStudentID = studentStream.readLine();
			while (tempStudentID != null) // A null indicates end of file.
			{
				tempName = studentStream.readLine();
				tempStringMediumScore = studentStream.readLine();
				tempDoubleMediumScore = Double.parseDouble(tempStringMediumScore);
				studentList.add(new Student(tempStudentID, tempName, tempDoubleMediumScore));
				tempStudentID = studentStream.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("There was a problem when opening the file " + filePath);
		}
		catch (IOException e)
		{
			System.out.println("There was a problem when reading file.");
		}
	}
}




































