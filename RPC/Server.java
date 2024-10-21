import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

  public static void main(String[] args) {
    try {
      CalculatorImpl calculator = new CalculatorImpl();

      Naming.rebind("CalculatorService", calculator);

      System.out.println("Calculator Service is running...");
    } catch (Exception e) {
      System.out.println("Server exception: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
