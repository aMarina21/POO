package TFinalDados;

import TFinalClasses.Agendamento;
import TFinalClasses.Agendamento;
import TFinalClasses.Medico;
import TFinalClasses.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AgendamentoDAO {
    public void cadastrarAgendamento(Agendamento agendamento){
        String sql = "INSERT INTO agendamentos (medico, paciente, data) VALUES (?, ?, ?)";

        try(Connection conexao = DBConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
        ) {
            statement.setString(1, agendamento.getMedico().getNome());
            statement.setString(2, agendamento.getPaciente().getNome());
            statement.setString(3, agendamento.getDataConsulta().toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar agendamento: " + e.getMessage());
        }
    }

    public ArrayList<Agendamento> buscarAgendamentos(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos){
        ArrayList<Agendamento> listaAgendamentos = new ArrayList<>();
        String sql  = "SELECT * FROM agendamentos";

        try(Connection conexao = DBConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
            while(rs.next()){
                //medico, paciente, data
                String nomeMedico = rs.getString("medico");
                String nomePaciente = rs.getString("paciente");

                Paciente pacienteEncontrado = null;
                for(Paciente p : pacientes){
                    if(p.getNome().equalsIgnoreCase(nomePaciente)){
                        pacienteEncontrado = p;
                        break;
                    }
                }

                Medico medicoEncontrado = null;
                for(Medico m : medicos){
                    if(m.getNome().equalsIgnoreCase(nomeMedico)){
                        medicoEncontrado = m;
                        break;
                    }
                }

                String dataString = rs.getString("data");
                LocalDate data = LocalDate.parse(dataString);

                Agendamento agendamento = new Agendamento(medicoEncontrado, pacienteEncontrado, data);
                listaAgendamentos.add(agendamento);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaAgendamentos;
    }

    public void removerAgendamento(Agendamento agendamento){
        String sql = "DELETE FROM agendamentos WHERE medico = ? AND paciente = ? AND data = ?";
        try(Connection conexao = DBConnection.getConnection();
            PreparedStatement statement = conexao.prepareStatement(sql);
        ) {
            statement.setString(1, agendamento.getMedico().getNome());
            statement.setString(2, agendamento.getPaciente().getNome());
            statement.setString(3, agendamento.getDataConsulta().toString());
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
