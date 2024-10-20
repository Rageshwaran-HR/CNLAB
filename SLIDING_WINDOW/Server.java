import java.io.*;
import java.net.*;
import java.util.Random;

public class SlidingWindowSender {

  public static void main(String[] args) throws IOException {
    int windowSize = 4; // Size of the sliding window
    int totalFrames = 10; // Total number of frames to be sent
    int sentFrames = 0; // Count of frames that have been sent
    Random random = new Random();

    Socket socket = new Socket("localhost", 8080);
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(
      new InputStreamReader(socket.getInputStream())
    );

    System.out.println("Sender: Starting to send frames...");

    while (sentFrames < totalFrames) {
      // Send frames within the window size
      for (int i = 0; i < windowSize && sentFrames < totalFrames; i++) {
        System.out.println("Sending Frame: " + sentFrames);
        out.println(sentFrames); // Send the frame number to the receiver
        sentFrames++;
      }

      // Wait for acknowledgment of frames
      for (int i = 0; i < windowSize && sentFrames - windowSize >= 0; i++) {
        String ack = in.readLine();
        if (ack != null && ack.startsWith("ACK")) {
          System.out.println("Received " + ack);
        }
      }

      // Simulate packet loss by randomly resending frames
      if (random.nextInt(10) < 2) { // 20% chance of packet loss
        System.out.println("Packet loss detected. Resending last frame...");
        sentFrames--; // Resend the last frame
      }
    }

    System.out.println("Sender: All frames sent successfully.");
    socket.close();
  }
}
