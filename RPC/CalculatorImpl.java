import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Remote object implementation
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

  // Constructor must throw RemoteException
  public CalculatorImpl() throws RemoteException {
    super();
  }

  // Implementation of the add method
  public int add(int x, int y) throws RemoteException {
    return x + y;
  }

  // Implementation of the subtract method
  public int subtract(int x, int y) throws RemoteException {
    return x - y;
  }
}
