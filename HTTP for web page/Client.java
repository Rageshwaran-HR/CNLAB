import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class HTTPClient {

  private static final String SERVER_ADDRESS = "localhost"; // Change to server's IP if needed
  private static final int SERVER_PORT = 8080;

  public static void main(String[] args) {
    // Example of downloading an HTML file
    String fileToDownload = "index.html"; // Change this to the file you want to download
    downloadFile(fileToDownload);

    // Example of uploading an HTML file
    String fileToUpload = "upload.html"; // Change this to the file you want to upload
    uploadFile(fileToUpload);
  }

  public static void downloadFile(String fileName) {
    try (
      Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      )
    ) {
      out.println("GET /" + fileName + " HTTP/1.1");
      out.println("Host: " + SERVER_ADDRESS);
      out.println();
      String responseLine;
      while ((responseLine = in.readLine()) != null) {
        System.out.println(responseLine);
      }
    } catch (IOException e) {
      System.out.println("Error in download: " + e.getMessage());
    }
  }

  public static void uploadFile(String fileName) {
    try (
      Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      )
    ) {
      String content = new String(
        Files.readAllBytes(new File(fileName).toPath())
      );
      out.println("POST / HTTP/1.1");
      out.println("Host: " + SERVER_ADDRESS);
      out.println("Content-Type: text/html");
      out.println("Content-Length: " + content.length());
      out.println();
      out.print(content);
      out.flush();

      String responseLine;
      while ((responseLine = in.readLine()) != null) {
        System.out.println(responseLine);
      }
    } catch (IOException e) {
      System.out.println("Error in upload: " + e.getMessage());
    }
  }
}
