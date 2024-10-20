import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DNSServer {

  private static final int PORT = 53; // Standard DNS port
  private static final Map<String, String> dnsMap = new HashMap<>();

  public static void main(String[] args) {
    // Predefined domain to IP mappings
    dnsMap.put("www.example.com", "192.0.2.1");
    dnsMap.put("www.google.com", "172.217.0.46");
    dnsMap.put("www.openai.com", "104.21.16.2");

    try (DatagramSocket socket = new DatagramSocket(PORT)) {
      System.out.println("DNS Server is listening on port " + PORT);

      while (true) {
        // Buffer to receive incoming requests
        byte[] buffer = new byte[512];
        DatagramPacket requestPacket = new DatagramPacket(
          buffer,
          buffer.length
        );
        socket.receive(requestPacket);

        // Extract the domain name from the request
        String domainName = new String(
          requestPacket.getData(),
          0,
          requestPacket.getLength()
        )
          .trim();
        System.out.println("Received DNS query for domain: " + domainName);

        // Lookup the corresponding IP address
        String ipAddress = dnsMap.get(domainName);
        String responseMessage = (ipAddress != null) ? ipAddress : "Not Found";

        // Send the response back to the client
        byte[] responseBuffer = responseMessage.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(
          responseBuffer,
          responseBuffer.length,
          requestPacket.getAddress(),
          requestPacket.getPort()
        );
        socket.send(responsePacket);

        System.out.println("Sent DNS response: " + responseMessage);
      }
    } catch (IOException e) {
      System.out.println("Error in DNS Server: " + e.getMessage());
    }
  }
}
