import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DNSClient {

  private static final String SERVER_ADDRESS = "localhost"; // Change to server's IP if needed
  private static final int SERVER_PORT = 53; // Standard DNS port

  public static void main(String[] args) {
    String domainName = "www.example.com"; // Change this to test different domain names

    try (DatagramSocket socket = new DatagramSocket()) {
      // Send the DNS query
      byte[] requestBuffer = domainName.getBytes();
      DatagramPacket requestPacket = new DatagramPacket(
        requestBuffer,
        requestBuffer.length,
        InetAddress.getByName(SERVER_ADDRESS),
        SERVER_PORT
      );
      socket.send(requestPacket);
      System.out.println("Sent DNS query for domain: " + domainName);

      // Receive the DNS response
      byte[] responseBuffer = new byte[512];
      DatagramPacket responsePacket = new DatagramPacket(
        responseBuffer,
        responseBuffer.length
      );
      socket.receive(responsePacket);

      String ipAddress = new String(
        responsePacket.getData(),
        0,
        responsePacket.getLength()
      );
      System.out.println(
        "Received DNS response: IP Address for domain " +
        domainName +
        " is " +
        ipAddress
      );
    } catch (IOException e) {
      System.out.println("Error in DNS Client: " + e.getMessage());
    }
  }
}
