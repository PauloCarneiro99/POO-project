import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe do cliente para o servidor do jogo.
 * @see Server
 */
public class Cliente {

	private Socket socket;
	public Scanner in;
	public PrintStream out;
	private String IPservidor = "";
	private boolean esperandoOp = true;
	public Inicio inicio;

	/**
	 * Opens up connection with server,
	 * creates windows to display the game
	 * and writes command lines to it.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new Cliente();
	}

	public Cliente(){
		while(true){
			try {
				IPservidor = "192.168.182.214";
				JTextArea textArea = new JTextArea();
				while(IPservidor.length() == 0){
					textArea.setEditable(true);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane, "Digite o IP do servidor", JOptionPane.PLAIN_MESSAGE);
					IPservidor = textArea.getText().trim().split("\n")[0];
				}
				socket = new Socket(IPservidor, Servidor.port);
				break;
			} catch (Exception e) {
				IPservidor = "";
				JOptionPane.showMessageDialog(null, "Falha ao conectar-se.\nTente novamente");
			}
		}
		System.out.println("Você está conectado");
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
		} catch (Exception e) {}
		new Thread(){
			@Override
			public void run() {
				while(hasNext()){
					if(hasNext("POR2")){
						nextLine();
						Inicio.increasePorcentagem2();
					}
					try {Thread.sleep(500);} catch (Exception e) {}
				}
			}
		}.start();
		inicio = new Inicio(this);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Finalizando o programa");
				out.println("DE");
				in.close();
				out.close();
			}
		}));
	}

	public void escreveP(String nomeJogo, double tempoDecorrido) {
		out.println("P;"+nomeJogo+";"+tempoDecorrido);
		if(Inicio.jogouTodos()){
			if(hasNext())
				while(hasNext("S;.*")){
					String read = nextLine();
					JOptionPane.showMessageDialog(null, read.substring(2, read.length()));
				}
			System.exit(0);
		}
	}

	public void escreveID(boolean Dupla, int qtd, String nome, String oponente) {
		int dupla = Dupla ? 1 : 0;
		if(Dupla){
			out.println("I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			String read = "";
			while(hasNext()){
				if(hasNext("D;.*")){
					esperandoOp = false;
					read = nextLine();
					JOptionPane.showMessageDialog(null, read.substring(2, read.length()));
				}
				else if(hasNext("S;.*")){
					read = nextLine();
					JOptionPane.showMessageDialog(null, read.substring(2, read.length()));
				}
			}
		}
		else{
			out.println("I;"+dupla+";"+qtd+";"+nome);
			if(hasNext("S;.*")){
				String read = nextLine();
				JOptionPane.showMessageDialog(null, read.substring(2, read.length()));
			}
		}
	}
	
	public synchronized String nextLine(){
		return in.nextLine();
	}
	
	public synchronized boolean hasNext(){
		return in.hasNext();
	}
	
	public synchronized boolean hasNext(String pattern){
		return in.hasNext(pattern);
	}

	public boolean isEsperandoOp() {
		return esperandoOp;
	}

}
