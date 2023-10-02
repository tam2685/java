import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ProductServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(3456);
            ProductService productService = new ProductServiceImpl();
            Naming.rebind("rmi://localhost:3456/ProductService", productService);
            System.out.println("Server is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
