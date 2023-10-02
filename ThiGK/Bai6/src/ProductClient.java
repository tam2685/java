import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ProductClient {
    public static void main(String[] args) {
        try {
            // Yêu cầu người dùng nhập mã sản phẩm
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter code: ");
            String productCode = scanner.nextLine();
            Product product = new Product(productCode, "", "", 0.0);

            ProductService productService = (ProductService) Naming.lookup("rmi://localhost:3456/ProductService");
            List<Product> products = productService.findProductByCode(product);

            for (Product p : products) {
                System.out.println("Code: " + p.getCode());
                System.out.println("Name: " + p.getName());
                System.out.println("Unit: " + p.getUnit());
                System.out.println("Price: " + p.getPrice());
                System.out.println("------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
