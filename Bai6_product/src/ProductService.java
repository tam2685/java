import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProductService extends Remote {

    Product findProductByCode(String productCode) throws RemoteException;
}