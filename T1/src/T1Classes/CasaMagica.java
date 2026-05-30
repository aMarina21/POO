package T1Classes;

public class CasaMagica extends Casa {
    public CasaMagica(int posicao) {
        super(posicao);
    }

    @Override
    public void acao(ContextoExecucao contexto) {
        super.acao(contexto);
        
        // Verifica jogador mais atrasado
        int minPosition = 0;
        for (int i = 0; i < contexto.getJogadores().length; i++) {
            if (contexto.getJogadores()[i].getPosicao() < contexto.getJogadores()[minPosition].getPosicao()) {
                minPosition = i;
            }
        }
        
        if (contexto.getJogador().getPosicao() == contexto.getJogadores()[minPosition].getPosicao()) {
            System.out.println("Casa magica! Como voce esta em ultimo lugar, voce retorna para onde estava!");
            contexto.getJogador().setPosicao(contexto.getultimaPosicao());
        } else {
            System.out.println("Casa magica! Trocou de posicao com o jogador mais atrasado!");
            trocarPosicao(contexto.getJogador(), contexto.getJogadores());
        }
    }

    public void trocarPosicao(Jogador jogadorEmCasa, Jogador[] jogadores) {
        int posicaoMinima = 0;
        
        for (int j = 0; j < jogadores.length; j++) {
            if (jogadores[j].getPosicao() < jogadores[posicaoMinima].getPosicao()) {
                posicaoMinima = j;
            }
        }

        Jogador ultimoJogador = jogadores[posicaoMinima];
        int posicaoTemp = jogadorEmCasa.getPosicao();
        jogadorEmCasa.setPosicao(ultimoJogador.getPosicao());
        ultimoJogador.setPosicao(posicaoTemp);
        System.out.println(jogadorEmCasa.getNome() + " trocou de posicao com " + ultimoJogador.getNome());
    }

}
