package exercise06_02;

import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class consists of two static methods which are
 * used to write students' information in a list to a 
 * text file, and vice versa, read students' information
 * in a text file, then initialize Student objects from 
 * those information and add them to a list. 
 * 
 * @version 1.0 2021-11-29
 * @author Tri Phan
 *
 */
public class StudentTextFileHandler 
{
	/**
	 * Iterates a list of Student objects to get information about students
	 * then write those information to a text file.
	 * @param students A list of Student objects.
	 * @param fileName A text file.
	 */
	public void writeList(List<Student> students, String fileName)
	{
		try
		(
			FileWriter studentFile = new FileWriter(fileName);
			PrintWriter writer = new PrintWriter(studentFile);
		)
		{
			for (Student s : students)
			{
				writer.println(s.getStudentID());
				writer.println(s.getName());
				writer.println(s.getMediumScore());
			}
		}
		catch (IOException e)
		{
			System.out.println("There was a problem writing a file.");
		}
	}
	
	/**
	 * Reads line-by-line a text file to get information about students,
	 * next, uses those information to initialize Student objects,
	 * then, add those Student objects to a list.
	 * @param students A list of Student objects.
	 * @param fileName A text file.
	 */
	public void readList(List<Student> students, String fileName)
	{
		try
		(
			FileReader studentFile = new FileReader(fileName);
			BufferedReader studentStream = new BufferedReader(studentFile);
		)
		{
			String tempStudentID;
			String tempName;
			String tempStringMediumScore;
			double tempDoubleMediumScore;
			
			tempStudentID = studentStream.readLine(); // Gets the first line of the file
			while (tempStudentID != null) // A null indicates end of file
			{
				tempName = studentStream.readLine();
				tempStringMediumScore = studentStream.readLine();
				tempDoubleMediumScore = Double.parseDouble(tempStringMediumScore);
				students.add(new Student(tempStudentID, tempName, tempDoubleMediumScore));
				tempStudentID = studentStream.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Cannot open file " + fileName);
		}
		catch (IOException e)
		{
			System.out.println("There was a problem reading a file.");
		}
	}
}


























