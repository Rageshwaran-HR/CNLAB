import java.util.HashMap;
import java.util.Scanner;

public class ARP_RARP_Simulation {

  // ARP table (IP to MAC address mappings)
  private static HashMap<String, String> arpTable = new HashMap<>();

  public static void main(String[] args) {
    // Sample ARP table entries
    arpTable.put("192.168.1.2", "00:0a:95:9d:68:16");
    arpTable.put("192.168.1.3", "00:0a:95:9d:68:17");
    arpTable.put("192.168.1.4", "00:0a:95:9d:68:18");

    Scanner scanner = new Scanner(System.in);

    System.out.println("Choose the protocol simulation:");
    System.out.println("1. ARP (IP to MAC)");
    System.out.println("2. RARP (MAC to IP)");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    switch (choice) {
      case 1:
        System.out.println("Enter IP address to find MAC address:");
        String ip = scanner.nextLine();
        simulateARP(ip);
        break;
      case 2:
        System.out.println("Enter MAC address to find IP address:");
        String mac = scanner.nextLine();
        simulateRARP(mac);
        break;
      default:
        System.out.println("Invalid choice");
    }

    scanner.close();
  }

  // ARP Simulation (IP to MAC address)
  public static void simulateARP(String ip) {
    if (arpTable.containsKey(ip)) {
      System.out.println(
        "MAC address for IP " + ip + " is: " + arpTable.get(ip)
      );
    } else {
      System.out.println("No MAC address found for IP: " + ip);
    }
  }

  // RARP Simulation (MAC address to IP)
  public static void simulateRARP(String mac) {
    boolean found = false;
    for (String ip : arpTable.keySet()) {
      if (arpTable.get(ip).equals(mac)) {
        System.out.println("IP address for MAC " + mac + " is: " + ip);
        found = true;
        break;
      }
    }
    if (!found) {
      System.out.println("No IP address found for MAC: " + mac);
    }
  }
}
