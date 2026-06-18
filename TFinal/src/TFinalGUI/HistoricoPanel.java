package TFinalGUI;

import TFinalClasses.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistoricoPanel extends JPanel {
    private MainFrame mainFrame;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;

    public HistoricoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(MainFrame.COR_FUNDO);
        setLayout(new BorderLayout());
        criarInterface();
    }

    private void criarInterface() {
        JPanel header = PesquisaMedicoPanel.criarHeaderComVoltar("📋 Prontuário / Histórico", mainFrame);
        add(header, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(MainFrame.COR_FUNDO);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        String[] colunas = { "Data", "Médico", "Especialidade", "Diagnóstico", "Receita", "Exames", "Valor (R$)" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabelaHistorico = PesquisaMedicoPanel.criarTabela(modeloTabela);
        tabelaHistorico.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabelaHistorico.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaHistorico.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabelaHistorico.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabelaHistorico.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabelaHistorico.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaHistorico.getColumnModel().getColumn(6).setPreferredWidth(70);

        // Click para detalhes
        tabelaHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabelaHistorico.getSelectedRow();
                    if (linha >= 0) {
                        mostrarDetalhes(linha);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaHistorico);
        scroll.setBackground(MainFrame.COR_PAINEL);
        scroll.getViewport().setBackground(MainFrame.COR_PAINEL);
        scroll.setBorder(BorderFactory.createLineBorder(MainFrame.COR_BORDA));
        centro.add(scroll, BorderLayout.CENTER);

        // Dica
        JLabel dica = MainFrame.criarLabel("💡 Clique duas vezes em uma consulta para ver detalhes", 12, false);
        dica.setForeground(MainFrame.COR_TEXTO_SECUNDARIO);
        dica.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        centro.add(dica, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    public void atualizar() {
        Object usuario = mainFrame.getUsuarioLogado();
        modeloTabela.setRowCount(0);

        ArrayList<Consulta> historico = null;
        if (usuario instanceof Paciente) {
            historico = mainFrame.getSistema().getConsultasPaciente((Paciente) usuario);
        }

        if (historico != null) {
            for (Consulta c : historico) {
                modeloTabela.addRow(new Object[] {
                        c.getData(),
                        c.getMedico().getNome(),
                        c.getMedico().getEspecialidade(),
                        c.getDescricao(),
                        c.getReceita().isEmpty() ? "—" : c.getReceita(),
                        c.getExames().isEmpty() ? "—" : c.getExames(),
                        String.format("%.2f", c.getValorPago())
                });
            }
        }
    }

    private void mostrarDetalhes(int linha) {
        StringBuilder sb = new StringBuilder();
        sb.append("📅 Data: ").append(modeloTabela.getValueAt(linha, 0)).append("\n");
        sb.append("👨‍⚕️ Médico: ").append(modeloTabela.getValueAt(linha, 1)).append("\n");
        sb.append("🏥 Especialidade: ").append(modeloTabela.getValueAt(linha, 2)).append("\n");
        sb.append("📝 Diagnóstico: ").append(modeloTabela.getValueAt(linha, 3)).append("\n");
        sb.append("💊 Receita: ").append(modeloTabela.getValueAt(linha, 4)).append("\n");
        sb.append("🔬 Exames: ").append(modeloTabela.getValueAt(linha, 5)).append("\n");
        sb.append("💰 Valor: R$ ").append(modeloTabela.getValueAt(linha, 6));

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        textArea.setBackground(new Color(30, 41, 59));
        textArea.setForeground(new Color(226, 232, 240));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Detalhes da Consulta", JOptionPane.INFORMATION_MESSAGE);
    }
}
