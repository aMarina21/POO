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

    public Medico(String nome, String especialidade, double valorConsulta, String senha) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.avalicoes = new ArrayList<>();
        this.planosDeSaude = new ArrayList<>();
        this.senha = senha;
    }

    public void adicionarPlano(String plano) throws PlanoInvalidoException {
        if (planoValido(plano)) {
            planosDeSaude.add(plano);
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

    public double getMediaEstrelas() {
        if (avalicoes == null || avalicoes.isEmpty()) {
            return 0.0;
        }
        double soma = 0;
        for (Avaliacao a : avalicoes) {
            soma += a.getEstrelas();
        }
        return soma / avalicoes.size();
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
