package TFinalExcecoes;

public class PacienteDuplicadoException extends Exception {
    public PacienteDuplicadoException(String nome) {
        super("Paciente " + nome + " já está cadastrado(a) no sistema!");
    }
}
