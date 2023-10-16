import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8088);
            CustomerService customerService = (CustomerService) registry.lookup("CustomerService");

            System.out.print("Nhập ID của khách hàng: ");
            int customerId = scanner.nextInt();

            
            String customerName = customerService.findCustomerNameById(customerId);

            System.out.println("Customer name for ID " + customerId + ": " + customerName);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
