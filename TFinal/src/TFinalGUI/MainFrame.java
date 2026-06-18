package TFinalGUI;

import TFinalClasses.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SistemaClinica sistema;
    private Object usuarioLogado; // Medico ou Paciente

    // Nomes dos cards
    public static final String LOGIN = "LOGIN";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String PESQUISA_MEDICO = "PESQUISA_MEDICO";
    public static final String AGENDAMENTO = "AGENDAMENTO";
    public static final String CONSULTA = "CONSULTA";
    public static final String AVALIACAO = "AVALIACAO";
    public static final String HISTORICO = "HISTORICO";
    public static final String ESTATISTICAS = "ESTATISTICAS";

    // Cores do tema
    public static final Color COR_FUNDO = new Color(15, 23, 42);
    public static final Color COR_PAINEL = new Color(30, 41, 59);
    public static final Color COR_CARD = new Color(51, 65, 85);
    public static final Color COR_PRIMARIA = new Color(56, 189, 248);
    public static final Color COR_SECUNDARIA = new Color(168, 85, 247);
    public static final Color COR_SUCESSO = new Color(74, 222, 128);
    public static final Color COR_ERRO = new Color(248, 113, 113);
    public static final Color COR_TEXTO = new Color(226, 232, 240);
    public static final Color COR_TEXTO_SECUNDARIO = new Color(148, 163, 184);
    public static final Color COR_BORDA = new Color(71, 85, 105);

    // Painéis
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private PesquisaMedicoPanel pesquisaMedicoPanel;
    private AgendamentoPanel agendamentoPanel;
    private ConsultaPanel consultaPanel;
    private AvaliacaoPanel avaliacaoPanel;
    private HistoricoPanel historicoPanel;
    private EstatisticasPanel estatisticasPanel;

    public MainFrame() {
        String path = "src/TFinalArquivos";
        if (!new File(path).exists()) {
            path = "TFinal/src/TFinalArquivos";
            if (!new File(path).exists()) {
                path = "POO/TFinal/src/TFinalArquivos";
            }
        }
        sistema = new SistemaClinica(path);
        sistema.criarDadosIniciais();

        setTitle("Clínica Médica — Sistema de Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 680);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setBackground(COR_FUNDO);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(COR_FUNDO);

        criarPaineis();
        add(mainPanel);
        mostrarTela(LOGIN);
        setVisible(true);
    }

    private void criarPaineis() {
        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);
        pesquisaMedicoPanel = new PesquisaMedicoPanel(this);
        agendamentoPanel = new AgendamentoPanel(this);
        consultaPanel = new ConsultaPanel(this);
        avaliacaoPanel = new AvaliacaoPanel(this);
        historicoPanel = new HistoricoPanel(this);
        estatisticasPanel = new EstatisticasPanel(this);

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(dashboardPanel, DASHBOARD);
        mainPanel.add(pesquisaMedicoPanel, PESQUISA_MEDICO);
        mainPanel.add(agendamentoPanel, AGENDAMENTO);
        mainPanel.add(consultaPanel, CONSULTA);
        mainPanel.add(avaliacaoPanel, AVALIACAO);
        mainPanel.add(historicoPanel, HISTORICO);
        mainPanel.add(estatisticasPanel, ESTATISTICAS);
    }

    public void mostrarTela(String nomeTela) {
        // Atualizar dados do painel antes de exibir
        switch (nomeTela) {
            case DASHBOARD:
                dashboardPanel.atualizar();
                break;
            case PESQUISA_MEDICO:
                pesquisaMedicoPanel.atualizar();
                break;
            case AGENDAMENTO:
                agendamentoPanel.atualizar();
                break;
            case CONSULTA:
                consultaPanel.atualizar();
                break;
            case AVALIACAO:
                avaliacaoPanel.atualizar();
                break;
            case HISTORICO:
                historicoPanel.atualizar();
                break;
            case ESTATISTICAS:
                estatisticasPanel.atualizar();
                break;
        }
        cardLayout.show(mainPanel, nomeTela);
    }

    public void mostrarAgendamentoParaMedico(Medico medico) {
        agendamentoPanel.setMedicoSelecionado(medico);
        mostrarTela(AGENDAMENTO);
    }

    public SistemaClinica getSistema() {
        return sistema;
    }

    public Object getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Object usuario) {
        this.usuarioLogado = usuario;
    }

    public void logout() {
        this.usuarioLogado = null;
        mostrarTela(LOGIN);
    }

    // ==================== COMPONENTES ESTILIZADOS ====================

    public static String obterFonteParaTexto(String texto) {
        if (texto != null) {
            for (int i = 0; i < texto.length();) {
                int cp = texto.codePointAt(i);
                if (cp > 0x2000) {
                    return "Segoe UI Emoji";
                }
                i += Character.charCount(cp);
            }
        }
        return "Segoe UI";
    }

    public static JButton criarBotao(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font(obterFonteParaTexto(texto), Font.BOLD, 14));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(200, 42));
        botao.setOpaque(true);

        Color corHover = corFundo.brighter();
        botao.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        botao.setBackground(corHover);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        botao.setBackground(corFundo);
                    }
                });
        return botao;
    }

    public static JTextField criarCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setBackground(COR_CARD);
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(300, 40));

        campo.setText(placeholder);
        campo.setForeground(COR_TEXTO_SECUNDARIO);
        campo.addFocusListener(
                new java.awt.event.FocusAdapter() {
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (campo.getText().equals(placeholder)) {
                            campo.setText("");
                            campo.setForeground(COR_TEXTO);
                        }
                    }

                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (campo.getText().isEmpty()) {
                            campo.setText(placeholder);
                            campo.setForeground(COR_TEXTO_SECUNDARIO);
                        }
                    }
                });
        return campo;
    }

    public static JPasswordField criarCampoSenha(String placeholder) {
        JPasswordField campo = new JPasswordField();
        campo.setBackground(COR_CARD);
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(300, 40));
        campo.setEchoChar((char) 0);
        campo.setText(placeholder);
        campo.setForeground(COR_TEXTO_SECUNDARIO);
        campo.addFocusListener(
                new java.awt.event.FocusAdapter() {
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (String.valueOf(campo.getPassword()).equals(placeholder)) {
                            campo.setText("");
                            campo.setForeground(COR_TEXTO);
                            campo.setEchoChar('●');
                        }
                    }

                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (campo.getPassword().length == 0) {
                            campo.setEchoChar((char) 0);
                            campo.setText(placeholder);
                            campo.setForeground(COR_TEXTO_SECUNDARIO);
                        }
                    }
                });
        return campo;
    }

    public static JLabel criarLabel(
            String texto,
            int tamanho,
            boolean negrito) {
        JLabel label = new JLabel(texto);
        label.setForeground(COR_TEXTO);
        label.setFont(
                new Font(
                        obterFonteParaTexto(texto),
                        negrito ? Font.BOLD : Font.PLAIN,
                        tamanho));
        return label;
    }

    public static JPanel criarPainelCard() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_PAINEL);
        painel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_BORDA, 1),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return painel;
    }

    public static JTextArea criarAreaTexto() {
        JTextArea area = new JTextArea();
        area.setBackground(COR_CARD);
        area.setForeground(COR_TEXTO);
        area.setCaretColor(COR_TEXTO);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static String getTextoOuVazio(JTextField campo, String placeholder) {
        String texto = campo.getText().trim();
        if (texto.equals(placeholder))
            return "";
        return texto;
    }
}
