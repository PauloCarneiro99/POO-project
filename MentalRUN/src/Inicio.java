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
	private BaseJogos jogo;
	private static String nome = "", oponente = "";
	private static Vector<String> jogosNomes = new Vector<String>();
	private static Vector<Boolean> jogosJogados = new Vector<Boolean>();
	private static Random r;

	public static void main(String[] args) {
		try {
			//new Inicio();
			System.out.println("\n\nNAO JOGA AQUI PORRA");
		} catch (Exception e) {}
	}
	
	public Inicio(Cliente cliente){
		Inicio.cliente = cliente;
		try {
			new Inicio();
		} catch (Exception e) {}
		Inicio.cliente.escreveID(!oponente.equals(""), jogosNomes.size(), nome, oponente);
	}
	
	public Inicio(){
		r = new Random();
		
		jogosNomes.add("Olhos de Aguia");
		jogosNomes.add("Sequencia Numerica");
		jogosNomes.add("Todos Iguais");
		
		for(int i=0;i<jogosNomes.size();i++){
			jogosJogados.add(false);
		}
		
		Image img = new ImageIcon(this.getClass().getResource("/Inicio.jpg")).getImage();
		janelaInicio = new JFrame("MentalRUN");
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

	public String getJogoID() {
		return this.jogo.nomeJogo;
	}

	private void Jogar(){
		janelaInicio.dispatchEvent(new WindowEvent(janelaInicio, WindowEvent.WINDOW_CLOSING));
		proximoJogo();
	}
	
	public void proximoJogo(){
		if(!jogouTodos()){
			int jogo = r.getIntRandom(jogosNomes.size());
			while(jogosJogados.elementAt(jogo))
				jogo = r.getIntRandom(jogosNomes.size());
			if(jogo == 0)
				this.jogo = new TodosIguais();
			else if(jogo == 1)
				this.jogo = new SequenciaNumerica();
			else if(jogo == 2)
				this.jogo = new TodosIguais();
			jogosJogados.setElementAt(true, jogo);
		}
	}
	
	private void Instrucoes(){
		JOptionPane.showMessageDialog(null, "Instruções\n");
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

	private boolean jogouTodos(){
		System.out.println(jogosJogados);
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
