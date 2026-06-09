package TFinalClasses;

import TFinalExcecoes.PlanoInvalidoException;

public class MedicoDermatologista extends Medico {
    public MedicoDermatologista(String nome, double valorConsulta, String senha) {
        super(nome, "Dermatologia", valorConsulta, senha);
    }

    public MedicoDermatologista(String nome, double valorConsulta, String senha) {
        super(nome, "Dermatologia", valorConsulta, senha);
    }

    @Override
    public int getPacientesPorDia() {
        return 3;
    }

    @Override
    public boolean planoValido(String plano) {
        return plano.equalsIgnoreCase("Unimed") || plano.equalsIgnoreCase("Hapvida");
    }

    @Override
    public void adicionarPlano(String plano) throws PlanoInvalidoException {
        if(this.planoValido(plano)){
            super.adicionarPlano(plano);
        } else {
            throw new PlanoInvalidoException("O médico dermatologista atende apenas os planos Unimed e Hapvida!");
        }
    }
}
