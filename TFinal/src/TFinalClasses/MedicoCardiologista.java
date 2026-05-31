package TFinalClasses;

public class MedicoCardiologista extends Medico {
    public MedicoCardiologista(String nome, double valorConsulta) {
        super(nome, "Cardiologia", valorConsulta);
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
