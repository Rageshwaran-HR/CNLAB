import java.io.*;
import java.net.*;

public class StopAndWaitServer {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      System.out.println("Server is listening on port 8080");
      Socket socket = serverSocket.accept();
      System.out.println("Client connected");

      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      String receivedFrame;
      int expectedFrame = 0;

      while (true) {
        // Read the incoming frame
        receivedFrame = in.readLine();
        if (receivedFrame != null) {
          System.out.println("Received Frame: " + receivedFrame);

          // Extract frame number
          int receivedFrameNumber = Integer.parseInt(receivedFrame);

          // Check if it's the expected frame
          if (receivedFrameNumber == expectedFrame) {
            System.out.println(
              "Frame " + expectedFrame + " received correctly."
            );
            out.println("ACK " + expectedFrame); // Send acknowledgment
            expectedFrame++; // Increment for the next expected frame
          } else {
            System.out.println("Received out of order frame. Ignoring...");
          }
        } else {
          break;
        }
      }

      socket.close();
    } catch (IOException ex) {
      System.out.println("Server error: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
