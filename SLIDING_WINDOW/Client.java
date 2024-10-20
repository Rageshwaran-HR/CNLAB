import java.io.*;
import java.net.*;

public class SlidingWindowReceiver {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("Receiver: Waiting for frames...");

    Socket socket = serverSocket.accept();
    BufferedReader in = new BufferedReader(
      new InputStreamReader(socket.getInputStream())
    );
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

    int expectedFrame = 0;
    String frame;

    while ((frame = in.readLine()) != null) {
      int receivedFrame = Integer.parseInt(frame);
      System.out.println("Received Frame: " + receivedFrame);

      // Send acknowledgment if the received frame is the expected one
      if (receivedFrame == expectedFrame) {
        System.out.println("Frame " + receivedFrame + " received correctly.");
        out.println("ACK " + receivedFrame);
        expectedFrame++; // Move to the next expected frame
      } else {
        System.out.println("Out-of-order frame received. Ignoring...");
      }
    }

    System.out.println("Receiver: All frames received successfully.");
    socket.close();
    serverSocket.close();
  }
}
