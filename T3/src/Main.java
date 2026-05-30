import T3Classes.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Bem-vindo ao sistema de correcao de provas!");

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("-----ESCOLHA UM OPCAO-----");
            System.out.println("1 - Criar novo(s) gabarito(s)\n2 - Ler gabaritos já existentes\n3 - Ler boletims de alunos");
            int escolha = sc.nextInt();
            sc.nextLine();

            Disciplina[] disciplinas = null;
            int numeroDeAlunos = 0;

            if (escolha == 2) {
                System.out.println("Digite a disciplina que deseja ler os gabaritos: ");
                String disciplina = sc.nextLine();

                String baseDir = System.getProperty("user.dir") + java.io.File.separator;
                String[] respostasGabarito = new String[10];
                try {
                    String gab_path = baseDir + disciplina + "_gabarito.txt";
                    System.out.println("Tentando ler gabarito de: " + gab_path);
                    try (BufferedReader brGabarito = new BufferedReader(new FileReader(gab_path))) {
                        for (int i = 0; i < 10; i++) {
                            respostasGabarito[i] = brGabarito.readLine();
                        }
                        Gabarito gabarito = new Gabarito(respostasGabarito);

                        ArrayList<Aluno> listaAlunos = new ArrayList<>();
                        String alunos_path = baseDir + disciplina + ".txt";
                        System.out.println("Tentando ler provas de: " + alunos_path);
                        try (BufferedReader brAlunos = new BufferedReader(new FileReader(alunos_path))) {
                            String linha;
                        while ((linha = brAlunos.readLine()) != null) {
                            if (linha.trim().isEmpty() || linha.startsWith("Media")) continue;
                            String[] divisoes = linha.split("\t");

                            if(divisoes.length >= 2){
                                String divRespostas = divisoes[0].trim();
                                String nomeAluno = divisoes[1].trim();

                                if(divRespostas.length() >= 10){
                                    String[] listaRespostas = new String[10];
                                    for(int i = 0; i < 10; i++){
                                        listaRespostas[i] = String.valueOf(divRespostas.charAt(i));
                                    }
                                    listaAlunos.add(new Aluno(listaRespostas, nomeAluno));
                                }
                            }
                        }
                    }
                    disciplinas = new Disciplina[1];
                    disciplinas[0] = new Disciplina(disciplina, listaAlunos.toArray(new Aluno[0]), gabarito);
                        }
                } catch (Exception e) {
                    System.out.println("ERRO: Nao foi possivel ler os arquivos da disciplina '" + disciplina + "'");
                    System.out.println("Detalhes do erro: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (escolha == 3) {
                System.out.println("Abrindo visualizador de boletins...");
                new InterfaceGrafica(new java.io.File(System.getProperty("user.dir")));
                return;
            } else {
                // Registra alunos
                System.out.println("Digite o numero de alunos:");
                numeroDeAlunos = sc.nextInt();
                sc.nextLine();

                String[] nomesAlunos = new String[numeroDeAlunos];
                for (int i = 0; i < numeroDeAlunos; i++) {
                    System.out.println("Digite o nome do aluno " + (i + 1) + ":");
                    nomesAlunos[i] = sc.nextLine();
                }

                // Registra disciplinas e provas
                System.out.println("Digite o numero de disciplinas:");
                int numeroDeDisciplinas = sc.nextInt();
                sc.nextLine();

                disciplinas = new Disciplina[numeroDeDisciplinas];
                for (int i = 0; i < numeroDeDisciplinas; i++) {
                    System.out.println("\n=== Disciplina " + (i + 1) + " ===");
                    System.out.println("Digite o nome da disciplina:");
                    String nomeDisciplina = sc.nextLine();

                    System.out.println("Digite as respostas do gabarito (10 respostas, V ou F):");
                    String[] respostasGabarito = sc.nextLine().split("");
                    Gabarito gabarito = new Gabarito(respostasGabarito);
                    gabarito.salvarGabarito(nomeDisciplina);

                    // Registrar provas dos alunos para cada disciplina
                    Aluno[] provas = new Aluno[numeroDeAlunos];
                    for (int j = 0; j < numeroDeAlunos; j++) {
                        System.out.println("Digite as respostas de " + nomesAlunos[j] + " para " + nomeDisciplina + " (10 respostas, V ou F):");
                        String[] respostasAluno = sc.nextLine().split("");
                        provas[j] = new Aluno(respostasAluno, nomesAlunos[j]);
                    }

                    disciplinas[i] = new Disciplina(nomeDisciplina, provas, gabarito);
                }

                // Corrige todas as provas e salva os resultados dos boletins usando metodo alfabetico como padrao
                for (Disciplina disc : disciplinas) {
                    disc.corrigirTodasAsProvas();
                    disc.ordenarAlfabeticamente();
                }
            }

            // Menu e visualizacao de resultados
            if (disciplinas != null) {
                while (true) {
                    System.out.println("\n=== Menu Principal ===");
                    System.out.println("Selecione uma opcao:");
                    System.out.println("1 - Selecionar disciplina");
                    System.out.println("2 - Gerar boletins");
                    System.out.println("3 - Sair");
                    int opcaoPrincipal = sc.nextInt();
                    sc.nextLine();

                    if (opcaoPrincipal == 1) {
                        System.out.println("Disciplinas disponiveis:");
                        for (int i = 0; i < disciplinas.length; i++) {
                            System.out.println((i + 1) + " - " + disciplinas[i].getNome());
                        }
                        System.out.println("Escolha uma disciplina:");
                        int escolhaDisciplina = sc.nextInt() - 1;
                        sc.nextLine();

                        if (escolhaDisciplina < 0 || escolhaDisciplina >= disciplinas.length) {
                            System.out.println("Opcao invalida.");
                            continue;
                        }

                        Disciplina disciplinaAtual = disciplinas[escolhaDisciplina];
                        while (true) {
                            System.out.println("\n=== Menu da Disciplina: " + disciplinaAtual.getNome() + " ===");
                            System.out.println("1 - Gabarito");
                            System.out.println("2 - Resultados Alfabeticamente");
                            System.out.println("3 - Resultados por Acertos");
                            System.out.println("4 - Voltar ao menu principal");
                            int opcao = sc.nextInt();
                            sc.nextLine();

                            if (opcao == 1) {
                                disciplinaAtual.getGabarito().lerGabarito(disciplinaAtual.getNome());
                            } else if (opcao == 2) {
                                disciplinaAtual.ordenarAlfabeticamente();
                                disciplinaAtual.lerProvasDeArquivo(disciplinaAtual.getNome());
                            } else if (opcao == 3) {
                                disciplinaAtual.ordernarPorAcertos();
                                disciplinaAtual.lerProvasDeArquivo(disciplinaAtual.getNome());
                            } else if (opcao == 4) {
                                break;
                            }
                        }
                    } else if (opcaoPrincipal == 2) {
                        GerarBoletim geradorBoletim = new GerarBoletim(new java.io.File(System.getProperty("user.dir")));
                        geradorBoletim.processarArquivos();


                        System.out.println("\nDeseja visualizar os boletins em uma interface grafica? (S/N)");
                        String respostaGUI = sc.nextLine().trim().toUpperCase();
                        if (respostaGUI.equals("S")) {
                            System.out.println("Abrindo interface grafica...");
                            System.out.println("Fechando console. Use a interface grafica para sair.\n");
                            new InterfaceGrafica(new java.io.File(System.getProperty("user.dir")));
                            return;
                        }
                    } else if (opcaoPrincipal == 3) {
                        System.out.println("Saindo do sistema. Obrigado por usar!");
                        break;
                    } else {
                        System.out.println("Opcao invalida. Tente novamente.");
                    }
                }
            }
        }
    }
}
