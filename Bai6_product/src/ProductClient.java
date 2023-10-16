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
        
            ProductService productService = (ProductService) Naming.lookup("rmi://localhost:3456/ProductService");
            Product product = productService.findProductByCode(productCode);
        
            if (product != null) {
                System.out.println("Code: " + product.getCode());
                System.out.println("Name: " + product.getName());
                System.out.println("Unit: " + product.getUnit());
                System.out.println("Price: " + product.getPrice());
                System.out.println("------------");
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
