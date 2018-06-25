// POR ENQUANTO NAO INCLUIR NA VERSAO FINAL, TA BUGADOOOOOOOOOOOOOOOOOOO

import java.awt.Font;

import javax.swing.JButton;

public class TodosDiferentes extends BaseJogos {
	
	private Random r = null;
	private int dificuldade = 4;
	private int[] seq;
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};

	public TodosDiferentes() {
		super("Todos Diferentes", "Troque os icones para que fiquem todos diferentes");
		r = new Random();
		montaTabuleiro();
	}
	
	@Override
	void montaTabuleiro() {
		seq = new int[dificuldade*2]; //Vetor com o par(cor, simbolo). [0..dif-1] = cor, [dif..dif-1] = simbolo
		for(int i = 0; i < dificuldade; i++) { //Gerando sequência
			seq[i] = r.getIntRandom(4); //Cor
			seq[i+dificuldade] = r.getIntRandom(8); //Simbolo
		}
		boolean hasRep;
		do { //Garantindo que a sequência não tem repetições
			hasRep = false; //Booleano define se há repetição na sequência ou não
			for(int j = 0; j < dificuldade; j++) {
				int auxCor = seq[j];
				int auxSimb = seq[j+dificuldade];
				for(int i = j+1; i < dificuldade; i++) {
					if((auxCor == seq[i]) && (auxSimb == seq[i+dificuldade])) //Checa se há repetição na sequência
						hasRep = true;
						seq[i] = r.getIntRandom(4); //Gerando novos valores
						seq[i+dificuldade] = r.getIntRandom(8);
				}
			}
		}while(hasRep);
		//Até aqui, deve-se ter uma sequência sem repetições de cores e símbolos
		//Garantindo que ao menos um dos icones se repetirá (no caso, o primeiro)
		for(int i = 0; i < 2; i++){
			int tmp = r.getIntRandom(42);
			while(!oqTemnoBotao(tmp).equals("")) tmp = r.getIntRandom(42); //Encontrando posição vazia no tabuleiro
			botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
			botoes.elementAt(tmp).setText(simb[seq[dificuldade]]);
			botoes.elementAt(tmp).setForeground(cores[seq[0]]);
			botoes.elementAt(tmp).setVisible(true);
		}
		for(int i = 2; i < dificuldade; i++){
			int tmp = r.getIntRandom(42);
			while(!oqTemnoBotao(tmp).equals("")) tmp = r.getIntRandom(42);
			botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
			int pos = r.getIntRandom(dificuldade);
			botoes.elementAt(tmp).setText(simb[seq[pos+dificuldade]]);
			botoes.elementAt(tmp).setForeground(cores[seq[pos]]);
			botoes.elementAt(tmp).setVisible(true);
		}
		
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
		int pos = -1;
		for(int j = 0; (j < dificuldade) && (pos == -1); j++) { //Procurando posição atual no array com a sequência
			if((oqTemnoBotao(i).equals(simb[seq[j+dificuldade]])) && (botoes.elementAt(i).getForeground() == cores[seq[j]])) {
				pos = j;
			}
		}
		//Atualizando novo ícone do vetor como próximo elemento da sequência
		botoes.elementAt(i).setText(simb[seq[((pos+1)%dificuldade)+dificuldade]]);
		botoes.elementAt(i).setForeground(cores[seq[(pos+1)%dificuldade]]);
		
		//Checar se não há repetição entre os botões
		int howManyRep = 0;
		for(int j = 0; (j < dificuldade) && (howManyRep < 2); j++) {
			howManyRep = 0;
			for(int k = 0; (k < 42) && (howManyRep < 2); k++) {
				if((oqTemnoBotao(k).equals(simb[seq[j+dificuldade]])) && (botoes.elementAt(k).getForeground() == cores[seq[j]])) {
					howManyRep++;
				}
			}
			if(howManyRep != 1) howManyRep = 2; //Sai do loop pois há mais de uma repetição ou não há repetição
		}
		if(howManyRep == 1) { //Não houve repetição na sequência
			dificuldade++;
			if(dificuldade < 9) {
				limpaTabuleiro();
				montaTabuleiro();
			}
			else
				finaliza();
		}
	}
}
