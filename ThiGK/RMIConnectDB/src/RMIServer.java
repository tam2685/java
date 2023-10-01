import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            CustomerService customerService = new CustomerServiceImpl();

            // Đăng ký dịch vụ RMI với cổng 1099
            Registry registry = LocateRegistry.createRegistry(8088);
            registry.rebind("CustomerService", customerService);

            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
