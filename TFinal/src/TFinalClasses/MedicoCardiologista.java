package TFinalClasses;

public class MedicoCardiologista extends Medico {
    public MedicoCardiologista(String nome, double valorConsulta, String senha) {
        super(nome, "Cardiologia", valorConsulta, senha);
    }

    public MedicoCardiologista(String nome, double valorConsulta, String senha) {
        super(nome, "Cardiologia", valorConsulta, senha);
    }

    @Override
    public double getValorConsulta() {
        return super.getValorConsulta() + (super.getValorConsulta() * 0.25);
    }

    @Override
    public int getPacientesPorDia() {
        return 3;
    }
}
