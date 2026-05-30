import TFinalClasses.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        MedicoPediatra pediatra = new MedicoPediatra("Dr.Marina", 250);
        Paciente paciente = new Paciente("Erick", 20, "Unimed");
        Consulta consulta = new Consulta(pediatra, paciente, "29/05/2026", "Consulta de rotina realizada com sucesso", "Vitamina D e Ferro", "", 250);
        Avaliacao avaliacao = new Avaliacao(pediatra, paciente, "Ótimo atendimento!", 5);

        consulta.registrarConsulta();
        avaliacao.registrarAvaliacao();
    }
}
