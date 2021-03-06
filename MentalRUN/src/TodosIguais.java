import java.awt.Font;

public class TodosIguais extends BaseJogos {

	private Random r = null;
	private int cor1, cor2, simb1, simb2, qtd1, qtd2, rodada = 5;
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};

	public TodosIguais() {
		super("Todos Iguais", "Clique no icone para muda-lo, deixe todos iguais");
		r = new Random();//criando o rand
		montaTabuleiro();
	}

	@Override
	void montaTabuleiro(){
		cor1 = cor2 = r.getIntRandom(5);
		while(cor1 == cor2)
			cor2 = r.getIntRandom(5);
		simb1 = simb2 = r.getIntRandom(8);
		while(simb1 == simb2)
			simb2 = r.getIntRandom(8);
		qtd1 = r.getIntRandom(2, rodada);
		qtd2 = rodada - qtd1;
		for(int i = 0; i < qtd1; i++){
			int tmp = r.getIntRandom(42);
			while(!oqTemnoBotao(tmp).equals(""))
				tmp = r.getIntRandom(42);
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
		rodada += 2;
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) {
		if(oqTemnoBotao(i).equals(simb[simb1])){
			botoes.elementAt(i).setText(simb[simb2]);
			botoes.elementAt(i).setForeground(cores[cor2]);
			qtd1--;
			qtd2++;
		}
		else{
			botoes.elementAt(i).setText(simb[simb1]);
			botoes.elementAt(i).setForeground(cores[cor1]);
			qtd1++;
			qtd2--;
		}
		if(qtd1 == 0 || qtd2 == 0){
			limpaTabuleiro();
			if(rodada < 26)
				montaTabuleiro();
			else
				finaliza();
		}
	}

}
