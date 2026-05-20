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
        try (FileWriter fw = new FileWriter(nome + "_ord_alfabetica.txt");
            BufferedWriter bw = new BufferedWriter(fw)) {
            for (Aluno prova : provasOrdenadas) {
                String respostas = String.join("", prova.getRespostas());
                bw.write(respostas + "\t" + prova.getNome() + "\t" + prova.getAcertos());
                bw.newLine();
            }
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
        try (FileWriter fw = new FileWriter(nome + "_ord_acerto.txt");
            BufferedWriter bw = new BufferedWriter(fw)) {
            for (Aluno prova : provasOrdenadas) {
                String respostas = String.join("", prova.getRespostas());
                bw.write(respostas + "\t" + prova.getNome() + "\t" + prova.getAcertos());
                bw.newLine();
            }
            bw.write("Media: " + calcularMediaTurma());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerProvasDeArquivo(String nome) {
        try (FileReader fr = new FileReader(nome + ".txt");
            BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


