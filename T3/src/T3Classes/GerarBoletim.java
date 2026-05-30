package T3Classes;

import java.io.*;

public class GerarBoletim {
    private File diretorio;

    private static class AlunoBoletim {
        String nome;
        String[] disciplinas;
        int[] notas;
        int numeroDisc;

        AlunoBoletim(String nome, int maxDisciplinas) {
            this.nome = nome;
            this.disciplinas = new String[maxDisciplinas];
            this.notas = new int[maxDisciplinas];
            this.numeroDisc = 0;
        }

        void adicionarNota(String disciplina, int nota) {
            disciplinas[numeroDisc] = disciplina;
            notas[numeroDisc] = nota;
            numeroDisc++;
        }

        double calcularMedia() {
            if (numeroDisc == 0) return 0;
            int total = 0;
            for (int i = 0; i < numeroDisc; i++) {
                total += notas[i];
            }
            return (double) total / numeroDisc;
        }

        boolean aprovado() {
            return calcularMedia() >= 7.0;
        }
    }

    public GerarBoletim() {
        this(new File("."));
    }

    public GerarBoletim(File diretorio) {
        this.diretorio = diretorio.getAbsoluteFile();
    }

    public void processarArquivos() {
        System.out.println("Lendo arquivos de resultados...");
        
        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".txt"));
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo de resultados encontrado.");
            return;
        }

        AlunoBoletim[] alunos = new AlunoBoletim[100]; 
        int totalAlunos = 0;

        for (File arquivo : arquivos) {
            String nomeDisciplina = arquivo.getName().replace(".txt", "");

            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    // Pula linhas vazias e a linha de média
                    if (linha.trim().isEmpty() || linha.startsWith("Media:")) {
                        continue;
                    }

                    String[] partes = linha.split("\t");
                    if (partes.length >= 3) {
                        String nomeAluno = partes[1];
                        int nota = Integer.parseInt(partes[2]);

                        // Encontra aluno no array
                        int indexAluno = -1;
                        for (int i = 0; i < totalAlunos; i++) {
                            if (alunos[i].nome.equals(nomeAluno)) {
                                indexAluno = i;
                                break;
                            }
                        }

                        if (indexAluno == -1) {
                            // Novo aluno
                            alunos[totalAlunos] = new AlunoBoletim(nomeAluno, 20); // Max 20 disciplinas
                            indexAluno = totalAlunos;
                            totalAlunos++;
                        }

                        alunos[indexAluno].adicionarNota(nomeDisciplina, nota);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + arquivo.getName());
                e.printStackTrace();
            }
        }

        System.out.println("Gerando boletins...");
        for (int i = 0; i < totalAlunos; i++) {
            gerarBoletimAluno(alunos[i]);
        }

        System.out.println("Processo concluido.");
    }


    private void gerarBoletimAluno(AlunoBoletim aluno) {
        String nomeArquivo = aluno.nome.replaceAll("\\s+", "_") + "_boletim.txt";
        String caminhoCompleto = diretorio.getAbsolutePath() + File.separator + nomeArquivo;
        double media = aluno.calcularMedia();
        boolean aprovado = aluno.aprovado();

        try {
            FileWriter fw = new FileWriter(caminhoCompleto);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("=== Boletim de " + aluno.nome + " ===");
            bw.newLine();
            bw.newLine();

            for (int i = 0; i < aluno.numeroDisc; i++) {
                bw.write("Disciplina: " + aluno.disciplinas[i]);
                bw.newLine();
                bw.write("  Nota: " + aluno.notas[i] + "/10");
                bw.newLine();
            }

            bw.newLine();
            bw.write("Media geral: " + String.format("%.2f", media));
            bw.newLine();
            bw.write("Situacao: " + (aprovado ? "Aprovado" : "Reprovado"));
            bw.newLine();

            bw.close();
            fw.close();

            System.out.println("Boletim gerado: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao gerar boletim para " + aluno.nome);
            e.printStackTrace();
        }
    }
}
