package TFinalClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consulta {
    private Medico medico;
    private Paciente paciente;
    private String data;
    private String descricao;
    private String receita;
    private String exames;
    private double valorPago;

    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getReceita() {
        return receita;
    }
    public void setReceita(String receita) {
        this.receita = receita;
    }
    public String getExames() {
        return exames;
    }
    public void setExames(String exames) {
        this.exames = exames;
    }
    public double getValorPago() {
        return valorPago;
    }
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    // Consulta deve ser registrada num arquivo de texto, provavelmente no formato nomePaciente_nomeMedico_data.txt. 
    // Primeira linha: nome do médico
    // Segunda linha: nome do paciente
    // Terceira linha: data da consulta
    // Quarta linha: descrição da consulta (sintomas, diagnóstico, tratamento sugerido, etc.)
    // Quinta linha: receita (se houver)
    // Sexta linha: exames solicitados (se houver)
    // Sétima linha: valor pago pelo paciente 
    public Consulta(Medico medico, Paciente paciente, String data, String descricao, String receita, String exames, double valorPago) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
        this.descricao = descricao;
        this.receita = receita;
        this.exames = exames;
        this.valorPago = valorPago;
    }

    public void registrarConsulta(){
        String dataFormatada = this.data.replace("/", "_");
        String caminho = this.paciente.getNome() + "_" + this.medico.getNome() + "_" + dataFormatada + ".txt";
        String diretorio = "TFinal/src/TFinalArquivos/consultas/";
        File arquivoConsulta = new File(diretorio + caminho);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoConsulta))){
            escritor.write("Medico: " + this.medico.getNome() + "\n");
            escritor.write("Paciente: " + this.paciente.getNome() + "\n");
            escritor.write("Data da consulta: " + this.data + "\n");
            escritor.write("Descrição da consulta: " + this.descricao + "\n");
            if(this.receita != null && !this.receita.trim().isEmpty()){
                escritor.write("Receita: " + this.receita + "\n");
            } else {
                escritor.write("Sem receita\n");
            }
            if(exames != null && !this.exames.trim().isEmpty()){
                escritor.write("Exames: " + this.exames + "\n");
            } else {
                escritor.write("Sem exames sugeridos\n");
            }
            escritor.write("Valor: R$" + this.valorPago);

            escritor.close();
        } catch (IOException e) {
            System.out.println("Erro ao registrar consulta: " + e.getMessage());
        }
    }

}
