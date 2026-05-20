package T3Classes;
import java.io.*;

public class Gabarito {
    private String[] respostas;
    private static final int NUMERO_DE_PERGUNTAS = 10;
    private String[] normalizarRespostas(String[] respostas) {
        if (respostas.length != NUMERO_DE_PERGUNTAS) {
            throw new IllegalArgumentException("Deve haver exatamente " + NUMERO_DE_PERGUNTAS + " respostas.");
        }
        String[] normalizadas = new String[respostas.length];
        for (int i = 0; i < respostas.length; i++) {
            normalizadas[i] = respostas[i].trim().toUpperCase();
            if (!normalizadas[i].equals("V") && !normalizadas[i].equals("F")) {
                throw new IllegalArgumentException("Respostas devem ser V ou F");
            }
        }
        return normalizadas;
    }

    public Gabarito(String[] respostas) {
        this.respostas = normalizarRespostas(respostas);
    }

    public String[] getRespostas() {
        return respostas;
    }

    public String[] setRespostas(String[] perguntas) {
        this.respostas = normalizarRespostas(perguntas);
        return respostas;
    }

    public void salvarGabarito(String nome) {
        try {
            String caminhoArquivo = System.getProperty("user.dir") + File.separator + nome + "_gabarito.txt";
            FileWriter fw = new FileWriter(caminhoArquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (String resposta : respostas) {
                bw.write(resposta);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerGabarito(String nome) {
        try {
            String caminhoArquivo = System.getProperty("user.dir") + File.separator + nome + "_gabarito.txt";
            FileReader fr = new FileReader(caminhoArquivo);
            BufferedReader br = new BufferedReader(fr);
            
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler gabarito: " + nome + "_gabarito.txt");
            e.printStackTrace();
        }
    }

}