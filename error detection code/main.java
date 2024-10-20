import java.util.Scanner;

public class CRCSimulation {

  // Method to perform the division and generate CRC
  private static String calculateCRC(String data, String divisor) {
    int m = data.length();
    int n = divisor.length();

    // Append zeros to the original data
    String appendedData = data + "0".repeat(n - 1);
    char[] dataArray = appendedData.toCharArray();
    char[] divisorArray = divisor.toCharArray();

    // Perform the division
    for (int i = 0; i <= m; i++) {
      if (dataArray[i] == '1') {
        for (int j = 0; j < n; j++) {
          dataArray[i + j] = (dataArray[i + j] == divisorArray[j]) ? '0' : '1'; // XOR operation
        }
      }
    }

    // Extract the CRC from the remainder
    StringBuilder crc = new StringBuilder();
    for (int i = m; i < m + n - 1; i++) {
      crc.append(dataArray[i]);
    }

    return crc.toString();
  }

  // Method to check if the received data is valid
  private static boolean isValidData(String data, String divisor) {
    String crc = calculateCRC(
      data.substring(0, data.length() - divisor.length() + 1),
      divisor
    );
    return crc.equals("0".repeat(divisor.length() - 1));
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Input data and divisor
    System.out.print("Enter the data (binary): ");
    String data = scanner.nextLine().trim();
    System.out.print("Enter the divisor (binary): ");
    String divisor = scanner.nextLine().trim();

    // Calculate CRC
    String crc = calculateCRC(data, divisor);
    System.out.println("CRC code: " + crc);

    // Simulate sending data with CRC
    String transmittedData = data + crc;
    System.out.println("Transmitted Data: " + transmittedData);

    // Simulate receiving data
    System.out.print("Enter the received data (including CRC): ");
    String receivedData = scanner.nextLine().trim();

    // Validate the received data
    if (isValidData(receivedData, divisor)) {
      System.out.println("The received data is valid.");
    } else {
      System.out.println("The received data is invalid.");
    }

    scanner.close();
  }
}
