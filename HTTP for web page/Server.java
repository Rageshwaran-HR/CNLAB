import java.io.*;
import java.net.*;

public class HTTPServer {

  private static final int PORT = 8080; // Port for the server

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("HTTP Server is listening on port " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        new Thread(new RequestHandler(clientSocket)).start();
      }
    } catch (IOException e) {
      System.out.println("Error in HTTP Server: " + e.getMessage());
    }
  }

  private static class RequestHandler implements Runnable {

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      try (
        BufferedReader in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream())
        );
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
      ) {
        String requestLine = in.readLine();
        System.out.println("Received: " + requestLine);

        if (requestLine.startsWith("GET")) {
          handleGetRequest(requestLine, out);
        } else if (requestLine.startsWith("POST")) {
          handlePostRequest(in, out);
        }
      } catch (IOException e) {
        System.out.println("Error handling request: " + e.getMessage());
      } finally {
        try {
          clientSocket.close();
        } catch (IOException e) {
          System.out.println("Error closing socket: " + e.getMessage());
        }
      }
    }

    private void handleGetRequest(String requestLine, PrintWriter out) {
      String[] requestParts = requestLine.split(" ");
      String fileName = requestParts[1].substring(1); // Get the requested file name
      File file = new File(fileName);

      if (file.exists() && !file.isDirectory()) {
        try {
          BufferedReader fileReader = new BufferedReader(new FileReader(file));
          out.println("HTTP/1.1 200 OK");
          out.println("Content-Type: text/html");
          out.println("Content-Length: " + file.length());
          out.println();
          String line;
          while ((line = fileReader.readLine()) != null) {
            out.println(line);
          }
          fileReader.close();
        } catch (IOException e) {
          out.println("HTTP/1.1 500 Internal Server Error");
        }
      } else {
        out.println("HTTP/1.1 404 Not Found");
      }
    }

    private void handlePostRequest(BufferedReader in, PrintWriter out) {
      String headerLine;
      StringBuilder body = new StringBuilder();
      int contentLength = 0;

      // Read headers
      while (!(headerLine = in.readLine()).isEmpty()) {
        if (headerLine.startsWith("Content-Length:")) {
          contentLength = Integer.parseInt(headerLine.split(":")[1].trim());
        }
      }

      // Read the body
      char[] buffer = new char[contentLength];
      try {
        in.read(buffer, 0, contentLength);
        body.append(buffer);
        // Save the uploaded file
        try (
          PrintWriter fileWriter = new PrintWriter(
            new FileWriter("uploaded.html")
          )
        ) {
          fileWriter.write(body.toString());
        }
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("File uploaded successfully.");
      } catch (IOException e) {
        out.println("HTTP/1.1 500 Internal Server Error");
      }
    }
  }
}
