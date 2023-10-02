import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {
    List<Product> findProductByCode(Product product) throws RemoteException;
}
