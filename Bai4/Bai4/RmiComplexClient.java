import java.rmi.Naming;
import java.util.Scanner;

public class RmiComplexClient {
    public static void main(String[] args) {
        try {
            // Look up the remote object
            ComplexOperation complexOperation = (ComplexOperation) Naming.lookup("rmi://localhost:8000/ComplexOperation");

            Scanner scanner = new Scanner(System.in);

            // Nhập số phức thứ nhất
            System.out.println("Nhập số phức thứ nhất:");
            System.out.print("Phần thực: ");
            double real1 = scanner.nextDouble();
            System.out.print("Phần ảo: ");
            double imaginary1 = scanner.nextDouble();
            Complex num1 = new Complex(real1, imaginary1);

            // Nhập số phức thứ hai
            System.out.println("Nhập số phức thứ hai:");
            System.out.print("Phần thực: ");
            double real2 = scanner.nextDouble();
            System.out.print("Phần ảo: ");
            double imaginary2 = scanner.nextDouble();
            Complex num2 = new Complex(real2, imaginary2);

            // Thực hiện các phép toán
            Complex sum = complexOperation.add(num1, num2);
            Complex difference = complexOperation.subtract(num1, num2);
            Complex product = complexOperation.multiply(num1, num2);
            Complex quotient = complexOperation.divide(num1, num2);

            // In kết quả
            System.out.println("Tổng: " + sum);
            System.out.println("Hiệu: " + difference);
            System.out.println("Tích: " + product);
            System.out.println("Thương: " + quotient);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
