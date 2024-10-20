import java.io.*;
import java.net.*;

public class ChatServer {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      System.out.println("Server is listening on port 8080");
      Socket socket = serverSocket.accept();
      System.out.println("Client connected");

      // I/O streams to communicate with the client
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      );
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader consoleInput = new BufferedReader(
        new InputStreamReader(System.in)
      );

      // Thread to read and print client messages
      Thread readThread = new Thread(() -> {
        try {
          String clientMessage;
          while ((clientMessage = in.readLine()) != null) {
            System.out.println("Client: " + clientMessage);
          }
        } catch (IOException e) {
          System.out.println("Connection closed.");
        }
      });
      readThread.start();

      // Main thread to send server messages
      String serverMessage;
      while ((serverMessage = consoleInput.readLine()) != null) {
        out.println(serverMessage); // Send server message to client
      }

      socket.close();
    } catch (IOException ex) {
      System.out.println("Server error: " + ex.getMessage());
    }
  }
}
