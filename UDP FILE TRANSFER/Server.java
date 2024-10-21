import java.io.*;
import java.net.*;

public class UDPServer {

  public static void main(String[] args) throws IOException {
    DatagramSocket socket = new DatagramSocket(9876);
    byte[] receiveData = new byte[1024];

    System.out.println("Server is ready to receive the file...");
    FileOutputStream fos = new FileOutputStream("received_file.txt");

    // Loop to receive all the file data
    while (true) {
      DatagramPacket receivePacket = new DatagramPacket(
        receiveData,
        receiveData.length
      );
      socket.receive(receivePacket);
      String received = new String(
        receivePacket.getData(),
        0,
        receivePacket.getLength()
      );

      // End of file transfer signal
      if (received.equals("END")) {
        System.out.println("File transfer complete.");
        break;
      }
      fos.write(receivePacket.getData(), 0, receivePacket.getLength());
    }

    fos.close();
    socket.close();
  }
}
