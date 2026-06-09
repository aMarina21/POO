package TFinalClasses;

import TFinalExcecoes.MedicoDuplicadoException;
import TFinalExcecoes.PacienteDuplicadoException;
import TFinalExcecoes.PlanoInvalidoException;

import java.io.*;
import java.util.ArrayList;

public class SistemaClinicaMedica {
    private ArrayList<Medico> listaMedicos;
    private ArrayList<Paciente> listaPacientes;

    public SistemaClinicaMedica() {
        this.listaMedicos = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
    }

    private String arquivoMedicos = "src/TFinalArquivos/medicos/medicos.txt";

    public void adicionarMedicos() {
        File arquivoM = new File(arquivoMedicos);
        if(!arquivoM.exists()){
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoM))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {

                String[] divisao = linha.split("\t");
                String nome = divisao[0];
                String senha = divisao[1];
                String especialidade = divisao[2];
                double valorConsulta = Double.parseDouble(divisao[3]);
                String planos = divisao[4];

                Medico medico;
                if (especialidade.equals("Cardiologista")) {
                    medico = new MedicoCardiologista(nome, valorConsulta, senha);
                } else if (especialidade.equals("Pediatra")) {
                    medico = new MedicoPediatra(nome, valorConsulta, senha);
                } else {
                    medico = new MedicoCardiologista(nome, valorConsulta, senha);
                }

                String[] listaPlanos = planos.split(",");
                for (String p : listaPlanos) {
                    try {
                        medico.adicionarPlano(p);
                    } catch (PlanoInvalidoException e) {
                        throw new RuntimeException(e);
                    }
                }
                listaMedicos.add(medico);
            }
            System.out.println("Medicos adicionados com sucesso!");
            System.out.println(listaMedicos.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String arquivoPacientes = "src/TFinalArquivos/pacientes/pacientes.txt";

    public void adicionarPacientes() throws FileNotFoundException {
        File arquivoP = new File(arquivoPacientes);
        if(!arquivoP.exists()){
            return;
        }
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoP))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split("\t");
                String nome = divisao[0];
                int idade = Integer.parseInt(divisao[1]);
                String senha = divisao[2];
                String plano = divisao[3];

                Paciente paciente = new Paciente(nome, idade, senha, plano);
                listaPacientes.add(paciente);
            }
            System.out.println("Pacientes adicionados com sucesso!");
            System.out.println(listaPacientes.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrarMedico(Medico novoMedico) throws MedicoDuplicadoException, IOException {
        for (Medico m : listaMedicos) {
            if (m.getNome().equalsIgnoreCase(novoMedico.getNome())) {
                throw new MedicoDuplicadoException(novoMedico.getNome());
            }
        }
        listaMedicos.add(novoMedico);
        novoMedico.registrarMedico();
    }

    public void cadastrarPaciente(Paciente novoPaciente) throws PacienteDuplicadoException, IOException {
        for (Paciente p : listaPacientes) {
            if (p.getNome().equalsIgnoreCase(novoPaciente.getNome())) {
                throw new PacienteDuplicadoException(novoPaciente.getNome());
            }
        }
        listaPacientes.add(novoPaciente);
        novoPaciente.registrarPaciente();
    }


    public Medico realizarLoginMedicos(String nome, String senha) {
        for (Medico m : listaMedicos) {
            if (m.getNome().equalsIgnoreCase(nome) && m.getSenha().equals(senha)) {
                System.out.println("Login do medico realizado com sucesso");
                return m;
            }
        }
        return null;
    }

    public Paciente realizarLoginPacientes(String nome, String senha) {
        for (Paciente p : listaPacientes) {
            if (p.getNome().equalsIgnoreCase(nome) && p.getSenha().equals(senha)) {
                System.out.println("Login do paciente realizado com sucesso");
                return p;
            }
        }
        return null;
    }
}
