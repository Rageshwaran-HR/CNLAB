import java.io.*;
import java.net.*;

public class FileSender {

  public static void main(String[] args) {
    DatagramSocket socket = null;
    try {
      InetAddress receiverAddress = InetAddress.getByName("localhost"); // Receiver's address
      socket = new DatagramSocket(); // Create UDP socket

      // File to send
      File file = new File("file_to_send.txt");
      FileInputStream fileInputStream = new FileInputStream(file);
      byte[] buffer = new byte[1024]; // Buffer to store chunks of the file

      System.out.println("Sending file: " + file.getName());

      // Send the file name first
      byte[] fileNameBytes = file.getName().getBytes();
      DatagramPacket fileNamePacket = new DatagramPacket(
        fileNameBytes,
        fileNameBytes.length,
        receiverAddress,
        9876
      );
      socket.send(fileNamePacket);

      // Send the file content
      int bytesRead;
      while ((bytesRead = fileInputStream.read(buffer)) != -1) {
        DatagramPacket packet = new DatagramPacket(
          buffer,
          bytesRead,
          receiverAddress,
          9876
        );
        socket.send(packet); // Send each chunk
        System.out.println("Sent packet of size: " + bytesRead);
      }

      // Send a termination message to indicate the file transfer is complete
      String endMessage = "END";
      DatagramPacket endPacket = new DatagramPacket(
        endMessage.getBytes(),
        endMessage.getBytes().length,
        receiverAddress,
        9876
      );
      socket.send(endPacket);
      System.out.println("File transfer completed.");

      fileInputStream.close();
    } catch (IOException e) {
      System.out.println("Error in sending file: " + e.getMessage());
    } finally {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    }
  }
}
