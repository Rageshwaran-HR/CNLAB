import java.io.*;
import java.net.*;
import java.util.Random;

public class StopAndWaitClient {

  public static void main(String[] args) {
    String hostname = "localhost";
    int port = 8080;
    int frameNumber = 0;
    Random random = new Random();

    try (Socket socket = new Socket(hostname, port)) {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      while (frameNumber < 5) { // Simulate sending 5 frames
        System.out.println("Sending Frame: " + frameNumber);
        out.println(frameNumber); // Send frame number

        // Simulate a delay
        Thread.sleep(1000);

        // Simulate a packet loss with 30% probability
        if (random.nextInt(10) < 3) {
          System.out.println("Frame " + frameNumber + " lost.");
          continue; // Frame lost, so don't wait for acknowledgment
        }

        // Wait for acknowledgment
        String ack = in.readLine();
        if (ack != null && ack.equals("ACK " + frameNumber)) {
          System.out.println("ACK received for Frame: " + frameNumber);
          frameNumber++; // Move to the next frame after receiving ACK
        } else {
          System.out.println(
            "No ACK received, resending Frame: " + frameNumber
          );
        }
      }
    } catch (IOException | InterruptedException ex) {
      System.out.println("Client error: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
