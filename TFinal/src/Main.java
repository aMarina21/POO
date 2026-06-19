import TFinalGUI.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        TFinalDados.DBConnection.getConnection();
        try {
            SwingUtilities.invokeLater(() -> {
                new MainFrame();
            });
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a aplicação:");
            e.printStackTrace();
        }
    }
}
