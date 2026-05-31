import TFinalClasses.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        MedicoPediatra pediatra = new MedicoPediatra("Dr.Marina", 250);
        Paciente paciente1 = new Paciente("Erick", 20, "Unimed");
        Paciente paciente2 = new Paciente("Livia", 19, "Hapvida");
        Paciente paciente3 = new Paciente("Enderson", 21, "Unimed");
//        Avaliacao avaliacao = new Avaliacao(pediatra, paciente, "Ótimo atendimento!", 5);
        Agendamento agendamento1 = new Agendamento(pediatra, paciente1, LocalDate.of(2026, 05, 31));
        Agendamento agendamento2 = new Agendamento(pediatra, paciente2, LocalDate.of(2026, 05, 31));
        Agendamento agendamento3 = new Agendamento(pediatra, paciente3, LocalDate.of(2026, 05, 31));

        agendamento1.registrarAgendamento();
        agendamento2.registrarAgendamento();
        agendamento3.registrarAgendamento();

        if(agendamento1.diaConsulta()){
            Consulta consulta = new Consulta(pediatra, paciente1, "29/05/2026", "Consulta de rotina realizada com sucesso", "Vitamina D e Ferro", "", 250);
            consulta.registrarConsulta();
        }







    }
}
