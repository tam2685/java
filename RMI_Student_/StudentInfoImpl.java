import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class StudentInfoImpl extends UnicastRemoteObject implements StudentInfo {
    private Connection connection;

    public StudentInfoImpl() throws RemoteException {
        super();
        initializeDatabaseConnection();
    }

    private void initializeDatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/student_db";
        String username = "root";
        String password = "123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findStudentById(String studentId) throws RemoteException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM SV_Data WHERE id = ?");
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getString("id"));
                student.setStudentName(resultSet.getString("student_name"));
                student.setStudentClass(resultSet.getString("class"));
                return student;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
