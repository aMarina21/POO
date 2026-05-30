package T3Classes;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class InterfaceGrafica extends JFrame {
    private JTextArea textAreaBoletim;
    private JComboBox<String> comboAlunosBoletim;
    private JButton btnProximo;
    private JButton btnAnterior;
    private JLabel lblStatus;
    private ArrayList<String> alunosDisponiveis;
    private int alunoAtualIndex;
    private File diretorio;

    public InterfaceGrafica() {
        this(new File("."));
    }

    public InterfaceGrafica(File diretorio) {
        try {
            // Garantir que o diretório seja um caminho absoluto
            this.diretorio = diretorio.getAbsoluteFile();
            this.alunosDisponiveis = new ArrayList<>();
            this.alunoAtualIndex = 0;

            inicializarUI();
            carregarAlunosDisponiveis();
        } catch (Exception ex) {
            System.err.println("Erro ao criar interface gráfica: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inicializar interface: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarUI() {
        try {
            setTitle("Sistema de Boletins - T3");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);
            setResizable(true);

            // Inicializar componentes de texto 
            textAreaBoletim = new JTextArea();
            textAreaBoletim.setFont(new Font("Courier New", Font.PLAIN, 12));
            textAreaBoletim.setEditable(false);
            textAreaBoletim.setLineWrap(true);
            textAreaBoletim.setWrapStyleWord(true);
            textAreaBoletim.setText("Carregando boletins...");
            JScrollPane scrollPane = new JScrollPane(textAreaBoletim);

            // Painel superior com controles
            JPanel painelSuperior = criarPainelControles();
            
            // Painel inferior com status
            JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblStatus = new JLabel("Inicializando...");
            painelInferior.add(lblStatus);

            // Adicionar componentes à janela
            add(painelSuperior, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(painelInferior, BorderLayout.SOUTH);

            setVisible(true);
        } catch (Exception ex) {
            System.err.println("Erro ao inicializar UI: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JPanel criarPainelControles() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painel.setBackground(new Color(240, 240, 240));

        // Label e ComboBox para seleção de aluno
        painel.add(new JLabel("Selecione um aluno:"));
        comboAlunosBoletim = new JComboBox<>();
        comboAlunosBoletim.setPreferredSize(new Dimension(200, 25));
        comboAlunosBoletim.addActionListener(e -> carregarBoletimSelecionado());
        painel.add(comboAlunosBoletim);

        // Botões de navegação
        btnAnterior = new JButton("< Anterior");
        btnAnterior.addActionListener(e -> navegarAnterior());
        painel.add(btnAnterior);

        btnProximo = new JButton("Próximo >");
        btnProximo.addActionListener(e -> navegarProximo());
        painel.add(btnProximo);

        return painel;
    }

    private void carregarAlunosDisponiveis() {
        try {
            alunosDisponiveis.clear();
            if (comboAlunosBoletim != null) {
                comboAlunosBoletim.removeAllItems();
            }

            File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith("_boletim.txt"));

            if (arquivos == null || arquivos.length == 0) {
                if (lblStatus != null) {
                    lblStatus.setText("Nenhum boletim encontrado no diretório.");
                }
                if (textAreaBoletim != null) {
                    textAreaBoletim.setText("Nenhum arquivo de boletim foi encontrado.\n\n" +
                            "Por favor, gere os boletins primeiro antes de usar esta interface.");
                }
                return;
            }

            for (File arquivo : arquivos) {
                String nomeAluno = arquivo.getName()
                        .replace("_boletim.txt", "")
                        .replaceAll("_", " ");
                alunosDisponiveis.add(nomeAluno);
            }

            // Items para ComboBox
            if (comboAlunosBoletim != null) {
                for (String aluno : alunosDisponiveis) {
                    comboAlunosBoletim.addItem(aluno);
                }
            }

            if (lblStatus != null) {
                lblStatus.setText("Total de " + alunosDisponiveis.size() + " boletim(ns) encontrado(s).");
            }
            alunoAtualIndex = 0;
            
            if (!alunosDisponiveis.isEmpty()) {
                carregarBoletim(alunosDisponiveis.get(0));
            }
        } catch (Exception ex) {
            System.err.println("Erro ao carregar alunos: " + ex.getMessage());
            ex.printStackTrace();
            if (textAreaBoletim != null) {
                textAreaBoletim.setText("Erro ao carregar boletins: " + ex.getMessage());
            }
        }
    }

    private void carregarBoletimSelecionado() {
        int selecionado = comboAlunosBoletim.getSelectedIndex();
        if (selecionado >= 0 && selecionado < alunosDisponiveis.size()) {
            alunoAtualIndex = selecionado;
            String nomeAluno = alunosDisponiveis.get(alunoAtualIndex);
            carregarBoletim(nomeAluno);
        } else if (alunosDisponiveis.size() > 0) {
            // Volta para o primeiro aluno se a seleção for inválida
            carregarBoletim(alunosDisponiveis.get(0));
        }
    }

    private void carregarBoletim(String nomeAluno) {
        try {
            if (nomeAluno == null || nomeAluno.isEmpty()) {
                if (textAreaBoletim != null) {
                    textAreaBoletim.setText("Nome de aluno inválido.");
                }
                return;
            }

            String nomeArquivo = nomeAluno.replaceAll("\\s+", "_") + "_boletim.txt";
            File arquivo = new File(diretorio, nomeArquivo);

            if (!arquivo.exists()) {
                if (textAreaBoletim != null) {
                    textAreaBoletim.setText("Arquivo não encontrado: " + nomeArquivo);
                }
                if (lblStatus != null) {
                    lblStatus.setText("Erro ao carregar boletim.");
                }
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                StringBuilder conteudo = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    conteudo.append(linha).append("\n");
                }
                if (textAreaBoletim != null) {
                    textAreaBoletim.setText(conteudo.toString());
                    textAreaBoletim.setCaretPosition(0);
                }
                if (lblStatus != null) {
                    lblStatus.setText("Boletim de " + nomeAluno + " (" + (alunoAtualIndex + 1) 
                            + "/" + alunosDisponiveis.size() + ")");
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler arquivo: " + ex.getMessage());
            ex.printStackTrace();
            if (textAreaBoletim != null) {
                textAreaBoletim.setText("Erro ao ler arquivo: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.err.println("Erro ao carregar boletim: " + ex.getMessage());
            ex.printStackTrace();
            if (textAreaBoletim != null) {
                textAreaBoletim.setText("Erro inesperado: " + ex.getMessage());
            }
        }
    }

    private void navegarProximo() {
        try {
            if (alunoAtualIndex < alunosDisponiveis.size() - 1) {
                alunoAtualIndex++;
                if (comboAlunosBoletim != null) {
                    comboAlunosBoletim.setSelectedIndex(alunoAtualIndex);
                }
                carregarBoletimSelecionado();
            } else {
                JOptionPane.showMessageDialog(this, "Você está no último boletim.", 
                        "Navegação", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Erro ao navegar próximo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void navegarAnterior() {
        try {
            if (alunoAtualIndex > 0) {
                alunoAtualIndex--;
                if (comboAlunosBoletim != null) {
                    comboAlunosBoletim.setSelectedIndex(alunoAtualIndex);
                }
                carregarBoletimSelecionado();
            } else {
                JOptionPane.showMessageDialog(this, "Você está no primeiro boletim.", 
                        "Navegação", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println("Erro ao navegar anterior: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    new InterfaceGrafica();
                } catch (Exception ex) {
                    System.err.println("Erro ao criar InterfaceGrafica: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            System.err.println("Erro fatal: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
