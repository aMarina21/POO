package TFinalClasses;

import TFinalExcecoes.PlanoInvalidoException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class Medico {
    private String nome;
    private String especialidade;
    private double valorConsulta;
    private String senha;
    private ArrayList<Avaliacao> avalicoes;
    private ArrayList<String> planosDeSaude;
    private String senha;

    public Medico(String nome, String especialidade, double valorConsulta, String senha) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.senha = "";
        this.avalicoes = new ArrayList<>();
        this.planosDeSaude = new ArrayList<>();
        this.senha = senha;
    }

    public void adicionarPlano(String plano) throws PlanoInvalidoException {
        if (planoValido(plano)) {
            planosDeSaude.add(plano);
        }
    }

    private String caminho = "src/TFinalArquivos/medicos/medicos.txt";

    public void registrarMedico() throws IOException {
        File arquivoMedicos = new File(caminho);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoMedicos, true))) {
            String planos = String.join(",", this.planosDeSaude);
            escritor.write(this.nome + "\t" + this.senha + "\t" + this.especialidade + "\t" + this.valorConsulta + "\t"
                    + planos + "\n");
        }
    }

    private String caminho = "src/TFinalArquivos/medicos/medicos.txt";

    public void registrarMedico() throws IOException {
        File arquivoMedicos = new File(caminho);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoMedicos, true))) {
            String planos = String.join(",", this.planosDeSaude);
            escritor.write(this.nome + "\t" + this.senha + "\t" + this.especialidade + "\t" + this.valorConsulta + "\t"
                    + planos + "\n");
        }
    }

    public boolean planoValido(String plano) {
        return true;
    }

    public abstract int getPacientesPorDia();

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public ArrayList<Avaliacao> getAvalicoes() {
        return avalicoes;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        avalicoes.add(avaliacao);
    }

    public ArrayList<String> getPlanosDeSaude() {
        return planosDeSaude;
    }

    public void setPlanosDeSaude(ArrayList<String> planosDeSaude) {
        this.planosDeSaude = planosDeSaude;
    }

    public String getSenha() {
        return senha;
    }
}
