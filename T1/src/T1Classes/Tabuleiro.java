package T1Classes;

public class Tabuleiro {
    private Casa[] casas;
    // Cores ANSI para console
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String LIGHT_MAGENTA = "\u001B[95m";

    public Tabuleiro(int numCasas) {
        this.casas = new Casa[numCasas];
        for (int i = 0; i < numCasas; i++) {
            if (i == 9 || i == 24 || i == 37) {
                casas[i] = new CasaParada(i);
            } else if (i == 12) {
                casas[i] = new CasaSurpresa(i);
            } else if (i == 4 || i == 14 || i == 29) {
                casas[i] = new CasaDaSorte(i);
            } else if (i == 16 || i == 26) {
                casas[i] = new CasaVoltarCompetidor(i);
            } else if (i == 19 || i == 34) {
                casas[i] = new CasaMagica(i);
            } else {
                casas[i] = new Casa(i);
            }
        }
    }

    public Casa getCasa(int posicao){
        return casas[posicao];
    }

    // tabuleiro borda de quadrado
    public void imprimirTabuleiro(Jogador[] jogadores){
        for (int i = 0; i <= 10; i++) {
            System.out.print(mostrarSimbolo(i, jogadores));
        }
        System.out.println();

        for (int i = 1; i <= 9; i++){
            System.out.print(mostrarSimbolo(40 - i, jogadores));
            for(int j = 0; j < 9; j++){
                System.out.print("    ");
            }
            System.out.println(mostrarSimbolo(10 + i, jogadores));
        }

        for(int i = 30; i >= 20; i--){
            System.out.print(mostrarSimbolo(i, jogadores));
        }
        System.out.println("\n");
    }

    public String mostrarSimbolo(int indice, Jogador[] jogadores){
        for(Jogador j : jogadores){
            if(j.getPosicao() == indice){
                if(j.isSortudo()){
                    return getCorJogador(j) + "[+]" + RESET;
                } else if (j.isAzarado()){
                    return getCorJogador(j) + "[-]" + RESET;
                } else {
                    return getCorJogador(j) + "[#]" + RESET;
                }
            }
        }
        return String.format("[%02d]", indice + 1);
    }

    private String getCorJogador(Jogador jogador) {
    String cor = jogador.getCor().toLowerCase();
        switch (cor) {
            case "vermelho":
                return RED;
            case "verde":
                return GREEN;
            case "azul":
                return BLUE;
            case "amarelo":
                return YELLOW;
            case "roxo":
                return PURPLE;
            case "rosa":
                return LIGHT_MAGENTA;
            default:
                return RESET;
        }
    }
}
