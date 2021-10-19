package Jogo;

import Socket.Server;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Jogo {
  private static Server server = new Server();
  private static String username = "";
  public static final Semaphore semaphore = new Semaphore(0);
  public static String gameID = "";
  public static boolean isAdm = false;
  public static Pergunta perguntaAtual = null;


  public static void getUsername() {
    while (username == null || username.length() > 10 || username.length() < 3) {
      System.out.print("Digite o nome do usuário: ");
      username = new Scanner(System.in).nextLine().trim();
    }
  }

  public static void menuPrincipal() {
    int opcao = -1;
    while (opcao != 0) {
      try {
        System.out.println("===================================================");
        System.out.println("1 - Criar jogo");
        System.out.println("2 - Entrar em um jogo");
        System.out.println("0 - Sair do jogo");
        //===================================================================
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(new Scanner(System.in).nextLine());
        switch (opcao) {
          case 1 -> {
            CommandHandle.criarJogo(username);
            semaphore.acquire();
            Console.println("Jogo criado! ID: " + gameID);
            menuDoJogo();
          }
          case 2 -> {
            String id = Console.askForString("Digite o id do jogo: ");
            CommandHandle.entrarNoJogo(id, username);
            semaphore.acquire();
            if (gameID == null || gameID.length() == 0) {
              Console.error("Erro ao entrar no jogo!");
            } else {
              System.out.println("===================================================");
              Console.println("Você entrou no jogo " + gameID);
              System.out.println("===================================================");
              Console.println("Esperando o jogo começar...");
              semaphore.acquire();
              menuDoJogo();
            }
          }
          default -> {
            System.out.println("Opção inválida!");
          }
        }
      } catch (NumberFormatException e) {
        System.out.println("Digite um número!");
        opcao = -1;
      } catch (InterruptedException e) {
        e.printStackTrace();
        opcao = -1;
      }
    }
  }

  public static void menuDoJogo() {
    try {
      if (isAdm) {
        System.out.println("===================================================");
        System.out.println("================ADM JOGO " + gameID + ("==========================").substring(gameID.length()));
        System.out.println("===================================================");
        Console.println("1 - Iniciar Jogo");
        String opcao = "";
        while (!opcao.equals("1")) {
          opcao = Console.askForString("Digite sua opção: ");
        }
        CommandHandle.iniciarJogo(gameID);
      }
      System.out.println("===================================================");
      System.out.println("====================JOGO " + gameID + ("==========================").substring(gameID.length()));
      System.out.println("===================================================");
      while (gameID != null && gameID.length() > 0) {
        semaphore.acquire();
        if (perguntaAtual != null) {
          Console.println("===================================================");
          CommandHandle.getStatus(gameID);
          semaphore.acquire();
          Console.println("===================================================");
          Console.println(perguntaAtual.getTitulo());
          int count = 1;
          for (String opcaoDeResposta : perguntaAtual.getOpcoes()) {
            Console.println(count++ + ") " + opcaoDeResposta);
          }
          Integer resposta = Integer.parseInt(Console.askForString("Digite a resposta: ").trim()) - 1;
          Console.println("===================================================");
          if (perguntaAtual.isRespostaCerta(perguntaAtual.getOpcoes()[resposta])) {
            CommandHandle.usuarioAcertou(gameID, username);
          } else {
            CommandHandle.usuarioErrou(gameID, username);
          }
          perguntaAtual = null;
        }
      }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public void sairDoJogo() {
    CommandHandle.sairDoJogo(gameID, username);
    gameID = null;
    isAdm = false;
  }

  public static void main(String[] args) {
    server.start();
    System.out.println("===================================================");
    System.out.println("===================BEM VINDO(A)====================");
    System.out.println("===================================================");
    getUsername();
    menuPrincipal();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (gameID != null && gameID.length() > 0) {
        CommandHandle.sairDoJogo(gameID, username);
      }
    }));
  }
}
