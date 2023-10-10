import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ScoreService extends Remote {
    Student getScoresByStudentCode(String studentCode) throws RemoteException;
    Subject getScoresBySubjectCode(String subjectCode) throws RemoteException;
}
