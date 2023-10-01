import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerService extends Remote {
    String findCustomerNameById(int customerId) throws RemoteException;
}
