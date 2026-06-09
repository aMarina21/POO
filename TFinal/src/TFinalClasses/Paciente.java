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
    private String senha;

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
        this.senha = senha;
        this.planoSaude = planoSaude;
        this.senha = "";
        this.historico = new ArrayList<>();
    }

    private String caminho = "src/TFinalArquivos/pacientes/pacientes.txt";

    public void registrarPaciente() throws IOException {
        File arquivoPacientes = new File(caminho);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoPacientes, true))) {
            escritor.write(this.nome + "\t" + this.idade + "\t" + this.senha + "\t" + this.planoSaude + "\n");
        }
    }

    public void adicionarConsultaHistorico(Consulta consulta) {
        this.historico.add(consulta);
    }
}
