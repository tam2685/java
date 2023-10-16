import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StudentServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(3456);
            StudentService studentService = new StudentServiceImpl();
            Naming.rebind("rmi://localhost:3456/StudentService", studentService);
            System.out.println("Server is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
