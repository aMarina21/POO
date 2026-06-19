package TFinalDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static Connection instance;
    private final static String URL = "jdbc:sqlite:clinica_medica.db";

    private DBConnection(){
    }

    public static Connection getConnection(){
        return getInstance();
    }

    public static Connection getInstance(){
        try {
            if(instance == null || instance.isClosed()){
                instance = DriverManager.getConnection(URL);
                criarTabelas(instance);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return instance;
    }

    public static void criarTabelas(Connection conexao){
        String sqlPacientes = "CREATE TABLE IF NOT EXISTS pacientes (" +
                "nome TEXT PRIMARY KEY, idade INTEGER, planoSaude TEXT, senha TEXT);";

        String sqlMedicos = "CREATE TABLE IF NOT EXISTS medicos (" +
                "nome TEXT PRIMARY KEY, especialidade TEXT, valorConsulta REAL, senha TEXT, tipo TEXT);";

        String sqlConsultas = "CREATE TABLE IF NOT EXISTS consultas (" +
                "medico TEXT, paciente TEXT, data TEXT, descricao TEXT, receita TEXT, exames TEXT, valor REAL);";

        String sqlAgendamentos = "CREATE TABLE IF NOT EXISTS agendamentos(" +
                "medico TEXT, paciente TEXT, data TEXT);";

        String sqlAvaliacoes = "CREATE TABLE IF NOT EXISTS avaliacoes (" +
                "medico TEXT, paciente TEXT, descricao TEXT, avaliacao INTEGER);";

        try (Statement statement = conexao.createStatement()){
            statement.execute(sqlPacientes);
            statement.execute(sqlMedicos);
            statement.execute(sqlConsultas);
            statement.execute(sqlAgendamentos);
            statement.execute(sqlAvaliacoes);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar as tabelas: " + e.getMessage());
        }
    }
}
