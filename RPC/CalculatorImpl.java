import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

  public CalculatorImpl() throws RemoteException {
    super();
  }

  public int add(int x, int y) throws RemoteException {
    return x + y;
  }

  public int subtract(int x, int y) throws RemoteException {
    return x - y;
  }
}
