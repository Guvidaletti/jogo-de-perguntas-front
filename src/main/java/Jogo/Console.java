package Jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
  private static List<String> esperando = new ArrayList<>();
  private static Scanner scanner = new Scanner(System.in);

  public static void println(String msg) {
    System.out.println(msg);
    esperando.forEach(System.out::print);
  }

  public static void print(String msg) {
    System.out.print(msg);
    esperando.forEach(System.out::print);
  }

  public static String askForString(String msg) {
    System.out.print(msg);
    esperando.add(msg);
    String entrada = scanner.nextLine();
    esperando.remove(0);
    return entrada;
  }

  public static void error(String msg) {
    System.err.println(msg);
    esperando.forEach(System.out::print);
  }
}
