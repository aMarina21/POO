package TFinalClasses;

import TFinalExcecoes.AvaliacaoInvalidaException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Avaliacao {
    private Medico medico;
    private Paciente paciente;
    private String descricao;
    private int estrelas;

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getEstrelas() {
        return estrelas;
    }

    // Registrar avaliação num arquivo de texto, provavelmente no formato
    // nomePaciente_nomeMedico_av.txt.
    // Primeira linha: nome do médico
    // Segunda linha: nome do paciente
    // Terceira linha: descrição da avaliação (comentários, sugestões, etc.)
    // Quarta linha: número de estrelas (1 a 5)
    public Avaliacao(Medico medico, Paciente paciente, String descricao, int estrelas) {
        this.medico = medico;
        this.paciente = paciente;
        this.descricao = descricao;
        this.estrelas = estrelas;
    }

    public void registrarAvaliacao() throws AvaliacaoInvalidaException {
        if (this.estrelas < 1 || this.estrelas > 5) {
            throw new AvaliacaoInvalidaException();
        }
        String caminho = this.paciente.getNome() + "_" + this.medico.getNome() + "_av.txt";
        String diretorio = "src/TFinalArquivos/avaliacoes/";
        File arquivoAvaliacao = new File(diretorio + caminho);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoAvaliacao))) {
            escritor.write("Medico: " + this.medico.getNome() + "\n");
            escritor.write("Paciente: " + this.paciente.getNome() + "\n");
            escritor.write("Descrição: " + this.descricao + "\n");
            escritor.write("Avaliação: " + this.estrelas + " estrelas");
            escritor.close();
        } catch (IOException e) {
            System.out.println("Erro ao registrar avaliação: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return medico.getNome() + " - " + estrelas + "★ - " + descricao;
    }
}
