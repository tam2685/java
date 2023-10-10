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
    public Product findProductByCode(String productCode) throws RemoteException {
        for (Product product : productList) {
            if (product.getCode().equals(productCode)) {
                return product;
            }
        }
        return null; // Return null if no matching product is found
    }
}
