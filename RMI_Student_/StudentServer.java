import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StudentServer {
    public static void main(String[] args) {
        try {
            StudentInfo studentInfo = new StudentInfoImpl();

            // Bước 1: Tạo registry tại cổng 1234
            LocateRegistry.createRegistry(1234);

            // Bước 2: Đăng ký Remote Object với tên "StudentInfo"
            Naming.rebind("rmi://localhost:1234/StudentInfo", studentInfo);

            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
