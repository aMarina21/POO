package TFinalExcecoes;

public class AvaliacaoInvalidaException extends Exception {
    public AvaliacaoInvalidaException() {
        super("Avaliacao inválida! A nota deve ser de 1 a 5 estrelas!");
    }
}
