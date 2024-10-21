import java.io.*;
import java.net.*;

public class UDPClient {

  public static void main(String[] args) throws IOException {
    DatagramSocket socket = new DatagramSocket();
    InetAddress IPAddress = InetAddress.getByName("localhost");
    byte[] sendData = new byte[1024];

    FileInputStream fis = new FileInputStream("file_to_send.txt");
    System.out.println("Sending the file...");

    // Reading file in chunks and sending
    int bytesRead;
    while ((bytesRead = fis.read(sendData)) != -1) {
      DatagramPacket sendPacket = new DatagramPacket(
        sendData,
        bytesRead,
        IPAddress,
        9876
      );
      socket.send(sendPacket);
    }

    // Send end of file signal
    String endSignal = "END";
    sendData = endSignal.getBytes();
    DatagramPacket endPacket = new DatagramPacket(
      sendData,
      sendData.length,
      IPAddress,
      9876
    );
    socket.send(endPacket);

    System.out.println("File sent successfully.");
    fis.close();
    socket.close();
  }
}
