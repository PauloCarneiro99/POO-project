import java.awt.Font;

public class TodosIguais extends BaseJogos {
	
	//https://www.facebook.com/notes/obx-creative/ascii-symbols-to-use-on-facebook-have-fun-/10150244846134770/

	private Random r = null;
	private int cor1, cor2;

	public TodosIguais() {
		super("Todos Iguais", "Troque os icones para que fiquem todos iguais");
		r = new Random();//criando o rand
		
		for(int j = 5; j < 12; j+=2){
			cor1 = cor2 = r.getIntRandom(4);
			while(cor1 == cor2)
				cor2 = r.getIntRandom(4);
			for(int i = 0; i < j; i++){
				int tmp = r.getIntRandom(42);
				botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 25));
				botoes.elementAt(tmp).setText("●");
				botoes.elementAt(tmp).setForeground(cores[cor1]);
				botoes.elementAt(tmp).setVisible(true);
				tmp = r.getIntRandom(42);
				botoes.elementAt(tmp).setFont(new Font("Arial", Font.PLAIN, 27));
				botoes.elementAt(tmp).setText("◆");
				botoes.elementAt(tmp).setForeground(cores[cor2]);
				botoes.elementAt(tmp).setVisible(true);
			}
		}
	}

	@Override
	String oqTemnoBotao(int i) {
		return botoes.elementAt(i).getIcon().toString();
	}

	@Override
	void clicouBotao(int i) {
		System.out.println(botoes.elementAt(i-1).getText());
	}

}
