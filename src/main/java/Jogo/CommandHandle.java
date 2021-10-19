package Jogo;

import Socket.Client;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class CommandHandle {
  public static void comandoRecebido(String mensagem) throws IOException {
    String[] comando = mensagem.split(";");

    switch (comando[0]) {
      case "created" -> {
        Jogo.gameID = comando[1];
        Jogo.isAdm = true;
        Jogo.semaphore.release();
      }
      case "joined" -> {
        Jogo.gameID = comando[1];
        Jogo.isAdm = false;
        Jogo.semaphore.release();
      }
      case "userEntered" -> {
        Console.println(comando[1] + " entrou no jogo!");
      }
      case "started" -> {
        Console.println("O jogo começou!");
        Jogo.semaphore.release();
      }
      case "question" -> {
        ObjectMapper objectMapper = new ObjectMapper();
        Pergunta pergunta = objectMapper.readValue(comando[1], Pergunta.class);
        Jogo.perguntaAtual = pergunta;
        Jogo.semaphore.release();
      }
      case "status" -> {
        Console.println(comando[1]);
        Jogo.semaphore.release();
      }
      case "quited" -> {
        Console.println(comando[1] + " saiu do jogo!");
      }
      case "hit" -> {
        Console.println(comando[1] + " acertou!");
      }
      case "miss" -> {
        Console.println(comando[1] + " errou!");
      }
      case "win" -> {
        Console.println(comando[1] + " ganhou o jogo!");
        Jogo.perguntaAtual = null;
        Jogo.gameID = "";
        Jogo.isAdm = false;
        Jogo.semaphore.release();
      }
      case "error" -> {
        Console.error("Jogo não encontrado");
        Jogo.semaphore.release();
      }
      default -> {
        Console.error("Comando não identificado : " + comando[0]);
      }
    }
  }

  public static void criarJogo(String username) {
    Client.sendToBack("creategame;" + username);
  }

  public static void entrarNoJogo(String id, String username) {
    Client.sendToBack("join;" + id + ";" + username);
  }

  public static void iniciarJogo(String id) {
    Client.sendToBack("start;" + id);
  }

  public static void sairDoJogo(String id, String username) {
    Client.sendToBack("quit;" + id + ";" + username);
  }

  public static void usuarioAcertou(String gameID, String username) {
    Client.sendToBack("acertou;" + gameID + ";" + username);
  }

  public static void usuarioErrou(String gameID, String username) {
    Client.sendToBack("errou;" + gameID + ";" + username);
  }

  public static void getStatus(String gameID) {
    Client.sendToBack("status;" + gameID);
  }
}
