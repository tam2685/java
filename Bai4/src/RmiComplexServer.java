import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiComplexServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            ComplexOperation complexOperation = new ComplexOperationImpl();
            
            // Start the RMI registry on port 8000
            LocateRegistry.createRegistry(8000);
            
            // Bind the remote object to the RMI Registry
            Naming.rebind("rmi://127.0.0.1:8000/ComplexOperation", complexOperation);
            
            System.out.println(">>>>>INFO: RMI Server started!!!!!!!!");

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
