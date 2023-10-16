import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceImpl extends UnicastRemoteObject implements ScoreService {
    private Connection connection;

    public ScoreServiceImpl() throws RemoteException {
        super();
        // Khởi tạo kết nối đến cơ sở dữ liệu MySQL ở đây
        String dbURL = "jdbc:mysql://localhost:3306/student_db";
        String username = "root";
        String password = "123456";
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
public Student getScoresByStudentCode(String studentCode) throws RemoteException {
    Student student = null;
    try {
        // Truy vấn thông tin điểm của sinh viên dựa trên mã sinh viên
        String query = "SELECT student_name, subject.subject_code, subject_name, subject_score " +
                       "FROM student " +
                       "INNER JOIN score ON student.student_code = score.student_code " +
                       "INNER JOIN subject ON score.subject_code = subject.subject_code " +
                       "WHERE student.student_code = ?";
    
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, studentCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            String studentName = resultSet.getString("student_name");
            student = new Student(studentCode, studentName, new ArrayList<>());

            List<Score> scores = new ArrayList<>();
            do {
                String subjectCode = resultSet.getString("subject_code");
                String subjectName = resultSet.getString("subject_name");
                double score = resultSet.getDouble("subject_score");
                scores.add(new Score(studentCode, subjectCode, subjectName, score));
            } while (resultSet.next());
            student.setScores(scores);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return student;
}

@Override
public Subject getScoresBySubjectCode(String subjectCode) throws RemoteException {
    Subject subject = null;
    try {
        // Truy vấn thông tin điểm của các sinh viên trong môn học dựa trên mã môn học
        String query = "SELECT student.student_code, student_name, subject_name, subject_score " +
                       "FROM student " +
                       "INNER JOIN score ON student.student_code = score.student_code " +
                       "INNER JOIN subject ON score.subject_code = subject.subject_code " +
                       "WHERE subject.subject_code = ?";
        
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subjectCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            String subjectName = resultSet.getString("subject_name");
            subject = new Subject(subjectCode, subjectName, new ArrayList<>());
            List<Score> scores = new ArrayList<>();
            do {
                String studentCode = resultSet.getString("student_code");
                String studentName = resultSet.getString("student_name");
                double score = resultSet.getDouble("subject_score");
                scores.add(new Score(studentCode, subjectCode, subjectName, score));
            } while (resultSet.next());
            subject.setScores(scores);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return subject;
}

}
