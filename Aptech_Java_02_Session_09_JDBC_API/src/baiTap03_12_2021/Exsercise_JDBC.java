package baiTap03_12_2021;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Exsercise_JDBC {
	private static String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName = Students_PPDIEN";
	private static String USER_NAME = "sa";
	private static String PW;

	public static Connection getConnection(String dbURL, String userName, String pass) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbURL, userName, pass);
			System.out.println("\nConnect successfully!");
		} catch (SQLException e) {
			System.out.println("\nConnect failure!");
			e.printStackTrace();
		}
		return con;
	}

	public static void displayAllStudent(Statement st, ResultSet rs, String sql) throws SQLException {
		sql = "SELECT * FROM Student";
		rs = st.executeQuery(sql);
		System.out.println("++++ Display all student information ++++");
		System.out.printf("\n%s | %-16s | %-7s | %-12s | %s", "StuID", "Student Name", "Gender", "Birthday",
				"ID Science\n");
		System.out.print("----------------------------------------------------------------");
		while (rs.next()) {
			System.out.printf("\n%s | %-16s | %-7s | %-12s | %d", rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getDate(4), rs.getInt(5));
		}
		;

	}

	public static void updateStudent(Statement st, ResultSet rs, String sql, Scanner sc) throws SQLException {
		System.out.println("\n++++ Update student information by student ID ++++");
		int isAvailable = 0;
		String stuID;
		do {
			System.out.print("Input Students ID want to edit: ");
			stuID = sc.nextLine();
			sql = "UPDATE Student SET IDStud = '" + stuID + "' WHERE IDStud = '" + stuID + "'";
			isAvailable = st.executeUpdate(sql);
			if (isAvailable == 0) {
				System.out.println("Could not found student id: " + stuID + ". Input again!");
			}
		} while (isAvailable == 0);

		System.out.print("Input name of student " + stuID + " :");
		String name = sc.nextLine();
		sql = "UPDATE Student SET Fullname = N'" + name + "' WHERE IDStud = '" + stuID + "'";
		int numberRowsAffected = st.executeUpdate(sql);
		if (numberRowsAffected == 0) {
			System.out.println("Update failed!");
		} else {
			System.out.println("Update succesfully!");
			System.out.println("Information after update: ");
			sql = "SELECT * FROM Student WHERE IDStud = 'STU01'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("\n%s | %-16s | %-7s | %-12s | %d", rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getDate(4), rs.getInt(5));
			}
		}
	}

	public static void deleteStudent(Statement st, ResultSet rs, String sql, Scanner sc) throws SQLException {
		System.out.println("\n++++ Delete Student by student ID ++++");
		int isAvailable = 0;
		System.out.print("Input Students ID want to delete: ");
		String stuID = sc.nextLine();

		sql = "DELETE FROM Marks WHERE IDStud = '" + stuID + "'";
		int deleteCount = st.executeUpdate(sql);
		if (deleteCount != 0) {
			sql = "DELETE FROM Student WHERE IDStud = '" + stuID + "'";
			isAvailable = st.executeUpdate(sql);
			if (isAvailable == 0) {
				System.out.println("Delete failed! Could not found student id: " + stuID + ".");
			} else {
				System.out.println("Delete successfull");
			}
		} else {
			System.out.println("Delete failed! Could not found student id: " + stuID + ".");
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your SQL Server password: ");
		PW = sc.nextLine();
		try {
			Connection con = getConnection(DB_URL, USER_NAME, PW);
			Statement st = con.createStatement();
			String sql = null;
			ResultSet rs = null;
			int choice;
			do {
				System.out.println("\n======== Students management system ======");
				System.out.println("1. Show all Student information");
				System.out.println("2. Edit student information");
				System.out.println("3. Delete student");
				System.out.println("4. Exit");
				System.out.print("Choice: ");
				choice = sc.nextInt();
				sc.nextLine();
				switch (choice) {
				case 1:
					displayAllStudent(st, rs, sql);
					break;
				case 2:
					updateStudent(st, rs, sql, sc);
					break;
				case 3:
					deleteStudent(st, rs, sql, sc);
					break;
				case 4:
					System.out.println("Goodbye!");
					st.close();
					con.close();
					break;
				default:
					System.out.println("Your choice is invalid. Input again!s");
					break;
				}
			} while (choice != 4);

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}
}
