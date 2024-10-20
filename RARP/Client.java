import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RARPClient {

  private static final String SERVER_ADDRESS = "localhost"; // Change to server's IP if needed
  private static final int SERVER_PORT = 9876;

  public static void main(String[] args) {
    String macAddress = "00:0A:95:9D:68:16"; // Change this to test different MAC addresses

    try (DatagramSocket socket = new DatagramSocket()) {
      // Send the RARP request
      byte[] requestBuffer = macAddress.getBytes();
      DatagramPacket requestPacket = new DatagramPacket(
        requestBuffer,
        requestBuffer.length,
        InetAddress.getByName(SERVER_ADDRESS),
        SERVER_PORT
      );
      socket.send(requestPacket);
      System.out.println("Sent RARP request for MAC: " + macAddress);

      // Receive the RARP response
      byte[] responseBuffer = new byte[256];
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
        "Received RARP response: IP Address for MAC " +
        macAddress +
        " is " +
        ipAddress
      );
    } catch (IOException e) {
      System.out.println("Error in RARP Client: " + e.getMessage());
    }
  }
}
