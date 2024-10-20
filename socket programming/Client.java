import java.io.*;
import java.net.*;

public class Client {

  public static void main(String[] args) {
    String hostname = "localhost";
    int port = 8080;

    try (Socket socket = new Socket(hostname, port)) {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      String message = reader.readLine();
      System.out.println("Server: " + message);
    } catch (UnknownHostException ex) {
      System.out.println("Server not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
    }
  }
}
