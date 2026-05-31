package TFinalExcecoes;

public class AgendaLotadaException extends Exception {
    public AgendaLotadaException(String nomeMedico, String data) {
        super("A agenda do(a) doutor(a) " + nomeMedico + "está lotada para o dia " + data);
    }
}
