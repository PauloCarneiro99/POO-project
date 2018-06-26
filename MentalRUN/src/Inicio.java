import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Inicio {

	public static Cliente cliente;
	private JFrame janelaInicio;
	private static BaseJogos jogo;
	private static String nome = "", oponente = "";
	private static Vector<String> jogosNomes = new Vector<String>();
	private static Vector<Boolean> jogosJogados = new Vector<Boolean>();
	private static Random r;

	public static String getNome() {
		return nome;
	}

	public static String getOponente() {
		return oponente;
	}
	
	public static void main(String[] args) {
		try {
			new Inicio();
			//System.out.println("\n\nNAO JOGA AQUI PORRA");
		} catch (Exception e) {}
	}
	
	public Inicio(Cliente cliente){
		Inicio.cliente = cliente;
		try {
			new Inicio();
		} catch (Exception e) {}
		Inicio.cliente.escreveID(isDupla(), jogosNomes.size(), nome, oponente);
	}
	
	public Inicio(){
		r = new Random();

		jogosNomes.add("Caça Palavras");
		jogosNomes.add("Encontre o Par");
		jogosNomes.add("Encontre o Único");
		jogosNomes.add("Olhos de Águia");
		jogosNomes.add("Ordem Crescente");
		jogosNomes.add("Qual Tem Mais");
		jogosNomes.add("Sequência Númerica");
		jogosNomes.add("Todos Iguais");
		
		for(int i=0;i<jogosNomes.size();i++){
			jogosJogados.add(false);
		}
		
		Image img = new ImageIcon(this.getClass().getResource("/Inicio.jpg")).getImage();
		Image icone = new ImageIcon(this.getClass().getResource("/mental.png")).getImage();
		janelaInicio = new JFrame("MentalRUN");
		janelaInicio.setIconImage(icone);
		janelaInicio.setVisible(true);
		janelaInicio.setBackground(Color.WHITE);
		janelaInicio.setSize(img.getWidth(null), img.getHeight(null));//define o tamanho da janela com o tamanho da imagem
		janelaInicio.setLocation(
				((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-img.getWidth(null))/2,
				((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-img.getHeight(null))/2);//define a posicao da janela no centro da tela
		janelaInicio.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		janelaInicio.getContentPane().setLayout(null);
		
		JLabel imgFundo = new JLabel();//cria um label para o fundo
		imgFundo.setIcon(new ImageIcon(img));//coloca a imagem nesse label
		imgFundo.setBounds(0, 0, img.getWidth(null), img.getHeight(null));//define o tamanho do label com o tamanho da imagem
		janelaInicio.getContentPane().add(imgFundo);//adiciona o label da imagem de fundo na janela
		
		JLabel lblJogar = new JLabel();
		lblJogar.addMouseListener(new MouseAdapter() {//adiciona um listener no botao jogar
			@Override
			public void mouseClicked(MouseEvent e) {
				Jogar();//chama uma funcao pra fazer alguma coisa quando clica em jogar
			}
		});
		lblJogar.setBounds(28, 148, 210, 107);//pixel zoomer
		janelaInicio.getContentPane().add(lblJogar);//adiciona o label jogar na janela
		
		JLabel lblInstrucoes = new JLabel();//mesma coisa que lblJogar
		lblInstrucoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Instrucoes();
			}
		});
		lblInstrucoes.setBounds(262, 148, 210, 107);
		janelaInicio.getContentPane().add(lblInstrucoes);

		JLabel lblCreditos = new JLabel();//mesma coisa que lblJogar
		lblCreditos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Creditos();
			}
		});
		lblCreditos.setBounds(28, 280, 444, 41);
		janelaInicio.getContentPane().add(lblCreditos);
		ID();
		
	}

	private void Jogar(){
		if(cliente == null){
			janelaInicio.dispatchEvent(new WindowEvent(janelaInicio, WindowEvent.WINDOW_CLOSING));
			proximoJogoSemCliente();
		}
		else{
			if(isDupla() && cliente.isEsperandoOp()){
				JOptionPane.showMessageDialog(null, "Esperando "+oponente+" se conectar");
			}
			else{
				//janelaInicio.dispatchEvent(new WindowEvent(janelaInicio, WindowEvent.WINDOW_CLOSING));
				proximoJogo();
			}
		}
	}
	
	public static void proximoJogo(){
		if(!jogouTodos()){
			int jogo = r.getIntRandom(jogosNomes.size());
			while(jogosJogados.elementAt(jogo))
				jogo = r.getIntRandom(jogosNomes.size());
			if(jogo == 0)
				Inicio.jogo = new CacaPalavras();
			else if(jogo == 1)
				Inicio.jogo = new EncontrePar();
			else if(jogo == 2)
				Inicio.jogo = new EncontreUnico();
			else if(jogo == 3)
				Inicio.jogo = new OlhoDeAguia();
			else if(jogo == 4)
				Inicio.jogo = new OrdemCrescente();
			else if(jogo == 5)
				Inicio.jogo = new QualTemMais();
			else if(jogo == 6)
				Inicio.jogo = new SequenciaNumerica();
			else if(jogo == 7)
				Inicio.jogo = new TodosIguais();
			jogosJogados.setElementAt(true, jogo);
		}
	}
	
	public static void proximoJogoSemCliente(){
		if(!jogouTodos()){
			int jogo = r.getIntRandom(jogosNomes.size());
			while(jogosJogados.elementAt(jogo))
				jogo = r.getIntRandom(jogosNomes.size());
			if(jogo == 0)
				Inicio.jogo = new CacaPalavras();
			else if(jogo == 1)
				Inicio.jogo = new EncontrePar();
			else if(jogo == 2)
				Inicio.jogo = new EncontreUnico();
			else if(jogo == 3)
				Inicio.jogo = new OlhoDeAguia();
			else if(jogo == 4)
				Inicio.jogo = new OrdemCrescente();
			else if(jogo == 5)
				Inicio.jogo = new QualTemMais();
			else if(jogo == 6)
				Inicio.jogo = new SequenciaNumerica();
			else if(jogo == 7)
				Inicio.jogo = new TodosIguais();
			jogosJogados.setElementAt(true, jogo);
		}
	}
	
	private void Instrucoes(){
		String instrucoes = "INSTRUÇÕES:\n";
		instrucoes+= "Caça Palavras: Encontre a palavra desejada\n";
		instrucoes+= "Encontre o par: Encontre os dois unicos simbolos que são um par identido\n";
		instrucoes+= "Encontre o único: Encontre o único simbolo que é único\n";
		instrucoes+= "Olhos de Águia: Encontre o caractere diferente\n";
		instrucoes+= "Ordem Crescente: Clique nos números em ordem crescente\n";
		instrucoes+= "Qual tem mais: Clique em qualquer icone que é o mais frequente";
		instrucoes+= "Sequência Númerica: Clique nos números em ordem crescente\n";
		instrucoes+= "Todos Iguais: Clique no icone para muda-lo, deixe todos iguais\n";
		
		JOptionPane.showMessageDialog(null, instrucoes);
	}
	
	private void Creditos(){
		JOptionPane.showMessageDialog(null, "Desenvolvedores\n"+
				"9293668 Andre S. Junior\n"+
				"10284667 Fabio F. Destro\n"+
				"10284945 Gabriel B. Domingos\n"+
				"10295304 Paulo O. Carneiro\n"+
				"10284952 Vitor G. Torres\n");
	}
	
	public double tempoDecorrido(){
		if(jogo != null)
			return jogo.tempoDecorrido();
		return 0;
	}

	public static boolean jogouTodos(){
		for(int i = 0; i < jogosJogados.size(); i++)
			if(!jogosJogados.elementAt(i))
				return false;
		return true;
	}
	
	private void ID(){
		JTextArea textArea = new JTextArea();
		while(nome.length() == 0){
			textArea.setEditable(true);
			JScrollPane scrollPane = new JScrollPane(textArea);
			JOptionPane.showMessageDialog(null, scrollPane, "Qual seu nome?", JOptionPane.PLAIN_MESSAGE);
			nome = textArea.getText().trim().split("\n")[0];
			textArea.setText("");
		}
		if(cliente != null){
			Object simnao[] = {"Sozinho", "Em dupla"};
			int dupla = JOptionPane.showOptionDialog(null, "Você quer jogar sozinho ou em dupla?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, simnao, simnao[1]);
			if(dupla == 1){
				while(oponente.length() == 0){
					textArea.setEditable(true);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane, "Qual o nome do seu oponente?", JOptionPane.PLAIN_MESSAGE);
					oponente = textArea.getText().trim().split("\n")[0];
				}
			}
		}
	}
	
	public static boolean isDupla(){
		return !oponente.equals("");
	}
	
}
