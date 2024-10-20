import java.io.*;
import java.net.*;

public class EchoServer {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      System.out.println("Echo Server is listening on port 8080");

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        new EchoThread(socket).start();
      }
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}

class EchoThread extends Thread {

  private Socket socket;

  public EchoThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);

      String text;
      while ((text = reader.readLine()) != null) {
        System.out.println("Received: " + text);
        writer.println(text); // Echo the received message
      }
      socket.close();
    } catch (IOException ex) {
      System.out.println("Server error: " + ex.getMessage());
    }
  }
}
