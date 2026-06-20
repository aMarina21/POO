package TFinalClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Paciente {
    private String nome;
    private int idade;
    private String planoSaude;
    private String senha;
    private ArrayList<Consulta> historico;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }

    public ArrayList<Consulta> getHistorico() {
        return historico;
    }

    public void setHistorico(ArrayList<Consulta> historico) {
        this.historico = historico;
    }

    public String getSenha() {
        return senha;
    }

    public Paciente(String nome, int idade, String planoSaude, String senha) {
        this.nome = nome;
        this.idade = idade;
        this.planoSaude = planoSaude;
        this.senha = senha;
        this.historico = new ArrayList<>();
    }

    public void adicionarConsultaHistorico(Consulta consulta) {
        this.historico.add(consulta);
    }
}
