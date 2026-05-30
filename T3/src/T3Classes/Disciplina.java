package T3Classes;
import java.io.*;

public class Disciplina {
    private String nome;
    private Aluno[] provas;
    private Gabarito gabarito;
    private Nota nota;

    public Disciplina(String nome, Aluno[] provas, Gabarito gabarito) {
        this.nome = nome;
        this.provas = provas;
        this.gabarito = gabarito;
        this.nota = new Nota(provas, gabarito);
    }

    public String getNome() {
        return nome;
    }

    public Aluno[] getProvas() {
        return provas;
    }

    public Gabarito getGabarito() {
        return gabarito;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProvas(Aluno[] provas) {
        this.provas = provas;
        this.nota.setProvas(provas);
    }

    public void setGabarito(Gabarito gabarito) {
        this.gabarito = gabarito;
        this.nota.setGabarito(gabarito);
    }

    public void corrigirTodasAsProvas() {
        nota.corrigirTodasAsProvas();
    }

    public double calcularMediaTurma() {
        return nota.calcularMediaTurma();
    }

    public void ordenarAlfabeticamente() {
        corrigirTodasAsProvas();
        Aluno[] provasOrdenadas = provas.clone();
        for (int i = 0; i < provasOrdenadas.length - 1; i++) {
            for (int j = 0; j < provasOrdenadas.length - i - 1; j++) {
                if (provasOrdenadas[j].getNome().compareTo(provasOrdenadas[j + 1].getNome()) > 0) {
                    Aluno temp = provasOrdenadas[j];
                    provasOrdenadas[j] = provasOrdenadas[j + 1];
                    provasOrdenadas[j + 1] = temp;
                }
            }
        }
        try {
            String caminhoArquivo = System.getProperty("user.dir") + File.separator + nome + ".txt";
            FileWriter fw = new FileWriter(caminhoArquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Aluno prova : provasOrdenadas) {
                String respostas = String.join("", prova.getRespostas());
                bw.write(respostas + "\t" + prova.getNome() + "\t" + prova.getAcertos());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ordernarPorAcertos() {
        corrigirTodasAsProvas();
        Aluno[] provasOrdenadas = provas.clone();
        for (int i = 0; i < provasOrdenadas.length - 1; i++) {
            for (int j = 0; j < provasOrdenadas.length - i - 1; j++) {
                if (provasOrdenadas[j].getAcertos() < provasOrdenadas[j + 1].getAcertos()) {
                    Aluno temp = provasOrdenadas[j];
                    provasOrdenadas[j] = provasOrdenadas[j + 1];
                    provasOrdenadas[j + 1] = temp;
                }
            }
        }
        try {
            String caminhoArquivo = System.getProperty("user.dir") + File.separator + nome + ".txt";
            FileWriter fw = new FileWriter(caminhoArquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Aluno prova : provasOrdenadas) {
                String respostas = String.join("", prova.getRespostas());
                bw.write(respostas + "\t" + prova.getNome() + "\t" + prova.getAcertos());
                bw.newLine();
            }
            bw.write("Media: " + calcularMediaTurma());
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerProvasDeArquivo(String nome) {
        try {
            String caminhoArquivo = System.getProperty("user.dir") + File.separator + nome + ".txt";
            FileReader fr = new FileReader(caminhoArquivo);
            BufferedReader br = new BufferedReader(fr);
            
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + nome + ".txt");
            e.printStackTrace();
        }
    }

}


