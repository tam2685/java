import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentService extends Remote {

    Student findStudentByCode(String studentCode) throws RemoteException;

    void addStudent(Student student) throws RemoteException;

    void updateStudent(Student student) throws RemoteException;

    void deleteStudent(String studentCode) throws RemoteException;
}
