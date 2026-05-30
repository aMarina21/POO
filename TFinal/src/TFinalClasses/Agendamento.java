package TFinalClasses;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Agendamento {
    private Medico medico;
    private Paciente paciente;
    private int diaAgendamento;
    private int mesAgendamento;
    private int anoAgendamento;
    private int diaConsulta;
    private int mesConsulta;
    private int anoConsulta;

    public Agendamento(Medico medico, Paciente paciente, int diaAgendamento, int mesAgendamento,
                       int anoAgendamento, int diaConsulta, int mesConsulta, int anoConsulta){
        this.medico = medico;
        this.paciente = paciente;
        this.diaAgendamento = diaAgendamento;
        this.mesAgendamento = mesAgendamento;
        this.anoAgendamento = anoAgendamento;
        this.diaConsulta = diaConsulta;
        this.mesConsulta = mesConsulta;
        this.anoConsulta = anoConsulta;
    }
}
