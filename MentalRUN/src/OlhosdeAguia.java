import java.awt.Color;

public class OlhosdeAguia extends BaseJogos {
	
	private Random r = null;
	private boolean numeros[] = null;
	private int max = 43, errado = 0;
	Color cores[] = new Color[4];

	public OlhosdeAguia() {
		super("Olhos de Aguia", "Clique nos números em ordem crescente");//contrutor da superclasse, nome do jogo e como joga ele
		r = new Random();//criando o rand
		cores[0] = new Color(38, 38, 38);//preto
		cores[1] = new Color(40, 154, 243);//azul
		cores[2] = new Color(244, 67, 55);//vermelho
		cores[3] = new Color(76, 175, 80);//verde
		
		numeros = new boolean[max];//crio um vetor de booleanos pra saber se ja coloquei aquele numero na tela pra evitar numeros iguais
		for(int i = 0; i < max; i++)
			numeros[i] = false;
		
		for(int i = 0; i < 42; i++){
			int atual = r.getIntRandom(1, max);//em cada posicao da matrix gero um numero entre 1 e o maximo (linha 12)
			while(numeros[atual])//se ele ja tiver sido usado
				atual = r.getIntRandom(1, max);//gero outro
			numeros[atual] = true;//anoto no vetor que agora esse numero ja foi usado
			botoes.elementAt(i).setText(Integer.toString(atual));//coloco no botao o numero
			botoes.elementAt(i).setForeground(cores[r.getIntRandom(4)]);
		}
	}
	
	/**
	 * Percorre o vetor de booleanos do comeco e para assim que achar um true,
	 * se a posicao parada for igual à perguntada retorna verdadeiro
	 * @param i Posição que eu quero saber se é a primeira
	 * @return Verdadeiro se a posicao i é a primeira ocupada do vetor de booleanos
	 */
	private boolean ehPrimeiro(int i){
		int j = 0;
		for(j = 0; j < max && !numeros[j]; j++);
		return i == j;
	}
	
	/**
	 * Procura pelo primeiro numero ocupado no vetor de booleanos
	 * @return O primeiro numero ocupado
	 */
	private int ehPrimeiro(){
		int j = 0;
		for(j = 0; j < max && !numeros[j]; j++);
		if(j == max && !numeros[j-1])
			return -1;
		return j;
	}
	
	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i-1).getText();
	}

	@Override
	void clicouBotao(int i) {
		System.out.println(tempoDecorrido());
		try {
			if(ehPrimeiro(Integer.parseInt(oqTemnoBotao(i)))){
				numeros[Integer.parseInt(oqTemnoBotao(i))] = false;
				botoes.elementAt(i-1).setText("");
				botoes.elementAt(i-1).setVisible(false);
				errado = 0;
			}
			else{
				if(errado < 1)
					errado++;
				else{
					//JOptionPane.showMessageDialog(null, , "penalidade de tempo", JOptionPane.PLAIN_MESSAGE);
				}
			}
		} catch (Exception e) {}
		if(ehPrimeiro() == -1){
			finaliza();
		}
	}

}
