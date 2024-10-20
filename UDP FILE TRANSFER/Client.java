import java.io.*;
import java.net.*;

public class FileReceiver {

  public static void main(String[] args) {
    DatagramSocket socket = null;
    try {
      socket = new DatagramSocket(9876); // Bind to port 9876
      byte[] buffer = new byte[1024]; // Buffer to store received packets

      System.out.println("Waiting for file...");

      // Receive file name first
      DatagramPacket fileNamePacket = new DatagramPacket(buffer, buffer.length);
      socket.receive(fileNamePacket);
      String fileName = new String(
        fileNamePacket.getData(),
        0,
        fileNamePacket.getLength()
      );

      // Create a file to store the received content
      FileOutputStream fileOutputStream = new FileOutputStream(
        "received_" + fileName
      );
      System.out.println("Receiving file: " + fileName);

      // Receive the file content
      while (true) {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        // Check for termination message
        String receivedMessage = new String(
          packet.getData(),
          0,
          packet.getLength()
        );
        if (receivedMessage.equals("END")) {
          System.out.println("File transfer completed.");
          break;
        }

        // Write the received packet data to the file
        fileOutputStream.write(packet.getData(), 0, packet.getLength());
        System.out.println("Received packet of size: " + packet.getLength());
      }

      fileOutputStream.close();
    } catch (IOException e) {
      System.out.println("Error in receiving file: " + e.getMessage());
    } finally {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    }
  }
}
