import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {
    private Connection connection;
    private List<Product> productList;

    protected ProductServiceImpl() throws RemoteException {
        super();
        String dbURL = "jdbc:mysql://localhost:3306/product_db";
        String username = "root";
        String password = "123456";
        try {
            
            connection = DriverManager.getConnection(dbURL, username, password);

            String sql = "SELECT code, name, unit, price FROM products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            productList = new ArrayList<>();

            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String unit = resultSet.getString("unit");
                double price = resultSet.getDouble("price");

                Product product = new Product(code, name, unit, price);
                productList.add(product);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to connect to the database.");
        }
    }

    @Override
    public List<Product> findProductByCode(Product product) throws RemoteException {
        List<Product> result = new ArrayList<>();
        for (Product p : productList) {
            if (p.getCode().equals(product.getCode())) {
                result.add(p);
            }
        }
        return result;
    }
}
