package Socket;

import java.io.IOException;
import java.net.*;

public class Client {
  private static InetAddress address;

  static {
    try {
      address = InetAddress.getByName("localhost");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public static void sendToBack(String message) {
    try {
      byte[] buf = message.getBytes();
      DatagramPacket packet
          = new DatagramPacket(buf, buf.length, address, 4444);
      Server.socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
