import java.awt.Font;

import javax.swing.JButton;

public class TodosIguais extends BaseJogos {
	
	//https://www.facebook.com/notes/obx-creative/ascii-symbols-to-use-on-facebook-have-fun-/10150244846134770/

	private Random r = null;
	private int cor1, cor2, simb1, simb2, qtd1, qtd2, rodada = 5;
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};

	public TodosIguais() {
		super("Todos Iguais", "Troque os icones para que fiquem todos iguais");
		r = new Random();//criando o rand
		montaTabuleiro();
	}
	
	private void montaTabuleiro(){
		cor1 = cor2 = r.getIntRandom(4);
		while(cor1 == cor2)
			cor2 = r.getIntRandom(4);
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
	
	private void limpaTabuleiro(){
		for(JButton botao : botoes){
			botao.setText("");
			botao.setVisible(false);
		}
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
			if(rodada < 16)
				montaTabuleiro();
			else
				finaliza();
		}
	}

}
