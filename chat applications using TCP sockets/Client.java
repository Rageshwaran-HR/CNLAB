import java.io.*;
import java.net.*;

public class ChatClient {

  public static void main(String[] args) {
    String hostname = "localhost";
    int port = 8080;

    try (Socket socket = new Socket(hostname, port)) {
      System.out.println("Connected to the server");

      // I/O streams to communicate with the server
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader consoleInput = new BufferedReader(
        new InputStreamReader(System.in)
      );

      // Thread to read and print server messages
      Thread readThread = new Thread(() -> {
        try {
          String serverMessage;
          while ((serverMessage = in.readLine()) != null) {
            System.out.println("Server: " + serverMessage);
          }
        } catch (IOException e) {
          System.out.println("Connection closed.");
        }
      });
      readThread.start();

      // Main thread to send client messages
      String clientMessage;
      while ((clientMessage = consoleInput.readLine()) != null) {
        out.println(clientMessage); // Send client message to server
      }

      socket.close();
    } catch (IOException ex) {
      System.out.println("Client error: " + ex.getMessage());
    }
  }
}
