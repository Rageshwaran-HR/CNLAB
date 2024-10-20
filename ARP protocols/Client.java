import java.util.Scanner;

public class ARPSimulation {

  public static void main(String[] args) {
    ARPTable arpTable = new ARPTable();
    Scanner scanner = new Scanner(System.in);

    System.out.println("ARP Protocol Simulation");
    System.out.println("========================");

    while (true) {
      System.out.print("Enter IP address to resolve (or 'exit' to quit): ");
      String ipAddress = scanner.nextLine();

      if (ipAddress.equalsIgnoreCase("exit")) {
        break;
      }

      // Simulate ARP request
      System.out.println("Sending ARP request for IP: " + ipAddress);
      String macAddress = arpTable.getMACAddress(ipAddress);

      if (macAddress != null) {
        // Simulate ARP response
        System.out.println(
          "Received ARP response: MAC Address for IP " +
          ipAddress +
          " is " +
          macAddress
        );
      } else {
        System.out.println("No entry found in ARP table for IP " + ipAddress);
        System.out.println("Learning new entry...");

        // Simulate learning a new device's MAC address (for demonstration)
        System.out.print("Enter the MAC address for " + ipAddress + ": ");
        String newMacAddress = scanner.nextLine();
        arpTable.addEntry(ipAddress, newMacAddress);
        System.out.println(
          "New entry added to ARP table: IP " +
          ipAddress +
          " -> MAC " +
          newMacAddress
        );
      }
      System.out.println();
    }

    scanner.close();
  }
}
