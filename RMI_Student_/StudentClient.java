import java.rmi.Naming;
import java.util.Scanner;

public class StudentClient {
    public static void main(String[] args) {
        try {
            StudentInfo studentInfo = (StudentInfo) Naming.lookup("rmi://localhost:1234/StudentInfo");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to search: ");
            String studentId = scanner.nextLine();

            Student student = studentInfo.findStudentById(studentId);

            if (student != null) {
                System.out.println("Student found:");
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("Student Name: " + student.getStudentName());
                System.out.println("Student Class: " + student.getStudentClass());
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
