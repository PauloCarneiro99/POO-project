import java.awt.Font;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

public class OrdemCrescente extends BaseJogos {

	private Random r = null;
	private Vector<Integer> numeros = new Vector<Integer>();
	private HashSet<Integer> foi  = new HashSet<Integer>();
	private int quantidade = 3, atual = 0, rodada = 0;
	
	
	public OrdemCrescente() {
		super("Ordem crescente", "Clique nos números em ordem crescente");
		r = new Random();//criando o rand
		montaTabuleiro();
	}

	@Override
	String oqTemnoBotao(int i) {
		return null;
	}

	@Override
	void clicouBotao(int i) {
		int num = Integer.parseInt(botoes.elementAt(i).getText());
		
		if(numeros.elementAt(atual) != num){ // errado
			penalidade(3);
			montaTabuleiro();
		}else {
			botoes.elementAt(i).setVisible(false);
			atual++;
			if(atual == quantidade){
				int aumento = r.getIntRandom(1,4);
				quantidade+=aumento;
				rodada++;
				if(rodada == 5){
					finaliza();
				}
				montaTabuleiro();
			}
		}
		
	}

	@Override
	void montaTabuleiro() {
		
		foi.clear();
		numeros.clear();
		atual = 0;
		limpaTabuleiro();
		
		for(int i=0;i<quantidade;i++){
			int at;
			
			int ini = 0 , fin = 20;
			if(rodada >= 3){
				ini = -9;
				fin = 20;
			}
			
			while(true){
				at = r.getIntRandom(ini, fin);
				if(!foi.contains(at)){ // se ainda não foi usado nesse tabuleiro 
					foi.add(at); // adiciona
					break;
				}
			}
		
			numeros.add(at);
		}
		
		Collections.sort(numeros);
		
		for(int i=0;i<quantidade;i++){
			int pos;
			while(true){
				pos = r.getIntRandom(0,42);
				if(!botoes.elementAt(pos).isVisible()){ // se ainda não esta setado
					break;
				}
			}
			
			botoes.elementAt(pos).setText(numeros.elementAt(i).toString());//coloco no botao o numero
			botoes.elementAt(pos).setForeground(cores[r.getIntRandom(4)]);
			botoes.elementAt(pos).setVisible(true);
			botoes.elementAt(pos).setFont(new Font("Arial", Font.PLAIN, 20));
		}
		
	}

}
