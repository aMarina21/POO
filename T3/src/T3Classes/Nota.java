package T3Classes;

public class Nota {
    private Aluno[] provas;
    private Gabarito gabarito;

    public Nota(Aluno[] provas, Gabarito gabarito) {
        this.provas = provas;
        this.gabarito = gabarito;
    }

    public Aluno[] getProvas() {
        return provas;
    }

    public Gabarito getGabarito() {
        return gabarito;
    }

    public void setProvas(Aluno[] provas) {
        this.provas = provas;
    }

    public void setGabarito(Gabarito gabarito) {
        this.gabarito = gabarito;
    }

    public void corrigirTodasAsProvas() {
        for (Aluno aluno : provas) {
            int acertos = corrigirProvaIndividual(aluno);
            aluno.setAcertos(acertos);
        }
    }

    public int corrigirProvaIndividual(Aluno aluno) {
        String[] respostasProva = aluno.getRespostas();
        String[] respostasGabarito = gabarito.getRespostas();
        String respostasStr = String.join("", respostasProva);
        
        if (respostasStr.equals("VVVVVVVVVV") || respostasStr.equals("FFFFFFFFFF")) {
            return 0;
        }
        
        int acertos = 0;
        for (int i = 0; i < respostasProva.length; i++) {
            if (respostasProva[i].equals(respostasGabarito[i])) {
                acertos++;
            }
        }
        return acertos;
    }

    public double calcularMediaTurma() {
        corrigirTodasAsProvas();
        int totalAcertos = 0;
        for (Aluno aluno : provas) {
            totalAcertos += aluno.getAcertos();
        }
        double media = (double) totalAcertos / (provas.length * 10);
        return media;
    }

    public double calcularMediaAluno(Aluno aluno) {
        int acertos = aluno.getAcertos();
        return (double) acertos / 10.0;
    }
}
