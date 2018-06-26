import java.awt.Font;

public class EncontreUnico extends BaseJogos {
	private int rodadas;
	private int posUnico;
	private int nroSimbolos;
	boolean inserido[] = new boolean[32];
	private String simb[] = {"●","◆","★","♥","✿","✖","▧","✻"};
	private int cor;
	private int simbolo;
	private int MaxColuna = 8;
	private Random r = null;
	
	
	public EncontreUnico() {
		super("Encontre o Único", "Encontre o único simbolo que é único");
		r = new Random();
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
			nroSimbolos++;
			if(rodadas == 6){ //cheguei no maximo de rodadas, finalizo o jogo
				finaliza();
			}
			montaTabuleiro();
			
		}else{//errei o simbolo do unico, sofro a penalidade e gero um novo tabuleiro
			penalidade(3);
			limpaTabuleiro();
			montaTabuleiro();
		}

	}

	@Override
	void montaTabuleiro(){
		limpaTabuleiro();//incializando o tabuleiro
		int pos;
		int tmp = r.getIntRandom(42);//gerando a posicao que o elemento unico sera posicionado no tabuleiro
		
		cor = r.getIntRandom(4);//gerando a cor do elemento unico
		simbolo = r.getIntRandom(8); //gerando o simbolo do elemento unico
		posUnico = tmp; //salvando a posicao que o unico vai estar
		//escrevendo o botao no tabuleiro
		botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
		botoes.elementAt(tmp).setText(simb[simbolo]);
		botoes.elementAt(tmp).setForeground(cores[cor]);
		botoes.elementAt(tmp).setVisible(true);
		
		//marcando que esse simbolo e essa cor ja foi inserido
		pos = cor*MaxColuna + simbolo;
		inserido[pos] = true;
		
		for(int i=0; i< nroSimbolos; i++){
			//inserindo os demas simbolos
			int aux = r.getIntRandom(2)+2;
			cor = r.getIntRandom(4);
			simbolo = r.getIntRandom(8);
			pos = cor*MaxColuna + simbolo;
			while(inserido[pos] == true){ //conferindo que estou inserindo simbolos diferentes dos que ja estao no tabuleiro
				cor = r.getIntRandom(4);
				simbolo = r.getIntRandom(8);
				pos = cor*MaxColuna + simbolo;
			}
			inserido[pos] = true; //marcando simbolo e cor como inseridos
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
		

	}

	@Override
	protected void limpaTabuleiro() {
		super.limpaTabuleiro();
		for(int i = 0; i < 32; i++){
			inserido[i] = false;
		}
	}

}
