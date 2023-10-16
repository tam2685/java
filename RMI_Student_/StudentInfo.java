import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentInfo extends Remote {
    Student findStudentById(String studentId) throws RemoteException;
}
