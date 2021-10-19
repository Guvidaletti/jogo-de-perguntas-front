package Jogo;

public class Pergunta {
  private long id;
  private String titulo;
  private String[] opcoes;
  private int correta;
  private String dificuldade = "";

  public Pergunta() {
  }

  public Pergunta(long id, String titulo, String[] opcoes, int correta, String dificuldade) {
    this.id = id;
    this.titulo = titulo;
    this.opcoes = opcoes;
    this.correta = correta;
    this.dificuldade = dificuldade;
  }

  public boolean isRespostaCerta(String resposta) {
    return opcoes[correta].equalsIgnoreCase(resposta);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String[] getOpcoes() {
    return opcoes;
  }

  public void setOpcoes(String[] opcoes) {
    this.opcoes = opcoes;
  }

  public int getCorreta() {
    return correta;
  }

  public void setCorreta(int correta) {
    this.correta = correta;
  }

  public String getDificuldade() {
    return dificuldade;
  }

  public void setDificuldade(String dificuldade) {
    this.dificuldade = dificuldade;
  }
}
