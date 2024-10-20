import java.io.*;
import java.net.*;

public class EchoClient {

  public static void main(String[] args) {
    String hostname = "localhost";
    int port = 8080;

    try (Socket socket = new Socket(hostname, port)) {
      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);

      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      // Sending message to the server
      BufferedReader consoleReader = new BufferedReader(
        new InputStreamReader(System.in)
      );
      System.out.println("Enter message: ");
      String message = consoleReader.readLine();

      writer.println(message); // Send the message to the server
      String response = reader.readLine(); // Receive echo from the server
      System.out.println("Server echoed: " + response);
    } catch (UnknownHostException ex) {
      System.out.println("Server not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
    }
  }
}
