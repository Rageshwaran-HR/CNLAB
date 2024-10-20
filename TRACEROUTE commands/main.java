import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class Traceroute {

  private static final int MAX_HOPS = 30;
  private static final int TIMEOUT = 2000; // Timeout for receiving replies

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: java Traceroute <hostname>");
      return;
    }

    String hostname = args[0];
    try {
      InetAddress target = InetAddress.getByName(hostname);
      System.out.println(
        "Traceroute to " + hostname + " (" + target.getHostAddress() + "):"
      );

      for (int ttl = 1; ttl <= MAX_HOPS; ttl++) {
        try (DatagramSocket socket = new DatagramSocket()) {
          socket.setSoTimeout(TIMEOUT);
          socket.setReuseAddress(true);

          // Prepare the ICMP packet
          byte[] buf = new byte[64]; // ICMP payload size
          DatagramPacket packet = new DatagramPacket(
            buf,
            buf.length,
            target,
            33434
          );
          socket.setOption(
            SocketOptions.IP_MULTICAST_IF,
            InetAddress.getLocalHost()
          );

          // Send the packet
          socket.send(packet);
          long startTime = System.currentTimeMillis();

          // Receive the ICMP reply
          try {
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            long endTime = System.currentTimeMillis();
            InetAddress address = packet.getAddress();
            long time = endTime - startTime;
            System.out.printf(
              "%d: %s (%s) %.3f ms%n",
              ttl,
              address.getHostName(),
              address.getHostAddress(),
              time
            );
          } catch (SocketTimeoutException e) {
            System.out.printf("%d: * * * Request timed out.%n", ttl);
          }
        } catch (IOException e) {
          System.out.println("Error: " + e.getMessage());
        }
      }
    } catch (UnknownHostException e) {
      System.out.println("Unknown host: " + e.getMessage());
    }
  }
}
