import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

  public static void main(String[] args) {
    try {
      // Create a remote object instance
      CalculatorImpl calculator = new CalculatorImpl();

      // Bind the remote object to the RMI registry using a specific name
      Naming.rebind("CalculatorService", calculator);

      System.out.println("Calculator Service is running...");
    } catch (Exception e) {
      System.out.println("Server exception: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
