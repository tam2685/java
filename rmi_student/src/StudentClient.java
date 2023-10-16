import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class StudentClient {
    public static void main(String[] args) {
        try {
            // Yêu cầu người dùng nhập mã sản phẩm
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter code: ");
            String studentCode = scanner.nextLine();
        
            StudentService studentService = (StudentService) Naming.lookup("rmi://localhost:3456/StudentService");
            Student student = studentService.findStudentByCode(studentCode);
        
            if (student != null) {
                System.out.println("Code: " + student.getCode());
                System.out.println("Name: " + student.getName());
                System.out.println("Score: " + student.getScore());
                System.out.println("------------");
            } else {
                System.out.println("student not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
