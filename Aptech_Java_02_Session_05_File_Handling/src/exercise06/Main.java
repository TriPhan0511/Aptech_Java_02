package exercise06;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

/*
 * Sample text file which contains information of all students:
 * 
 * s1
 * Alex Ferguson 
 * 7.5
 * s2
 * Hillary Duff
 * 5.8
 * s3
 * White Rose
 * 7.5
 * 
 */

public class Main 
{
	private static final String STUDENTS_FILE = "src/exercise06/Students.txt";
	private static final String HIGHEST_SCORE = "src/exercise06/MediumScore.txt";
	
	public static void main(String[] args)
	{
//		Instantiates a StudentTextFileHandler object
		StudentTextFileHandler fileHandler = new StudentTextFileHandler();
		
//		Creates an empty list to hold students
		List<Student> students = new ArrayList<>();
		
//		Read the list from file when the program starts
		fileHandler.readList(students, STUDENTS_FILE);
		
		char choice;
//		Menu option
		do
		{
			System.out.println("\nStudent Text File Handler.");
			System.out.println("1. Add a new student.");
			System.out.println("2. List all students.");
			System.out.println("3. Find all students who have highest score.");
			System.out.println("4. Save all works and quit the program.");
			choice = Console.nextChar("\nEnter your choice: ", "Invalid choice.");
			switch (choice)
			{
				case '1': 
					addStudent(students);
					break;
					
				case '2':
					listAll(students);
					break;
					
				case '3':
					printStudentsHaveHighestScore(students);
					break;
					
				case '4':
//					Write to files
					fileHandler.writeList(students, STUDENTS_FILE);
					fileHandler.writeList(getStudentsHaveHighestScore(students), HIGHEST_SCORE);
					break;
					
				default:
					System.out.println("Please enter a number between 1 - 4.");
			}
		} while (choice != '4');
	}
	
	/**
	 * Gets information about a student from the user,
	 * next, initialize a Student object from those information,
	 * then, add that Student object to a list.
	 * @param students A list of Student objects.
	 */
	private static void addStudent(List<Student> students)
	{
		System.out.println("\nLet's enter information about a student");
		String tempStudentID = 
				Console.nextString("\nStudent's id: ", "Student ID should not be empty.");
		String tempName = 
				Console.nextString("Student's name: ", "Name should not be empty.");
		double tempMediumScore = 
				Console.nextDouble("Student's medium score: ", "Invalid score.");
		students.add(new Student(tempStudentID, tempName, tempMediumScore));
	}
	
	/**
	 * Prints out information about all of students in a list.
	 * @param students A list of Student objects.
	 */
	private static void listAll(List<Student> students)
	{
		if (students.size() == 0)
		{
			System.out.println("\nThere is no information about students.");
		}
		else
		{
			System.out.println("\nInformation about students:\n");
			for (Student s :  students)
			{
				System.out.printf("%s %s %.1f\n", 
						s.getStudentID(), s.getName(), s.getMediumScore());
			}
		}
		
	}
	
	/**
	 * Gets students who have highest score
	 * @param students A list of Student objects.
	 * @return A list of students who have highest score.
	 */
	private static List<Student> getStudentsHaveHighestScore(List<Student> students)
	{
		List<Double> scores = new ArrayList<>();
		List<Student> found = new ArrayList<>();
		
		if (students.size() != 0)
		{
//			Gets a list of students' score
			for (Student s : students)
			{
				scores.add(s.getMediumScore());
			}
//			Sorts the scores list in descending order
			Collections.sort(scores, Collections.reverseOrder());
//			Gets highest score which is the first item in the scores list
			double highestScore = scores.get(0);
//			Adds students who have highest score to a list named "found"
//			and returns the "found" list.
			for (Student s : students)
			{
				if (s.getMediumScore() == highestScore)
				{
					found.add(s);
				}
			}
		}
		return found;
	}
	
	/**
	 * Prints out students who have highest score to the console.
	 * @param students A list of Student objects.
	 */
	private static void printStudentsHaveHighestScore(List<Student> students)
	{
		List<Student> list = getStudentsHaveHighestScore(students);
		System.out.print("\nStudents have highest score.");
		listAll(list);
	}
}























