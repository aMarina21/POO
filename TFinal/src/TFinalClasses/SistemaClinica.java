package TFinalClasses;

import TFinalDados.*;
import TFinalExcecoes.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SistemaClinica {
    private ArrayList<Medico> medicos;
    private ArrayList<Paciente> pacientes;
    private ArrayList<Agendamento> agendamentos;
    private ArrayList<Consulta> consultas;
    // Chave: "nomeMedico|data" -> fila de pacientes em espera
    private Map<String, Queue<Paciente>> listasEspera;

    private String basePath;

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();
    private ConsultaDAO consultaDAO = new ConsultaDAO();
    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public SistemaClinica(String basePath) {
        this.basePath = basePath;
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.listasEspera = new HashMap<>();
        carregarDados();
        criarDadosIniciais();
    }

    // ==================== PERSISTÊNCIA ====================

    private void carregarDados() {
        carregarMedicos();
        carregarPacientes();
        carregarAgendamentos();
        carregarConsultas();
        this.medicos = medicoDAO.buscarMedicos();
        this.pacientes = pacienteDAO.buscarPacientes();
        this.agendamentos = agendamentoDAO.buscarAgendamentos(this.pacientes, this.medicos);
        this.consultas = consultaDAO.buscarConsultas(this.pacientes, this.medicos);

        carregarListaEspera();
        //teste
        ArrayList<Paciente> pacientesDB = pacienteDAO.buscarPacientes();
        System.out.println(pacientesDB.size());
    }

    private void carregarMedicos() {
        this.medicos = medicoDAO.buscarMedicos();
    }


    private void carregarPacientes() {
        this.pacientes = pacienteDAO.buscarPacientes();
    }


    private void carregarAgendamentos() {
        this.agendamentos = agendamentoDAO.buscarAgendamentos(pacientes, medicos);
    }

    private void carregarConsultas() {
        this.consultas = consultaDAO.buscarConsultas(pacientes, medicos);
    }

    private void carregarListaEspera() {
        File arquivo = new File(basePath + "/listaespera.txt");
        if (!arquivo.exists())
            return;
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty())
                    continue;
                String[] partes = linha.split("\t");
                if (partes.length < 3)
                    continue;
                String nomeMedico = partes[0];
                String nomePaciente = partes[1];
                String data = partes[2];
                Paciente paciente = buscarPacientePorNome(nomePaciente);
                if (paciente != null) {
                    String chave = nomeMedico + "|" + data;
                    listasEspera.computeIfAbsent(chave, k -> new LinkedList<>()).add(paciente);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar lista de espera: " + e.getMessage());
        }
    }

    private void salvarListaEspera() {
        File arquivo = new File(basePath + "/listaespera.txt");
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))) {
            for (Map.Entry<String, Queue<Paciente>> entry : listasEspera.entrySet()) {
                String[] chave = entry.getKey().split("\\|");
                String nomeMedico = chave[0];
                String data = chave[1];
                for (Paciente p : entry.getValue()) {
                    escritor.write(nomeMedico + "\t" + p.getNome() + "\t" + data + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista de espera: " + e.getMessage());
        }
    }

    // ==================== LOGIN ====================

    public Object login(String nome, String senha) {
        for (Medico m : medicos) {
            if (m.getNome().equalsIgnoreCase(nome) && m.getSenha().equals(senha)) {
                return m;
            }
        }
        for (Paciente p : pacientes) {
            if (p.getNome().equalsIgnoreCase(nome) && p.getSenha().equals(senha)) {
                return p;
            }
        }
        return null;
    }

    // ==================== CADASTRO ====================

    public void cadastrarPaciente(String nome, int idade, String plano, String senha) {
        Paciente p = new Paciente(nome, idade, plano, senha);
        pacientes.add(p);

        //teste de cadastro no DB
        pacienteDAO.cadastrarPaciente(p);
        System.out.println("Paciente cadastrado no banco de dados!");
    }

    public void cadastrarMedico(String nome, double valor, String senha, String tipo) {
        Medico m;
        switch (tipo) {
            case "Cardiologista":
                m = new MedicoCardiologista(nome, valor, senha);
                break;
            case "Dermatologista":
                m = new MedicoDermatologista(nome, valor, senha);
                break;
            case "Pediatra":
                m = new MedicoPediatra(nome, valor, senha);
                break;
            default:
                return;
        }
        medicos.add(m);
        medicoDAO.cadastrarMedico(m);
    }

    // ==================== PESQUISA DE MÉDICOS ====================

    public ArrayList<Medico> pesquisarMedicos(String termoBusca, String planoPaciente) {
        ArrayList<Medico> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            boolean correspondeNome = termoBusca.isEmpty()
                    || m.getNome().toLowerCase().contains(termoBusca.toLowerCase());
            boolean correspondeEspecialidade = termoBusca.isEmpty()
                    || m.getEspecialidade().toLowerCase().contains(termoBusca.toLowerCase());

            if (correspondeNome || correspondeEspecialidade) {
                // Se paciente tem plano, mostrar apenas médicos que aceitam o plano
                if (planoPaciente != null && !planoPaciente.isEmpty()) {
                    if (m.planoValido(planoPaciente)) {
                        resultado.add(m);
                    }
                } else {
                    resultado.add(m);
                }
            }
        }
        return resultado;
    }

    // ==================== AGENDAMENTO ====================

    public void agendarConsulta(Medico medico, Paciente paciente, LocalDate data)
            throws AgendaLotadaException {
        // Contar agendamentos existentes para este médico nesta data
        int contagem = 0;
        for (Agendamento ag : agendamentos) {
            if (ag.getMedico().getNome().equals(medico.getNome())
                    && ag.getDataConsulta().equals(data)) {
                contagem++;
            }
        }
        if (contagem >= medico.getPacientesPorDia()) {
            throw new AgendaLotadaException(medico.getNome(), data.toString());
        }
        Agendamento ag = new Agendamento(medico, paciente, data);
        agendamentoDAO.cadastrarAgendamento(ag);
        agendamentos.add(ag);
    }

    public void entrarListaEspera(Medico medico, Paciente paciente, LocalDate data) {
        String chave = medico.getNome() + "|" + data.toString();
        listasEspera.computeIfAbsent(chave, k -> new LinkedList<>()).add(paciente);
        salvarListaEspera();
    }

    public void cancelarAgendamento(Agendamento agendamento) {
        agendamentos.remove(agendamento);
        agendamentoDAO.removerAgendamento(agendamento);
        //remover agendamento antigo do db

        // Promover paciente da lista de espera
        String chave = agendamento.getMedico().getNome() + "|" + agendamento.getDataConsulta().toString();
        Queue<Paciente> fila = listasEspera.get(chave);
        if (fila != null && !fila.isEmpty()) {
            Paciente promovido = fila.poll();
            Agendamento novoAg = new Agendamento(agendamento.getMedico(), promovido, agendamento.getDataConsulta());
            agendamentos.add(novoAg);
            agendamentoDAO.cadastrarAgendamento(novoAg);
            //salvar novo agendamento no db

            salvarListaEspera();
        }
    }

    public ArrayList<Agendamento> getAgendamentosPaciente(Paciente paciente) {
        ArrayList<Agendamento> resultado = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            if (ag.getPaciente().getNome().equals(paciente.getNome())) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    public ArrayList<Agendamento> getAgendamentosMedico(Medico medico) {
        ArrayList<Agendamento> resultado = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            if (ag.getMedico().getNome().equals(medico.getNome())) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    public ArrayList<Agendamento> getAgendamentosHojeMedico(Medico medico) {
        ArrayList<Agendamento> resultado = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        for (Agendamento ag : agendamentos) {
            if (ag.getMedico().getNome().equals(medico.getNome())
                    && ag.getDataConsulta().equals(hoje)) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    // ==================== CONSULTA ====================

    public Consulta realizarConsulta(Medico medico, Paciente paciente, String descricao,
            String receita, String exames, double valor) {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Se paciente tem plano, não paga
        if (paciente.getPlanoSaude() != null && !paciente.getPlanoSaude().isEmpty()) {
            valor = 0;
        }

        Consulta consulta = new Consulta(medico, paciente, data, descricao, receita, exames, valor);
        consultaDAO.cadastrarConsulta(consulta); //salvando a consulta no db
        consultas.add(consulta);
        paciente.adicionarConsultaHistorico(consulta);

        // Remover agendamento correspondente de hoje
        Agendamento paraRemover = null;
        for (Agendamento ag : agendamentos) {
            if (ag.getMedico().getNome().equals(medico.getNome())
                    && ag.getPaciente().getNome().equals(paciente.getNome())
                    && ag.getDataConsulta().equals(LocalDate.now())) {
                paraRemover = ag;
                break;
            }
        }
        if (paraRemover != null) {
            agendamentos.remove(paraRemover);
            agendamentoDAO.removerAgendamento(paraRemover);
        }

        return consulta;
    }

    // ==================== AVALIAÇÃO ====================

    public void registrarAvaliacao(Medico medico, Paciente paciente, String descricao, int estrelas)
            throws AvaliacaoInvalidaException {
        Avaliacao avaliacao = new Avaliacao(medico, paciente, descricao, estrelas);
        avaliacaoDAO.cadastrarAvaliacao(avaliacao);
        medico.adicionarAvaliacao(avaliacao);
    }

    // ==================== HISTÓRICO ====================

    public ArrayList<Consulta> getHistoricoPaciente(Paciente paciente) {
        return paciente.getHistorico();
    }

    // ==================== ESTATÍSTICAS ====================

    public Medico getMedicoMaisBemAvaliado() {
        Medico melhor = null;
        double melhorMedia = -1;
        for (Medico m : medicos) {
            double media = m.getMediaEstrelas();
            if (media > melhorMedia) {
                melhorMedia = media;
                melhor = m;
            }
        }
        return melhor;
    }

    public String getEspecialidadeMaisProcurada() {
        Map<String, Integer> contagem = new HashMap<>();
        for (Consulta c : consultas) {
            String esp = c.getMedico().getEspecialidade();
            contagem.put(esp, contagem.getOrDefault(esp, 0) + 1);
        }
        String maisProcurada = "N/A";
        int max = 0;
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maisProcurada = entry.getKey();
            }
        }
        return maisProcurada;
    }

    public int getTotalConsultas() {
        return consultas.size();
    }

    public double getMediaGeralAvaliacoes() {
        int total = 0;
        double soma = 0;
        for (Medico m : medicos) {
            for (Avaliacao a : m.getAvalicoes()) {
                soma += a.getEstrelas();
                total++;
            }
        }
        return total > 0 ? soma / total : 0;
    }

    public Map<String, Integer> getConsultasPorMedico() {
        Map<String, Integer> resultado = new HashMap<>();
        for (Medico m : medicos) {
            resultado.put(m.getNome(), 0);
        }
        for (Consulta c : consultas) {
            String nome = c.getMedico().getNome();
            resultado.put(nome, resultado.getOrDefault(nome, 0) + 1);
        }
        return resultado;
    }

    // ==================== CONSULTA DE DADOS ====================

    public ArrayList<Consulta> getConsultasPaciente(Paciente paciente) {
        ArrayList<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultas) {
            if (c.getPaciente().getNome().equals(paciente.getNome())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ==================== UTILITÁRIOS ====================

    public Medico buscarMedicoPorNome(String nome) {
        for (Medico m : medicos) {
            if (m.getNome().equalsIgnoreCase(nome))
                return m;
        }
        return null;
    }

    public Paciente buscarPacientePorNome(String nome) {
        for (Paciente p : pacientes) {
            if (p.getNome().equalsIgnoreCase(nome))
                return p;
        }
        return null;
    }

    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public Queue<Paciente> getListaEspera(Medico medico, LocalDate data) {
        String chave = medico.getNome() + "|" + data.toString();
        return listasEspera.getOrDefault(chave, new LinkedList<>());
    }

    // ==================== SEED DATA ====================

    public void criarDadosIniciais() {
        if (!medicos.isEmpty() || !pacientes.isEmpty())
            return;

        // Médicos
        cadastrarMedico("Dr. Carlos", 300, "1234", "Cardiologista");
        cadastrarMedico("Dra. Marina", 250, "1234", "Pediatra");
        cadastrarMedico("Dra. Fernanda", 280, "1234", "Dermatologista");

        // Pacientes
        cadastrarPaciente("Erick", 20, "Unimed", "1234");
        cadastrarPaciente("Livia", 19, "Hapvida", "1234");
        cadastrarPaciente("Enderson", 21, "", "1234");
        cadastrarPaciente("Maria", 18, "Unimed", "1234");
    }
}
