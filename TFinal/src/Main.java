import TFinalClasses.*;
import TFinalExcecoes.MedicoDuplicadoException;
import TFinalExcecoes.PacienteDuplicadoException;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        MedicoPediatra pediatra = new MedicoPediatra("Dr.Marina", 250, "a1b2c3");
        MedicoCardiologista cardiologista = new MedicoCardiologista("João", 300, "a442d");

        Paciente paciente1 = new Paciente("Erick", 20, "Unimed", "azx123");
        Paciente paciente2 = new Paciente("Livia", 19, "Hapvida", "asd345");
        Paciente paciente3 = new Paciente("Enderson", 21, "Unimed", "bnm789");

        // Avaliacao avaliacao = new Avaliacao(pediatra, paciente, "Ótimo atendimento!",
        // 5);
        //
        Agendamento agendamento1 = new Agendamento(pediatra, paciente1, LocalDate.of(2026, 05, 31));
        Agendamento agendamento2 = new Agendamento(pediatra, paciente2, LocalDate.of(2026, 05, 31));
        Agendamento agendamento3 = new Agendamento(pediatra, paciente3, LocalDate.of(2026, 05, 31));
        //
        //// agendamento1.registrarAgendamento(); / agendamento2.registrarAgendamento();

        pediatra.adicionarPlano("Unimed");
        pediatra.adicionarPlano("Hapvida");
        cardiologista.adicionarPlano("Unimed");

        // if(agendamento1.diaConsulta()){
        // Consulta consulta = new Consulta(pediatra, paciente1, "29/05/2026", "Consulta
        // de rotina realizada com sucesso", "Vitamina D e Ferro", "", 250);
        // consulta.registrarConsulta();
        // }

        SistemaClinicaMedica sistema = new SistemaClinicaMedica();
        sistema.adicionarMedicos();
        sistema.adicionarPacientes();

        try {
            sistema.cadastrarMedico(cardiologista);
            System.out.println("Medico cadastrado com sucesso!");
        } catch (MedicoDuplicadoException e) {
            System.err.println(e.getMessage());
        }

        try {
            sistema.cadastrarPaciente(paciente1);
            System.out.println("Paciente cadastrado com sucesso!");
        } catch (PacienteDuplicadoException e) {
            System.err.println(e.getMessage());
        }

        sistema.realizarLoginMedicos("João", "a442d");
        sistema.realizarLoginPacientes("Erick", "azx123");
    }
}
