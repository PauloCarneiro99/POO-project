
public class ExemploNovoJogo extends BaseJogos {
	
	//https://www.facebook.com/notes/obx-creative/ascii-symbols-to-use-on-facebook-have-fun-/10150244846134770/

	private Random r = null;
	private int cor1, cor2;

	public ExemploNovoJogo() {
		super("Olhos de Aguia", "Joga Assim");
		r = new Random();//criando o rand
		cor1 = r.getIntRandom(4);
		cor2 = r.getIntRandom(4);
		for(int i = 0; i < 5; i++){
			int tmp = r.getIntRandom(42);
			botoes.elementAt(tmp).setText("●");
			botoes.elementAt(tmp).setForeground(cores[1]);
			tmp = r.getIntRandom(42);
			botoes.elementAt(tmp).setText("◆");
			botoes.elementAt(tmp).setForeground(cores[2]);
		}
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getIcon().toString();
	}

	@Override
	void clicouBotao(int i) {
	}

}
