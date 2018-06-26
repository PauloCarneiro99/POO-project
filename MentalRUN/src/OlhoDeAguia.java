import java.awt.Font;
import java.util.Vector;

public class OlhoDeAguia extends BaseJogos {

	//private Vector<Character> letrasA, letrasB;
	private Vector<Character> letrasA = new Vector<Character>();
	private Vector<Character> letrasB = new Vector<Character>();
	
	private Random r = null;
	private Character letraMais,letraUnica;	
	private Integer rodadas = 0,tamanhoLetra = 20;
	
	
	public OlhoDeAguia() {
		super("Olhos de Águia", "Encontre o caractere diferente");
		// TODO Auto-generated constructor stub
		
		
		letrasA.add('G');
		letrasB.add('C');
		letrasA.add('Q');
		letrasB.add('O');
		letrasA.add('S');
		letrasB.add('$');
		letrasA.add('I');
		letrasB.add('1');
		letrasA.add('8');
		letrasB.add('B');
		letrasA.add('7');
		letrasB.add('1');
		
		
		r = new Random();//criando o rand
		montaTabuleiro();
		
	}
	
	@Override
	void montaTabuleiro(){
		
		int aOuB = r.getIntRandom(2); // sorteia se a letra unica vem do vetor A ou B
		int idLetra = r.getIntRandom(letrasA.size());
			
		if(aOuB == 0) {
			letraMais = letrasA.elementAt(idLetra);
			letraUnica = letrasB.elementAt(idLetra);
		}else {
			letraMais = letrasB.elementAt(idLetra);
			letraUnica = letrasA.elementAt(idLetra);
		}
		
		
		for(int i = 0; i < 42; i++){
			botoes.elementAt(i).setText(letraMais.toString());//coloco no botao o numero
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(4)]);
			botoes.elementAt(i).setVisible(true);
			botoes.elementAt(i).setFont(new Font("Arial", Font.PLAIN, tamanhoLetra));
		}
		
		int idDiferente = r.getIntRandom(42);
		botoes.elementAt(idDiferente).setText(letraUnica.toString());
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) {
		if(oqTemnoBotao(i).equals(letraUnica.toString())) {
			rodadas++;
			if(rodadas == 8) {
				finaliza();
			}else {
				if(rodadas%2==1) // a cada duas rodadas, diminui o tamanho da letra
					tamanhoLetra--;
				montaTabuleiro();
			}
		}else {
			penalidade(3);
			montaTabuleiro();
		}
		

	}

}
