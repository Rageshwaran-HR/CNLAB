import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class PingSimulator {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter IP address or hostname to ping: ");
    String ipAddress = scanner.nextLine();

    try {
      InetAddress inet = InetAddress.getByName(ipAddress);

      System.out.println("Pinging " + ipAddress + " with 32 bytes of data:");

      // Send ping requests 4 times (like default in actual ping command)
      for (int i = 0; i < 4; i++) {
        long startTime = System.currentTimeMillis(); // Capture start time

        // Send ping request and check if host is reachable (timeout set to 1000ms)
        if (inet.isReachable(1000)) {
          long endTime = System.currentTimeMillis(); // Capture end time
          long rtt = endTime - startTime;
          System.out.println(
            "Reply from " + ipAddress + ": time=" + rtt + "ms"
          );
        } else {
          System.out.println("Request timed out.");
        }

        // Wait for 1 second between pings (like real ping)
        Thread.sleep(1000);
      }
    } catch (IOException | InterruptedException e) {
      System.out.println("Error: " + e.getMessage());
    }

    scanner.close();
  }
}
