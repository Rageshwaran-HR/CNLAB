import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote interface for a calculator
public interface Calculator extends Remote {
    // Method to add two numbers (remote method)
    public int add(int x, int y) throws RemoteException;

    // Method to subtract two numbers (remote method)
    public int subtract(int x, int y) throws RemoteException;
}
