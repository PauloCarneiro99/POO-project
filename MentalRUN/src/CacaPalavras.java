import java.awt.Font;
import java.util.HashSet;
import java.util.Vector;

public class CacaPalavras extends BaseJogos {

	private Random r = null;
	private Vector<String> palavras = new Vector<String>();
	private HashSet<Integer> posicoesResp = new HashSet<Integer>();
	
	
	public CacaPalavras() {
		super("Caça Palavras", "Clique nos botões que formam a palavra desejada");
		
		palavras.add("JOGO");
		palavras.add("VALE");
		palavras.add("GANHAR");
		palavras.add("NOTA");
		palavras.add("DEZ");
		palavras.add("DELA");
		palavras.add("JAVA");
		palavras.add("AMOR");
		palavras.add("IGUAIS");
		palavras.add("CAÇA");
		palavras.add("RAPIDO");
		
		r = new Random();//criando o rand
		montaTabuleiro();
		
		
	}

	
	private void montaTabuleiro(){
		for(int i = 0; i < 42; i++){
			Character c = (char) (r.getIntRandom(26) + 'A');
			botoes.elementAt(i).setText(c.toString());//coloco no botao o numero
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(4)]);
			botoes.elementAt(i).setVisible(true);
			botoes.elementAt(i).setFont(new Font("Arial", Font.PLAIN, 20));
		}
		
		int idPalavra = r.getIntRandom(palavras.size()); // sorteia a palavra
		int vertical = r.getIntRandom(2); // sorteia se vai ficar na vertical ou horizontal
		int inverso = r.getIntRandom(2); // sorteia se vai ficar escrito de tras para frente
		
		vertical = 0;
		inverso = 0;
		
		int tamPal = palavras.elementAt(idPalavra).length();
		while(true) {
			int id = r.getIntRandom(42);
			System.out.println(palavras.elementAt(idPalavra) + "\n" + id);
			
			
			if(vertical == 1) { // vai ficar na vertical
				
				if(inverso == 1 && id-(tamPal-1) >= 0) { // da para ser escrito de traz para frente 
					
					break;
				}else if(id+(tamPal-1) < 6) { // da pra escrever
						
					break;
				}
				
			}else { // vai ficar na horizontal
				
				if(inverso == 1 && (id%7)-(tamPal-1) >= 0) {
					int cont = 0;
					for(int i=id;i>=id-(tamPal-1);i--,cont++) {
						botoes.elementAt(i).setText(Character.toString(palavras.elementAt(idPalavra).charAt(cont)));
						//botoes.elementAt(i).setForeground(cores[2]); // TIRAR DEPOIS
						posicoesResp.add(i);
						System.out.println(i);
					}
					
					break;
				}else if((id%7)+(tamPal-1) < 7) {
					
					int cont = 0;
					for(int i=id;i<=id+(tamPal-1);i++,cont++) {
						botoes.elementAt(i).setText(Character.toString(palavras.elementAt(idPalavra).charAt(cont)));
						//botoes.elementAt(i).setForeground(cores[2]); // TIRAR DEPOIS
						posicoesResp.add(i);
						System.out.println(i);
					}
					
					break;
				}
				
			}
			
		}
		
		
	}
	
	@Override
	String oqTemnoBotao(int i) {
		
		return null;
	}

	@Override
	void clicouBotao(int i) {
		botoes.elementAt(i).setForeground(cores[4]);

	}

}
