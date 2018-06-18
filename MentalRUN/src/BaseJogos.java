import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

abstract class BaseJogos {
	
	private JFrame janelaBaseJogos;
	protected Vector<JButton> botoes;
	protected String nome = "", comoJoga = "";
	private long tempoComeco = 0, tempoFim = 0;
	private boolean jogando = false;
	private Image img = null;
	protected static int segundos;
	protected Color cores[] = new Color[4];
	
	public BaseJogos(String nome, String comoJoga){
		this.nome = nome;
		this.comoJoga = comoJoga;
		img = new ImageIcon(this.getClass().getResource("/"+this.nome+".png")).getImage();
		janelaBaseJogos = new JFrame(this.nome);
		janelaBaseJogos.setVisible(true);
		janelaBaseJogos.setSize(img.getWidth(null), img.getHeight(null)+30);//define o tamanho da janela com o tamanho da imagem
		janelaBaseJogos.setLocation(
				((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-img.getWidth(null))/2,
				((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-img.getHeight(null))/2);//define a posicao da janela no centro da tela
		janelaBaseJogos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaBaseJogos.getContentPane().setLayout(null);
		janelaBaseJogos.getContentPane().setBackground(Color.WHITE);
		
		JLabel imgFundo = new JLabel();//cria um label para o fundo
		imgFundo.setIcon(new ImageIcon(img));//coloca a imagem nesse label
		imgFundo.setBounds(0, 0, img.getWidth(null), img.getHeight(null));//define o tamanho do label com o tamanho da imagem
		janelaBaseJogos.getContentPane().add(imgFundo);//adiciona o label da imagem de fundo na janela
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(28, 163, 444, 379);
		pnlBotoes.setLayout(new GridLayout(6, 7));
		pnlBotoes.setBackground(Color.WHITE);
		janelaBaseJogos.add(pnlBotoes);
		
		botoes = new Vector<JButton>();
		for(int i = 1; i <= 42; i++){
			botoes.addElement(new JButton());//cria botoes e vai colocando eles em um vetor
			botoes.elementAt(i-1).setActionCommand(""+i);
		}
		
		for(JButton botao : botoes){//pra cada botao nos botoes
			botao.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clicouBotao(Integer.parseInt(e.getActionCommand()));
				}
			});
			botao.setOpaque(true);
			botao.setContentAreaFilled(false);
			botao.setBorderPainted(false);
			pnlBotoes.add(botao);//adiciona ele no painel de botoes
		}
		
		JLabel lblInstrucoes = new JLabel();//mesma coisa que lblJogar
		lblInstrucoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				instrucoes();
			}
		});
		lblInstrucoes.setBounds(440, 100, 30, 30);
		janelaBaseJogos.getContentPane().add(lblInstrucoes);
		
		cores[0] = new Color(38, 38, 38);//preto
		cores[1] = new Color(40, 154, 243);//azul
		cores[2] = new Color(244, 67, 55);//vermelho
		cores[3] = new Color(76, 175, 80);//verde
		
		comeca();//dispara o cronometro
		
	}
	
	/**
	 * Retorna o conteudo do i-ésimo botao
	 * @param i Indice do botao
	 * @return Seu conteudo
	 */
	abstract String oqTemnoBotao(int i);
	
	/**
	 * Realiza ações diferentes para cada jogo quando se clica em um botao
	 * @param i Indice do botao clicado
	 */
	abstract void clicouBotao(int i);
	
	/**
	 * Mostra na tela uma janela com as instruções de cada jogo
	 */
	private void instrucoes(){
		JOptionPane.showMessageDialog(null, comoJoga, nome, JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Dispara o cronometro
	 */
	private void comeca(){
		this.tempoComeco = System.nanoTime()/1000000;
		this.jogando = true;
	}
	
	/**
	 * Para o cronometro
	 */
	protected void finaliza(){
		this.tempoFim = System.nanoTime()/1000000;
		this.jogando = false;
		
		//mostra o tempo separando em minutos e segundos e fecha a janela
		JOptionPane.showMessageDialog(null, "Seu tempo foi: "+(tempoDecorrido() > 60 ? (int)((tempoDecorrido() - (tempoDecorrido() % 60))/60)+"m " : "")+(int)(tempoDecorrido()%60)+"s", "Seu tempo", JOptionPane.PLAIN_MESSAGE);
		janelaBaseJogos.dispatchEvent(new WindowEvent(janelaBaseJogos, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	 * Retorna o tempo decorrido de jogo
	 * @return Tempo decorrido de jogo
	 */
	protected double tempoDecorrido(){
		if(jogando)
			return ((double)(System.nanoTime()/1000000 - tempoComeco))/1000;
		else
			return ((double)(tempoFim - tempoComeco))/1000;
	}
	
	/**
	 * Abre uma janela do tamanho da inferior sobre a janela do jogo e impede a jogada.
	 * @param segundos O tempo que a janela devera dicar aberta
	 */
	public void penalidade(final int segundos){
		BaseJogos.segundos = segundos;
		//cria um painel de mensagens "new Object[]{}, null" é pra que ele fique sem botoes
		final JOptionPane optionPane = new JOptionPane("Penalidade: "+segundos+" segundos", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

		optionPane.setBackground(Color.WHITE);
		final JDialog dialog = new JDialog();//cria a janela
		dialog.setTitle("Penalidade");
		dialog.setContentPane(optionPane);//coloca o painel de mensagens dentro dessa janela
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);//destiva o botao de fechar janela
		dialog.setLocation(
				((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-img.getWidth(null))/2,
				((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-img.getHeight(null))/2);
		dialog.setSize(img.getWidth(null), img.getHeight(null)+30);
		dialog.setResizable(false);
		dialog.setAlwaysOnTop(true);
		
		final Timer timer = new Timer(0, new ActionListener() {//cria um timer com delay inicial 0
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("yo");
				if(BaseJogos.segundos > 0){//altera o q ta escrito no painel de mensagens
					optionPane.setMessage("Penalidade: "+BaseJogos.segundos+" segundo"+(BaseJogos.segundos!=1?"s":""));
					BaseJogos.segundos--;
				}
				else{
					dialog.dispose();//fecha a janela
				}
			}
		});
		Thread close = new Thread(){//cria uma thread pra desligar o timer depois que ele acabar
			@Override
			public void run() {
				try {
					Thread.sleep(BaseJogos.segundos*1000+500);
				} catch (InterruptedException e) {}
				timer.stop();//desliga o timer
			}
		};
		timer.setRepeats(true);
		timer.setDelay(1000);//define a frequencia do timer pra 1 segundo
		close.start();
		timer.start();
		dialog.setVisible(true);
	}

}
