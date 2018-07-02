import java.awt.Font;
import java.util.Vector;

public class SequenciaNumerica extends BaseJogos {

	private Random r = null;
	private Vector<Integer> usados = null;
	private int rodada;

	public SequenciaNumerica() {
		super("Sequência Númerica", "Clique nos números em ordem crescente");//contrutor da superclasse, nome do jogo e como joga ele
		r = new Random();//criando o rand
		usados = new Vector<Integer>();//crio um vetor pra saber se ja coloquei aquele numero na tela pra evitar numeros iguais
		rodada = 1;
		montaTabuleiro();
	}

	private boolean ehPrimeiro(int i){
		return i == rodada;
	}

	@Override
	void montaTabuleiro(){
		for(int i = 0; i < 42; i++){
			int atual = r.getIntRandom(42);//em cada posicao da matrix gero um numero entre 1 e o maximo (linha 12)
			while(usados.contains(atual))//se ele ja tiver sido usado
				atual = r.getIntRandom(42);//gero outro
			usados.add(atual);//anoto no vetor que agora esse numero ja foi usado
			botoes.elementAt(i).setText(Integer.toString(atual+1));//coloco no botao o numero
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(5)]);
			botoes.elementAt(i).setVisible(true);
			botoes.elementAt(i).setFont(new Font("Arial", Font.PLAIN, 20));
		}
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) {
		try {
			if(ehPrimeiro(Integer.parseInt(oqTemnoBotao(i)))){
				botoes.elementAt(i).setText("");
				botoes.elementAt(i).setVisible(false);
				rodada++;
			}
			else{
				penalidade(3);
			}
		} catch (Exception e) {}
		if(rodada == 43){
			finaliza();
		}
	}

}
