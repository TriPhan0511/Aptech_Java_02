package exercise06_02;

import java.util.List;
import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args)
	{
		StudentTextFileHandler fileHandler = new StudentTextFileHandler();
		String fileName = "Students.txt";
		List<Student> students = new ArrayList<>();
		fileHandler.readList(students, fileName);
		
		char choice;
		do
		{
			System.out.println("\nStudent Text File Handler.");
			System.out.println("1. Add a new student.");
			System.out.println("2. List all students.");
//			System.out.println("3. Find all students who have highest score.");
			System.out.println("3. Save all works and quit the program.");
			choice = Console.nextChar("Enter your choice: ");
			switch (choice)
			{
				case '1': 
					addStudent(students);
					break;
				case '2':
					listAll(students);
					break;
				case '3':
					fileHandler.writeList(students, fileName);
					break;
				default:
					System.out.println("Please enter a number between 1 - 3.");
			}
		} while (choice != '3');
	}
	
	private static void addStudent(List<Student> students)
	{
		String tempStudentID = 
				Console.nextString("Enter student id: ");
		String tempName = 
				Console.nextString("Enter student's name: ");
		double tempMediumScore = 
				Console.nextDouble("Enter student's medium score: ");
		students.add(new Student(tempStudentID, tempName, tempMediumScore));
	}
	
	private static void listAll(List<Student> students)
	{
		for (Student s :  students)
		{
			System.out.printf("%s %s %.1f\n", 
					s.getStudentID(), s.getName(), s.getMediumScore());
		}
	}
}
