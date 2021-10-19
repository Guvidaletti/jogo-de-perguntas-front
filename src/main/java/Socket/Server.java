package Socket;

import Jogo.CommandHandle;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread {
  public static DatagramSocket socket;

  static {
    try {
      socket = new DatagramSocket();
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  private boolean running;
  private byte[] buf = new byte[999];

  @Override
  public void run() {
    running = true;
    while (running) {
      try {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        CommandHandle.comandoRecebido(received);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
