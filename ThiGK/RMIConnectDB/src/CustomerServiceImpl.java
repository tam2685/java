import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl extends UnicastRemoteObject implements CustomerService {
    private Connection conn;

    public CustomerServiceImpl() throws RemoteException {
        try {
            // Kết nối vào cơ sở dữ liệu MySQL (thay thế thông tin kết nối của bạn)
            String url = "jdbc:mysql://localhost:3306/customer_db";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String findCustomerNameById(int customerId) throws RemoteException {
        String query = "SELECT name FROM customer WHERE customerID = ?";
        String customerName = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.err.println("Database query error: " + e.toString());
            e.printStackTrace();
        }

        return customerName;
    }
}
