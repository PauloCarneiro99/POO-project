import java.awt.Font;
import java.util.Vector;

public class EncontrePar extends BaseJogos {

	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};
	private int cor;
	private int simbolo;
	private int nroSimbolos = 3;
	private Random r = null;
	private int pos1,pos2;
	private int posClicada1, posClicada2;
	boolean botao;
	private int rodada;
	private Vector<String> simbolosUsados;

	public EncontrePar() {
		super("Encontre o Par", "Ecnontre os dois unicos simbolos que sao um par identico");
		r = new Random();//criando o rand
		simbolosUsados = new Vector<String>();
		rodada = 0;
		montaTabuleiro();
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) {
		if(botao == false){ //primeira vez que clicou no botao do tabuleiro
			posClicada1 = i;
			if(posClicada1 == pos1 || posClicada1 == pos2)
				botao = true;
			else{//ja errou, sofre penalidade e gera um novo tabuleiro
				penalidade(3);
				montaTabuleiro();
			}
		}else{//segunda vez que clicou no botao do tabuleiro
			posClicada2 = i;
			if((posClicada1 == pos1 && posClicada2 == pos2)||(posClicada1 == pos2 && posClicada2 == pos1)){
				//verifica se o jogador ganhou
				botao = false;
				rodada++;//incrementa 1 em rodadas ganhas
				if(rodada == 12)//Finaliza o jogo
					finaliza();
				nroSimbolos += r.getIntRandom(1, 3); //incrementando os simbolos que irao aparecer no jogo
				montaTabuleiro();//monto novamente o tabuleiro
			}else{//errei o par, sofro penalidade e monto um novo tabuleiro
				botao = false;
				penalidade(2);
				montaTabuleiro();
			}
		}
	}

	@Override
	void montaTabuleiro() {
		botao = false;
		limpaTabuleiro();

		//gerando qual simbolo sera repetido e qual a cor do simbolo
		cor = r.getIntRandom(5);
		simbolo = r.getIntRandom(8);
		simbolosUsados.add(simb[simbolo]+cor);

		//colocando o simbolos identicos
		pos1 = r.getIntRandom(42);
		botoes.elementAt(pos1).setFont(new Font("Arial", Font.PLAIN, 30));
		botoes.elementAt(pos1).setText(simb[simbolo]);
		botoes.elementAt(pos1).setForeground(cores[cor]);
		botoes.elementAt(pos1).setVisible(true);

		int tmp = r.getIntRandom(42);
		while(!oqTemnoBotao(tmp).equals(""))//se o conteudo do botao ja estiver ocupado, continua procurando uma posição vazia
			tmp = r.getIntRandom(42);
		pos2 = tmp;
		botoes.elementAt(pos2).setFont(new Font("Arial", Font.PLAIN, 30));
		botoes.elementAt(pos2).setText(simb[simbolo]);
		botoes.elementAt(pos2).setForeground(cores[cor]);
		botoes.elementAt(pos2).setVisible(true);

		//colocando os demais simbolos
		for(int i=0; i< nroSimbolos; i++){
			tmp = r.getIntRandom(42);
			int corAux = r.getIntRandom(5);
			int simbAux = r.getIntRandom(8);
			while(simbolosUsados.contains(simb[simbAux]+corAux)){
				corAux = r.getIntRandom(5);
				simbAux = r.getIntRandom(8);
			}
			simbolosUsados.add(simb[simbAux]+corAux);
			while(!oqTemnoBotao(tmp).equals("")){//se o conteudo do botao ja estiver ocupado, continua procurando uma posição vazia
				tmp = r.getIntRandom(42);
			}
			botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
			botoes.elementAt(tmp).setText(simb[simbAux]);
			botoes.elementAt(tmp).setForeground(cores[corAux]);
			botoes.elementAt(tmp).setVisible(true);
		}
		//System.out.println(simbolosUsados.toString());
	}

	@Override
	protected void limpaTabuleiro() {
		super.limpaTabuleiro();
		simbolosUsados.clear();
	}

}
