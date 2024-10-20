import java.util.HashMap;
import java.util.Map;

class ARPTable {

  private Map<String, String> arpTable;

  // Constructor to initialize the ARP table with predefined IP-MAC pairs
  public ARPTable() {
    arpTable = new HashMap<>();
    arpTable.put("192.168.0.1", "00:0A:95:9D:68:16");
    arpTable.put("192.168.0.2", "00:0A:95:9D:68:17");
    arpTable.put("192.168.0.3", "00:0A:95:9D:68:18");
    arpTable.put("192.168.0.4", "00:0A:95:9D:68:19");
  }

  // Method to simulate an ARP request and find the MAC address for a given IP address
  public String getMACAddress(String ipAddress) {
    if (arpTable.containsKey(ipAddress)) {
      return arpTable.get(ipAddress); // Return the MAC address if IP is found
    } else {
      return null; // Return null if the IP is not in the table
    }
  }

  // Method to add a new IP-MAC mapping to the ARP table (simulate learning new devices)
  public void addEntry(String ipAddress, String macAddress) {
    arpTable.put(ipAddress, macAddress);
  }
}
