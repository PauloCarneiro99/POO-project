import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Inicio {
	
	private JFrame janelaInicio;

	public static void main(String[] args) {
		try {
			new Inicio();
		} catch (Exception e) {}
	}
	
	public Inicio(){
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
		janelaInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	private void Jogar(){
		Object simnao[] = {"Sozinho", "Em dupla"};
		int op = JOptionPane.showOptionDialog(null, "Você quer jogar sozinho ou em dupla?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, simnao, simnao[1]);
		System.out.println(op);
		proximoJogo();
	}
	
	public void proximoJogo(){
		new Random().getIntRandom(5);
		new OrdemCrescente();
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
		instrucoes+= "Todos Diferentes: Troque os icones para que fiquem todos diferentes\n";
		
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
	
}
