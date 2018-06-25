import java.awt.Font;
import java.util.HashSet;
import java.util.Vector;

public class CacaPalavras extends BaseJogos {

	private Random r = null;
	private Vector<String> palavras = new Vector<String>();
	private HashSet<Integer> posicoesResp  = new HashSet<Integer>();
	private HashSet<Integer> palavrasSorteadas = new HashSet<Integer>();
	private int rodadas = 0,marcadasCertas = 0,marcadasErradas = 0,tamPal;
	
	
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
		palavras.add("MENTAL");
		palavras.add("RUN");
		palavras.add("CAÇA");
		palavras.add("RAPIDO");
		
		r = new Random();//criando o rand
		montaTabuleiro();	
		
	}

	@Override
	void montaTabuleiro(){
		marcadasCertas = 0;
		marcadasErradas = 0;
		for(int i = 0; i < 42; i++){
			Character c = (char) (r.getIntRandom(26) + 'A');
			botoes.elementAt(i).setText(c.toString());//coloco no botao o numero
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(4)]);
			botoes.elementAt(i).setVisible(true);
			botoes.elementAt(i).setFont(new Font("Arial", Font.PLAIN, 20));
		}
		
		int idPalavra = 0;
		while(true) {
			int idAux = r.getIntRandom(palavras.size()); // sorteia a palavra
			if(!palavrasSorteadas.contains(idAux)) {
				palavrasSorteadas.add(idAux);
				idPalavra = idAux;
				break;
			}
		}
		
		lblTituloJogo.setText("Caça Palavras:   ("+ palavras.elementAt(idPalavra)+")");
		
		int vertical = r.getIntRandom(2); // sorteia se vai ficar na vertical ou horizontal
		int inverso = r.getIntRandom(2); // sorteia se vai ficar escrito de tras para frente
		
		
		tamPal = palavras.elementAt(idPalavra).length();
		while(true) {
			int id = r.getIntRandom(42);	
			System.out.println(palavras.elementAt(idPalavra) + "\n" + id);
			
			
			if(vertical == 1) { // vai ficar na vertical
				
				System.out.println("id:"+   Integer.toString(((int)(id/6))-(tamPal-1))   );
				if(inverso == 1 && ((int)(id/7))-(tamPal-1) >= 0) { // da para ser escrito de traz para frente 
					for(int i=id,cont = 0;cont<tamPal;i-=7,cont++) {
						botoes.elementAt(i).setText(Character.toString(palavras.elementAt(idPalavra).charAt(cont)));
						//botoes.elementAt(i).setForeground(cores[2]); // TIRAR DEPOIS
						posicoesResp.add(i);
						System.out.println(i);
					}
					break;
				}else if(inverso == 0 && ((int)(id/7))+(tamPal-1) < 6) { // da pra escrever	
					for(int i=id,cont = 0;cont<tamPal;i+=7,cont++) {
						botoes.elementAt(i).setText(Character.toString(palavras.elementAt(idPalavra).charAt(cont)));
						//botoes.elementAt(i).setForeground(cores[2]); // TIRAR DEPOIS
						posicoesResp.add(i);
						System.out.println(i);
					}
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
				}else if(inverso == 0 && (id%7)+(tamPal-1) < 7) {
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
		
		if(botoes.elementAt(i).getForeground() == cores[4]) { // já está clicado
			if(posicoesResp.contains(i)) {
				marcadasCertas--;
			}else {
				marcadasErradas--;
			}
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(4)]);
			System.out.println("Certas" + marcadasCertas + " erradas" + marcadasErradas);
			if(marcadasCertas == tamPal && marcadasErradas == 0) {
				rodadas++;
				if(rodadas == 6) {
					finaliza();
				}
				montaTabuleiro();
			}
		}else {
			botoes.elementAt(i).setForeground(cores[4]);
			if(posicoesResp.contains(i)) {
				marcadasCertas++;
			}else {
				marcadasErradas++;
			}
			System.out.println("Certas" + marcadasCertas + " erradas" + marcadasErradas);
			if(marcadasCertas == tamPal && marcadasErradas == 0) {
				rodadas++;
				if(rodadas == 6) {
					finaliza();
				}
				montaTabuleiro();
			}
		}
		
		
	}

}
