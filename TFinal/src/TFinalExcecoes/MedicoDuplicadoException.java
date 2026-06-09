package TFinalExcecoes;

public class MedicoDuplicadoException extends RuntimeException {
  public MedicoDuplicadoException(String nome) {
    super("Médico(a) " + nome + " já está cadastrado(a) no sistema!");
  }
}
