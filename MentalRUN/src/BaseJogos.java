import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

abstract class BaseJogos {

	private JPanel pnlBotoes, pnlPenalidade;
	public JProgressBar pb1, pb2;
	private JLabel lblPenalidade;
	protected JLabel lblTituloJogo;
	private JFrame janelaBaseJogos;
	protected Vector<JButton> botoes;
	protected String nomeJogo = "", comoJoga = "";
	private long tempoComeco = 0, tempoFim = 0;
	private boolean jogando = false;
	private Image img = null;
	protected static int segundos;
	protected Color cores[] = new Color[5];

	public BaseJogos(String nome, String comoJoga){
		//new ProgressoThread(Inicio.cliente).start();
		Font nexaL = null;
		Font nexaB = null;
		try{
			nexaL = Font.createFont(Font.TRUETYPE_FONT, new File("fontes/nexaLight.otf")).deriveFont(18f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fontes/nexaLight.otf")));

			nexaB = Font.createFont(Font.TRUETYPE_FONT, new File("fontes/nexaBold.otf")).deriveFont(18f);
			GraphicsEnvironment gee = GraphicsEnvironment.getLocalGraphicsEnvironment();
			gee.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fontes/nexaBold.otf")));
		}catch(Exception e){}


		this.nomeJogo = nome;
		this.comoJoga = comoJoga;
		//img = new ImageIcon(this.getClass().getResource("/"+this.nome+".png")).getImage();
		img = new ImageIcon(this.getClass().getResource("/fundoPadrao.png")).getImage();

		janelaBaseJogos = new JFrame(this.nomeJogo);
		Image icone = new ImageIcon(this.getClass().getResource("/mental.png")).getImage();
		janelaBaseJogos.setIconImage(icone);
		janelaBaseJogos.setVisible(true);
		janelaBaseJogos.setSize(img.getWidth(null), img.getHeight(null)+30);//define o tamanho da janela com o tamanho da imagem
		janelaBaseJogos.setLocation(
				((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-img.getWidth(null))/2,
				((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-img.getHeight(null))/2);//define a posicao da janela no centro da tela
		janelaBaseJogos.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		janelaBaseJogos.getContentPane().setLayout(null);
		janelaBaseJogos.getContentPane().setBackground(Color.WHITE);


		JLabel lblNome1 = new JLabel();
		lblNome1.setText(Inicio.getNome().toUpperCase());
		lblNome1.setFont(nexaL);
		lblNome1.setBounds(40, 20,170,20);
		janelaBaseJogos.add(lblNome1);

		pb1 = new JProgressBar();
		pb1.setBounds(230, 12, 140, 26);
		pb1.setMinimum(0);
		pb1.setMaximum(100);

		pb1.setValue(0);

		//janelaBaseJogos.add(pb1);
		if(Inicio.isDupla()){
			JLabel lblNome2 = new JLabel();
			lblNome2.setText(Inicio.getOponente().toUpperCase());
			lblNome2.setFont(nexaL);
			lblNome2.setBounds(40, 55,170,20);
			janelaBaseJogos.add(lblNome2);

			pb2 = new JProgressBar();
			pb2.setBounds(230, 50, 140, 26);
			pb2.setMinimum(0);
			pb2.setMaximum(100);

			pb2.setValue(0);

			//janelaBaseJogos.add(pb2);
		}


		lblTituloJogo = new JLabel();
		lblTituloJogo.setText(nome.toUpperCase());
		lblTituloJogo.setFont(nexaB);
		lblTituloJogo.setBounds(40, 108,360,20);
		janelaBaseJogos.add(lblTituloJogo);


		pnlBotoes = new JPanel();
		pnlBotoes.setBounds(28, 163, 444, 379);
		pnlBotoes.setLayout(new GridLayout(6, 7));
		pnlBotoes.setBackground(Color.WHITE);

		JLabel imgFundoPenalidade = new JLabel();
		Image imgPenal = new ImageIcon(this.getClass().getResource("penalidade.png")).getImage();
		imgFundoPenalidade.setIcon(new ImageIcon(imgPenal));

		pnlPenalidade = new JPanel();

		pnlPenalidade.setBounds(28, 163, 444, 379);
		pnlPenalidade.setBorder(BorderFactory.createLineBorder(new Color(204,204,204)));
		pnlPenalidade.setBackground(new Color(238,238,238));

		pnlPenalidade.setVisible(false);

		lblPenalidade = new JLabel();
		lblPenalidade.setBorder(new EmptyBorder(20, 0, 0, 0));
		lblPenalidade.setFont(nexaL);


		pnlPenalidade.add(lblPenalidade);
		pnlPenalidade.add(imgFundoPenalidade);

		janelaBaseJogos.add(pnlPenalidade);
		janelaBaseJogos.add(pnlBotoes);

		// COLOQUEI NO FINAL PARA FICAR ATRAS
		JLabel imgFundo = new JLabel();//cria um label para o fundo
		imgFundo.setIcon(new ImageIcon(img));//coloca a imagem nesse label
		imgFundo.setBounds(0, 0, img.getWidth(null), img.getHeight(null));//define o tamanho do label com o tamanho da imagem
		janelaBaseJogos.getContentPane().add(imgFundo);//adiciona o label da imagem de fundo na janela


		//janelaBaseJogos.add(pnlPenalidade);

		botoes = new Vector<JButton>();
		for(int i = 0; i < 42; i++){
			botoes.addElement(new JButton());//cria botoes e vai colocando eles em um vetor
			botoes.elementAt(i).setActionCommand(""+i);
			botoes.elementAt(i).setVisible(false);
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
		cores[1] = new Color(23, 104, 176);//azul
		cores[2] = new Color(238,159, 21); // laranja
		cores[3] = new Color(76, 175, 80);//verde
		cores[4] = new Color(230, 38, 25);//vermelho

		comeca();//dispara o cronometro

	}

	/**
	 * Retorna o conteudo do i-esimo botao
	 * @param i Indice do botao
	 * @return Seu conteudo
	 */
	abstract String oqTemnoBotao(int i);

	/**
	 * Realiza açoes diferentes para cada jogo quando se clica em um botao
	 * @param i Indice do botao clicado
	 */
	abstract void clicouBotao(int i);

	/**
	 * Especifica o que será colocado nos botoes to tabuleiro
	 */
	abstract void montaTabuleiro();

	/**
	 * Mostra na tela uma janela com as instrucoes de cada jogo
	 */
	private void instrucoes(){
		JOptionPane.showMessageDialog(null, comoJoga, nomeJogo, JOptionPane.PLAIN_MESSAGE);
	}

	protected void limpaTabuleiro(){
		for(JButton botao : botoes){
			botao.setText("");
			botao.setVisible(false);
		}
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
		//JOptionPane.showMessageDialog(null, "Seu tempo foi: "+(tempoDecorrido() > 60 ? (int)((tempoDecorrido() - (tempoDecorrido() % 60))/60)+"m " : "")+(int)(tempoDecorrido()%60)+"s", "Seu tempo", JOptionPane.PLAIN_MESSAGE);
		janelaBaseJogos.dispatchEvent(new WindowEvent(janelaBaseJogos, WindowEvent.WINDOW_CLOSING));
		if(Inicio.cliente != null){
			Inicio.cliente.escreveP(nomeJogo, tempoDecorrido());
			Inicio.proximoJogo();
		}else
			Inicio.proximoJogoSemCliente();

	}

	/**
	 * Retorna o tempo decorrido de jogo
	 * @return Tempo decorrido de jogo
	 */
	public double tempoDecorrido(){
		if(jogando)
			return ((double)(System.nanoTime()/1000000 - tempoComeco))/1000;
		else
			return ((double)(tempoFim - tempoComeco))/1000;
	}

	/**
	 * Cria um painel, em cima dos botões, para indicar a penalidade
	 * @param segundos O tempo que a janela devera dicar aberta
	 */
	protected void penalidade(final int segundos){
		BaseJogos.segundos = segundos;

		pnlBotoes.setVisible(false);
		pnlPenalidade.setVisible(true);

		final Timer timer = new Timer(0, new ActionListener() {//cria um timer com delay inicial 0
			@Override
			public void actionPerformed(ActionEvent e) {
				if(BaseJogos.segundos > 0){//altera o q ta escrito no painel de mensagens
					lblPenalidade.setText("PENALIDADE: "+BaseJogos.segundos+" SEGUNDO"+(BaseJogos.segundos!=1?"S":""));
					BaseJogos.segundos--;
				}
				else{
					pnlPenalidade.setVisible(false);
					pnlBotoes.setVisible(true);
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
	}

}
