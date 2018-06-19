import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Inicio {
	
	private JFrame janelaInicio;
	private int jogoID;
	private BaseJogos jogo;

	public static void main(String[] args) {
		try {
			new Inicio();
		} catch (Exception e) {}
	}
	
	public Inicio(){
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
		
	}

	public void setJogo(int jogo) {
		this.jogoID = jogo;
	}

	private void Jogar(){
		janelaInicio.dispatchEvent(new WindowEvent(janelaInicio, WindowEvent.WINDOW_CLOSING));
		proximoJogo();
	}
	
	public void proximoJogo(){
		jogoID = 2;
		if(jogoID == 0)
			jogo = new SequenciaNumerica();
		else if(jogoID == 1)
			jogo = new SequenciaNumerica();
		else if(jogoID == 2)
			jogo = new TodosIguais();
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

	public synchronized boolean isJogando() {
		if(jogo != null)
			return jogo.isJogando();
		return false;
	}
	
	public double tempoDecorrido(){
		if(jogo != null)
			return jogo.tempoDecorrido();
		return 0;
	}
	
}
