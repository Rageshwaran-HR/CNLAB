import java.io.*;
import java.net.*;

public class SimpleHTTPFileServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("HTTP File Server is running on port 8080...");

    while (true) {
      Socket clientSocket = serverSocket.accept();
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );
      OutputStream out = clientSocket.getOutputStream();

      // Read the request line
      String requestLine = in.readLine();
      System.out.println("Request: " + requestLine);

      if (requestLine.startsWith("GET")) {
        // Serve the requested file for download (e.g., image.jpg)
        File file = new File("image.jpg"); // Make sure this file exists
        FileInputStream fis = new FileInputStream(file);
        byte[] fileData = new byte[(int) file.length()];

        fis.read(fileData);
        fis.close();

        // Send HTTP response header
        out.write(("HTTP/1.1 200 OK\r\n").getBytes());
        out.write(("Content-Type: image/jpeg\r\n").getBytes());
        out.write(("Content-Length: " + fileData.length + "\r\n").getBytes());
        out.write(("\r\n").getBytes());

        // Send file data
        out.write(fileData);
        out.flush();
      } else if (requestLine.startsWith("POST")) {
        // Handle file upload (image upload)
        PrintWriter writer = new PrintWriter(out, true);

        // Skipping headers
        String line;
        while (!(line = in.readLine()).isEmpty()) {
          System.out.println(line);
        }

        // Assuming file content comes right after the headers (no multipart/form-data handling in this basic example)
        FileOutputStream fos = new FileOutputStream("uploaded_image.jpg");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = clientSocket.getInputStream().read(buffer)) > 0) {
          fos.write(buffer, 0, bytesRead);
        }

        fos.close();
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<html><body>File uploaded successfully!</body></html>");
        writer.flush();
      }

      clientSocket.close();
    }
  }
}
