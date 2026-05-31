package TFinalClasses;

public class MedicoPediatra extends Medico {
    public MedicoPediatra(String nome, double valorConsulta) {
        super(nome, "Pediatria", valorConsulta);
    }

    @Override
    public int getPacientesPorDia() {
        return 2;
    }
}
