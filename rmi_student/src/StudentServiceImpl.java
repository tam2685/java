import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl extends UnicastRemoteObject implements StudentService {
    private Connection connection;
    private List<Student> StudentList;

    protected StudentServiceImpl() throws RemoteException {
        super();
        String dbURL = "jdbc:mysql://localhost:3306/student_db1";
        String username = "root";
        String password = "123456";
        try {
            
            connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "SELECT code, name, score FROM students";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            StudentList = new ArrayList<>();

            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                double score = resultSet.getDouble("score");

                Student student = new Student(code, name, score);
                StudentList.add(student);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to connect to the database.");
        }
    }

    @Override
    public Student findStudentByCode(String studentCode) throws RemoteException {
        for (Student student : StudentList) {
            if (student.getCode().equals(studentCode)) {
                return student;
            }
        }
        return null; 
    }
    @Override
    public void addStudent(Student student) throws RemoteException {
        // Add student to the database and update studentList
        // Use prepared statements for SQL queries
        String insertSQL = "INSERT INTO students (code, name, score) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, student.getCode());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setDouble(3, student.getScore());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to add a student.");
        }
        studentList.add(student);
    }

    @Override
    public void updateStudent(Student student) throws RemoteException {
        // Update student in the database and update studentList
        // Use prepared statements for SQL queries
        String updateSQL = "UPDATE students SET name = ?, score = ? WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDouble(2, student.getScore());
            preparedStatement.setString(3, student.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to update the student.");
        }
        // Update the corresponding student in studentList
        for (Student existingStudent : studentList) {
            if (existingStudent.getCode().equals(student.getCode())) {
                existingStudent.setName(student.getName());
                existingStudent.setScore(student.getScore());
                break;
            }
        }
    }

    @Override
    public void deleteStudent(String studentCode) throws RemoteException {
        // Delete student from the database and studentList
        // Use prepared statements for SQL queries
        String deleteSQL = "DELETE FROM students WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, studentCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to delete the student.");
        }
        // Remove the corresponding student from studentList
        Student studentToRemove = null;
        for (Student existingStudent : studentList) {
            if (existingStudent.getCode().equals(studentCode)) {
                studentToRemove = existingStudent;
                break;
            }
        }
        if (studentToRemove != null) {
            studentList.remove(studentToRemove);
        }
    }
}
