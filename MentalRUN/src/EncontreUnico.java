import java.awt.Font;
import java.util.Vector;

public class EncontreUnico extends BaseJogos {
	private int rodadas;
	private int posUnico;
	private int nroSimbolos;
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};
	private int cor;
	private int simbolo;
	private Random r = null;
	private Vector<String> simbolosUsados;


	public EncontreUnico() {
		super("Encontre o Único", "Encontre o único simbolo que é único");
		r = new Random();
		simbolosUsados = new Vector<String>();
		rodadas = 0;
		nroSimbolos = 3;
		montaTabuleiro();
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getText();
	}

	@Override
	void clicouBotao(int i) { //acertei, gero um novo tabuleiro e aumento o numero de simbolos que serao gerados
		if(i == posUnico){
			rodadas++;
			nroSimbolos += 2;
			if(rodadas == 6){ //cheguei no maximo de rodadas, finalizo o jogo
				finaliza();
			}
			montaTabuleiro();

		}else{//errei o simbolo do unico, sofro a penalidade e gero um novo tabuleiro
			penalidade(3);
			montaTabuleiro();
		}

	}

	@Override
	void montaTabuleiro(){
		limpaTabuleiro();//incializando o tabuleiro
		int tmp = r.getIntRandom(42);//gerando a posicao que o elemento unico sera posicionado no tabuleiro

		cor = r.getIntRandom(5);//gerando a cor do elemento unico
		simbolo = r.getIntRandom(8); //gerando o simbolo do elemento unico
		posUnico = tmp; //salvando a posicao que o unico vai estar
		//escrevendo o botao no tabuleiro
		botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
		botoes.elementAt(tmp).setText(simb[simbolo]);
		botoes.elementAt(tmp).setForeground(cores[cor]);
		botoes.elementAt(tmp).setVisible(true);
		simbolosUsados.add(simb[simbolo]+cor);

		for(int i=0; i< nroSimbolos; i++){
			//inserindo os demas simbolos
			cor = r.getIntRandom(5);
			simbolo = r.getIntRandom(8);
			while(simbolosUsados.contains(simb[simbolo]+cor)){
				cor = r.getIntRandom(5);
				simbolo = r.getIntRandom(8);
			}
			simbolosUsados.add(simb[simbolo]+cor);
			int aux = r.getIntRandom(2,4);
			for(int j=0; j<aux; j++){
				tmp = r.getIntRandom(42);
				while(!oqTemnoBotao(tmp).equals("")){//se o conteudo do botao ja estiver ocupado, continua procurando uma posição vazia
					tmp = r.getIntRandom(42);
				}
				//inserindo no tabuleiro
				botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
				botoes.elementAt(tmp).setText(simb[simbolo]);
				botoes.elementAt(tmp).setForeground(cores[cor]);
				botoes.elementAt(tmp).setVisible(true);
			}
		}
		//System.out.println(simbolosUsados.toString());
	}

	@Override
	protected void limpaTabuleiro() {
		super.limpaTabuleiro();
		simbolosUsados.clear();
	}

}
