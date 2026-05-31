package TFinalClasses;

import TFinalExcecoes.AgendaLotadaException;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Agendamento {
    private Medico medico;
    private Paciente paciente;
    private LocalDate dataConsulta;

    public Agendamento(Medico medico, Paciente paciente, LocalDate dataConsulta) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataConsulta = dataConsulta;
    }

    public boolean diaConsulta(){
        return LocalDate.now().equals(this.dataConsulta);
    }

    private String caminho = "TFinal/src/TFinalArquivos/agendamentos/agendamentos.txt";
    public int contarAgendamentos() throws IOException {
        File arquivoAgendamento = new File(caminho);
        int contador = 0;

        if(!arquivoAgendamento.exists()) return 0;
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoAgendamento))){
            String linha;
            while((linha = leitor.readLine()) != null){
                String divisao[] = linha.split("\t");
                String medico = divisao[0];
                String data = divisao[2];

                if(medico.equalsIgnoreCase(this.medico.getNome()) && data.equals(this.dataConsulta.toString())){
                    contador++;
                }
            }
        }
        return contador;
    }

    public boolean temVagas() throws IOException {
        int qtdVagas = this.medico.getPacientesPorDia();
        return contarAgendamentos() < qtdVagas;
    }

    public void registrarAgendamento() throws IOException, AgendaLotadaException {
        File arquivoAgendamento = new File(caminho);
        if(temVagas()){
            try(BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoAgendamento, true))){
                if(!arquivoAgendamento.exists() || arquivoAgendamento.length() == 0){
                    escritor.write("Médico\tPaciente\tData\n");
                }
                escritor.write(this.medico.getNome() + "\t" + this.paciente.getNome() + "\t" + this.dataConsulta + "\n");
                System.out.println("Agendamento concluido!");
            }
        } else {
            throw new AgendaLotadaException(this.medico.getNome(), this.dataConsulta.toString());
        }
    }

}
