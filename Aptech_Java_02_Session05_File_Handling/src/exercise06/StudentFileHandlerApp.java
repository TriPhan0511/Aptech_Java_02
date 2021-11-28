package exercise06;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class StudentFileHandlerApp 
{
//	private static final String STUDENTS_FILE = "Students.txt";
//	private static final String MEDIUM_SCORE_FILE = "MediumScore.txt";
	private static final String STUDENTS_FILE = "src/exercise06/Students.txt";
	private static final String MEDIUM_SCORE_FILE = "src/exercise06/MediumScore.txt";
	private static Scanner sc = new Scanner(System.in);
	private static List<Student> studentList = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		StudentFileHandler.readRecords(studentList, STUDENTS_FILE);
		char choice;
		do
		{
			System.out.println("\nStudent File Handler.");
			System.out.println("1. Add a new student.");
			System.out.println("2. List all students");
			System.out.println("3. Find students who have highest medium score\n");
			System.out.println("4. Save and quit the program.\n");
			choice = Console.getChar(sc, "Enter your choice: ");
			switch (choice)
			{
				case '1':
					addStudent(studentList);
					break;
				case '2': 
					listAll(studentList);
					break;
				case '3':
					findStudentsHaveHighestScore(studentList);
					break;
				case '4':
					StudentFileHandler.saveRecords(studentList, STUDENTS_FILE);
					StudentFileHandler.saveRecords(findStudents(studentList), MEDIUM_SCORE_FILE);
					break;
				default:
					System.out.println("\nPlease choose a number from 1 - 4 only.");
			}
		} while (choice != '4');
	}
	
	private static void addStudent(List<Student> studentList)
	{
		String tempStudentID = Console.getString(sc, "\nEnter student's id: ");
		String tempName = Console.getString(sc, "Enter student's name: ");
		double tempMediumScore = Console.getDouble(sc, "Enter student's medium score: ");
		studentList.add(new Student(tempStudentID, tempName, tempMediumScore));
	}
	
	private static void listAll(List<Student> studentList)
	{
		for (Student s : studentList)
		{
			System.out.println(s.getStudentID() 
					+ " "
					+ s.getName()
					+ " " 
					+ s.getMediumScore());
		}
	}
	
	private static List<Student> findStudents(List<Student> studenList)
	{
//		Find highest score:
//		gets a list of students' score
		List<Double> scores = new ArrayList<>();
		for (Student s : studenList)
		{
			scores.add(s.getMediumScore());
		}
//		then sorts the scores list in descending order
		Collections.sort(scores, Collections.reverseOrder());
//		gets highest score which is the first item in the scores list
		double highestScore = scores.get(0);
		
//		Add students who have highest score to a list named "found"
		List<Student> found = new ArrayList<>();
		for (Student s : studenList)
		{
			if (s.getMediumScore() == highestScore)
			{
				found.add(s);
			}
		}
		return found;
	}
	
	private static void findStudentsHaveHighestScore(List<Student> studentList)
	{
		List<Student> list = findStudents(studentList);
		System.out.println("\nStudents have highest score:");
		listAll(list);
	}
}





























