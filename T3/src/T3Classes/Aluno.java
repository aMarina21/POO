package T3Classes;

public class Aluno {
    private String[] respostas;
    private String nome;
    private static final int NUMERO_DE_PERGUNTAS = 10;
    private int acertos;
    private String[] normalizarRespostas(String[] respostas) {
        if (respostas.length != NUMERO_DE_PERGUNTAS) {
            throw new IllegalArgumentException("Deve haver exatamente " + NUMERO_DE_PERGUNTAS + " respostas.");
        }
        String[] normalizadas = new String[respostas.length];
        for (int i = 0; i < respostas.length; i++) {
            normalizadas[i] = respostas[i].trim().toUpperCase();
            if (!normalizadas[i].equals("V") && !normalizadas[i].equals("F")) {
                throw new IllegalArgumentException("Resposta " + (i + 1) + " inválida. Use apenas V ou F.");
            }
        }
        return normalizadas;
    }

    public Aluno(String[] respostas, String nome) {
        this.respostas = normalizarRespostas(respostas);
        this.nome = nome;
    }

    public String[] getRespostas() {
        return respostas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] setRespostas(String[] perguntas) {
        this.respostas = normalizarRespostas(perguntas);
        return respostas;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }
}
