import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class RARPServer {

  private static final int PORT = 9876;
  private static final Map<String, String> macToIpMap = new HashMap<>();

  public static void main(String[] args) {
    // Predefined MAC to IP mappings
    macToIpMap.put("00:0A:95:9D:68:16", "192.168.0.1");
    macToIpMap.put("00:0A:95:9D:68:17", "192.168.0.2");
    macToIpMap.put("00:0A:95:9D:68:18", "192.168.0.3");

    try (DatagramSocket socket = new DatagramSocket(PORT)) {
      System.out.println("RARP Server is listening on port " + PORT);

      while (true) {
        // Receive the request packet
        byte[] buffer = new byte[256];
        DatagramPacket requestPacket = new DatagramPacket(
          buffer,
          buffer.length
        );
        socket.receive(requestPacket);

        // Extract MAC address from the request
        String macAddress = new String(
          requestPacket.getData(),
          0,
          requestPacket.getLength()
        );
        System.out.println("Received RARP request for MAC: " + macAddress);

        // Lookup the corresponding IP address
        String ipAddress = macToIpMap.get(macAddress);
        String responseMessage = (ipAddress != null) ? ipAddress : "Not Found";

        // Send the response packet back to the client
        byte[] responseBuffer = responseMessage.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(
          responseBuffer,
          responseBuffer.length,
          requestPacket.getAddress(),
          requestPacket.getPort()
        );
        socket.send(responsePacket);

        System.out.println("Sent RARP response: " + responseMessage);
      }
    } catch (IOException e) {
      System.out.println("Error in RARP Server: " + e.getMessage());
    }
  }
}
