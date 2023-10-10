import java.rmi.Naming;
import java.util.Scanner;
import java.util.List;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Look up the remote object by its name
            String registryURL = "rmi://localhost:3456/ScoreService";
            ScoreService scoreService = (ScoreService) Naming.lookup(registryURL);

            // Thực hiện nhập mã sinh viên hoặc mã môn học và gọi phương thức tương ứng
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Student code or Subject Code: ");
            String code = scanner.nextLine();

            // Gọi phương thức và hiển thị kết quả
            Student student = scoreService.getScoresByStudentCode(code);
            Subject subject = scoreService.getScoresBySubjectCode(code);

            if (student != null) {
                System.out.println("Students:");
                System.out.println("Student Code: " + student.getStudentCode());
                System.out.println("Student Name: " + student.getStudentName());
                System.out.println("***");
                // Display scores if available
                List<Score> scores = student.getScores();
                if (scores != null && !scores.isEmpty()) {
                    System.out.println("Scores:");
                    for (Score score : scores) {
                        System.out.println("Subject code: " + score.getSubjectCode()); 
                        System.out.println("Subject name: " + score.getSubjectName()); 
                        System.out.println("Score: " + score.getScore());
                        System.out.println("---");
                    }
                } else {
                    System.out.println("Score is not found");
                }
            } 
            else if (subject != null) {
                System.out.println("Subjects:");
                System.out.println("Subject code: " + subject.getSubjectCode());
                System.out.println("Subject name: " + subject.getSubjectName());
                System.out.println("***");
                List<Score> scores = subject.getScores();
                if (scores != null && !scores.isEmpty()) {
                    System.out.println("Scores:");
                    for (Score score : scores) {
                        System.out.println("Student code: " + score.getStudentCode());
                        // Lấy thông tin sinh viên từ cơ sở dữ liệu dựa trên studentCode
                        Student students = scoreService.getScoresByStudentCode(score.getStudentCode());
                        if (students != null) {
                            System.out.println("Student name: " + students.getStudentName());
                        } else {
                            System.out.println("Student is not found");
                        }
                        System.out.println("Score: " + score.getScore());
                        System.out.println("---");
                    }
                } else {
                    System.out.println("Score is not found");
                }
            }else {
                System.out.println("Not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
