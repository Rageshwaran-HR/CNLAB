import java.rmi.Naming;

public class Client {

  public static void main(String[] args) {
    try {
      Calculator calculator = (Calculator) Naming.lookup(
        "rmi://localhost/CalculatorService"
      );

      int sum = calculator.add(5, 3);
      int difference = calculator.subtract(10, 4);

      System.out.println("Sum: " + sum);
      System.out.println("Difference: " + difference);
    } catch (Exception e) {
      System.out.println("Client exception: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

