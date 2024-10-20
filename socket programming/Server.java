import java.io.*;
import java.net.*;

public class Server {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      System.out.println("Server is listening on port 8080");

      Socket socket = serverSocket.accept();
      System.out.println("Client connected");

      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);

      writer.println("Hello Client!");

      socket.close();
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
