import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PrimeNumberClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IPrimeNumberChecker primeNumberChecker = (IPrimeNumberChecker) registry.lookup("PrimeNumberChecker");
            System.out.println("nhap so can kiem tra :");
            Scanner scanner = new Scanner(System.in);

            int numberToCheck = scanner.nextInt();
            boolean isPrime = primeNumberChecker.isPrime(numberToCheck);

            scanner.close();
            if (isPrime) {
                System.out.println(numberToCheck + " la so Nguyen To");
            } else {
                System.out.println(numberToCheck + " khong phai la so Nguyen To");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
