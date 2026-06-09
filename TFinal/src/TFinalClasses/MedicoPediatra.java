package TFinalClasses;

public class MedicoPediatra extends Medico {
    public MedicoPediatra(String nome, double valorConsulta, String senha) {
        super(nome, "Pediatria", valorConsulta, senha);
    }

    public MedicoPediatra(String nome, double valorConsulta, String senha) {
        super(nome, "Pediatria", valorConsulta, senha);
    }

    @Override
    public int getPacientesPorDia() {
        return 2;
    }
}
