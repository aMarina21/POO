package T1Classes;

import java.util.Scanner;

public class ContextoExecucao {
    private Jogador jogador;
    private Jogador[] jogadores;
    private int escolha;
    private Scanner scanner;
    private int ultimaPosicao;

    public ContextoExecucao(Jogador jogador) {
        this.jogador = jogador;
    }

    public ContextoExecucao(Jogador[] jogadores) {
        this.jogadores = jogadores;
    }

    public ContextoExecucao(Jogador jogador, Jogador[] jogadores, int escolha) {
        this.jogador = jogador;
        this.jogadores = jogadores;
        this.escolha = escolha;
    }

    public ContextoExecucao(Jogador jogador, Jogador[] jogadores, int escolha, Scanner scanner) {
        this.jogador = jogador;
        this.jogadores = jogadores;
        this.escolha = escolha;
        this.scanner = scanner;
    }

    public ContextoExecucao(Jogador jogador, Jogador[] jogadores, int escolha, Scanner scanner, int ultimaPosicao) {
        this.jogador = jogador;
        this.jogadores = jogadores;
        this.escolha = escolha;
        this.scanner = scanner;
        this.ultimaPosicao = ultimaPosicao;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador[] getJogadores() {
        return jogadores;
    }

    public int getEscolha() {
        return escolha;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int getultimaPosicao() {
        return ultimaPosicao;
    }

}
