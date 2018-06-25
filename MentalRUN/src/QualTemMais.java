import java.awt.Font;

public class QualTemMais extends BaseJogos {
	
	private Random r = null;
	private int cor1, cor2, simb1, simb2, qtd1, qtd2, dificuldade = 3;
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};

	public QualTemMais() {
		super("Qual Tem Mais", "Clique em qualquer icone que é o mais frequente");
		r = new Random();
		montaTabuleiro();
	}
	
	@Override
	void montaTabuleiro() {
		do {
			cor1 = r.getIntRandom(4); //Gerando duas cores
			cor2 = r.getIntRandom(4);
			simb1 = r.getIntRandom(8); //Gerando dois símbolos
			simb2 = r.getIntRandom(8);
		}while((cor1 == cor2) && (simb1 == simb2));	//Ou a cor ou os símbolos podem ser iguais, mas não ambos
		qtd1 = dificuldade; //Determinando a quantidade de símbolos
		qtd2 = dificuldade + 1;
		for(int i = 0; i < qtd1; i++){
			int tmp = r.getIntRandom(42);
			while(!oqTemnoBotao(tmp).equals("")) tmp = r.getIntRandom(42);//Encontrando posição vazia no tabuleiro
			botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
			botoes.elementAt(tmp).setText(simb[simb1]);
			botoes.elementAt(tmp).setForeground(cores[cor1]);
			botoes.elementAt(tmp).setVisible(true);
		}
		for(int i = 0; i < qtd2; i++){
			int tmp = r.getIntRandom(42);
			while(!oqTemnoBotao(tmp).equals(""))
				tmp = r.getIntRandom(42);
			botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
			botoes.elementAt(tmp).setText(simb[simb2]);
			botoes.elementAt(tmp).setForeground(cores[cor2]);
			botoes.elementAt(tmp).setVisible(true);
		}
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) {
		if(oqTemnoBotao(i).equals(simb[simb2]) && (botoes.elementAt(i).getForeground() == cores[cor2])){
			System.out.println("Acerto!");
			dificuldade++;
		}
		else {
			System.out.println("Erro!");
		}
		limpaTabuleiro();
		if(dificuldade < 9)
			montaTabuleiro();
		else
			finaliza();
	}
}
