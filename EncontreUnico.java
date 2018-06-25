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
		super("", "");
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
	void clicouBotao(int i) {
		if(i == posUnico){
			rodadas++;
			nroSimbolos++;
			if(rodadas == 6){
				finaliza();
			}
			montaTabuleiro();
			
		}else{
			penalidade(3);
			limpaTabuleiro();
			montaTabuleiro();
		}

	}

	@Override
	void montaTabuleiro() {
		limpaTabuleiro();
		int pos;
		int tmp = r.getIntRandom(42);
		
		cor = r.getIntRandom(4);
		simbolo = r.getIntRandom(8);
		posUnico = tmp;
		botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
		botoes.elementAt(tmp).setText(simb[simbolo]);
		botoes.elementAt(tmp).setForeground(cores[cor]);
		botoes.elementAt(tmp).setVisible(true);
		
		pos = cor*MaxColuna + simbolo;
		
		inserido[pos] = true;
		for(int i=0; i< nroSimbolos; i++){
			int aux = r.getIntRandom(2)+2;
			cor = r.getIntRandom(4);
			simbolo = r.getIntRandom(8);
			pos = cor*MaxColuna + simbolo;
			while(inserido[pos] == true){
				cor = r.getIntRandom(4);
				simbolo = r.getIntRandom(8);
				pos = cor*MaxColuna + simbolo;
			}
			inserido[pos] = true;
			for(int j=0; j<aux; j++){
				tmp = r.getIntRandom(42);
				while(!oqTemnoBotao(tmp).equals("")){//se o conteudo do botao ja estiver ocupado, continua procurando uma posição vazia
					tmp = r.getIntRandom(42);
				}
				botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 30));
				botoes.elementAt(tmp).setText(simb[simbolo]);
				botoes.elementAt(tmp).setForeground(cores[cor]);
				botoes.elementAt(tmp).setVisible(true);
			}
		}
		

	}
	
	private void limpaTabuleiro(){
		for(int i=0;i <32; i++){
			inserido[i] = false;
		}
		
		for(int i = 0; i < 42; i++){
			System.out.printf(botoes.elementAt(i).getText());
			botoes.elementAt(i).setText("");
			botoes.elementAt(i).setVisible(false);
		}
	}

}
