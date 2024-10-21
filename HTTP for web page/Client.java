import java.io.*;
import java.net.*;

public class SimpleHTTPFileClient {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 8080);
    OutputStream out = socket.getOutputStream();
    InputStream in = socket.getInputStream();

    PrintWriter writer = new PrintWriter(out, true);
    writer.println("GET / HTTP/1.1");
    writer.println("Host: localhost");
    writer.println();

    // Read the HTTP response and file content
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String responseLine;
    while (!(responseLine = reader.readLine()).isEmpty()) {
      System.out.println(responseLine);
    }

    // Save the file (image.jpg)
    FileOutputStream fos = new FileOutputStream("downloaded_image.jpg");
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) > 0) {
      fos.write(buffer, 0, bytesRead);
    }

    fos.close();

    // Upload an image (POST request)
    File file = new File("upload_image.jpg"); // Make sure this file exists
    FileInputStream fis = new FileInputStream(file);
    byte[] fileData = new byte[(int) file.length()];
    fis.read(fileData);
    fis.close();

    // Sending the POST request
    PrintWriter writer = new PrintWriter(out, true);
    writer.println("POST / HTTP/1.1");
    writer.println("Host: localhost");
    writer.println("Content-Length: " + fileData.length);
    writer.println("Content-Type: image/jpeg");
    writer.println();
    out.write(fileData);
    out.flush();

    // Reading the response from the server
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String responseLine;
    while ((responseLine = reader.readLine()) != null) {
      System.out.println(responseLine);
    }

    socket.close();
  }
}
