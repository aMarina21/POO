package TFinalClasses;

import TFinalExcecoes.AgendaLotadaException;

import java.io.*;
import java.time.LocalDate;

public class Agendamento {
    private Medico medico;
    private Paciente paciente;
    private LocalDate dataConsulta;

    public Agendamento(Medico medico, Paciente paciente, LocalDate dataConsulta) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public boolean diaConsulta() {
        return LocalDate.now().equals(this.dataConsulta);
    }

    @Override
    public String toString() {
        return medico.getNome() + " | " + paciente.getNome() + " | " + dataConsulta.toString();
    }

}
