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
		inicio = new Inicio(this);
		new ProgressoThreadCliente(this).start();
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
		System.out.println("Cli Env: "+"P;"+nomeJogo+";"+tempoDecorrido);
		out.println("P;"+nomeJogo+";"+tempoDecorrido);
		if(Inicio.jogouTodos()){
			while(in.hasNextLine()){
				JOptionPane.showMessageDialog(null, in.nextLine());
			}
			System.exit(0);
		}
	}

	public void escreveID(boolean Dupla, int qtd, String nome, String oponente) {
		int dupla = Dupla ? 1 : 0;
		if(Dupla){
			System.out.println("Cli Env: "+"I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			out.println("I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			String read = "";
			while(in.hasNextLine()){
				if(in.hasNext("D;.*")){
					esperandoOp = false;
					read = in.nextLine();
					System.out.println("Cli Leu: "+read);
					JOptionPane.showMessageDialog(null, read.substring(2, read.length()));
				}
				else{
					read = in.nextLine();
					System.out.println("Cli Leu: "+read);
					JOptionPane.showMessageDialog(null, read);
				}
			}
		}
		else{
			System.out.println("Cli Env: "+"I;"+dupla+";"+qtd+";"+nome);
			out.println("I;"+dupla+";"+qtd+";"+nome);
			if(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
			}
		}
	}

	public boolean isEsperandoOp() {
		return esperandoOp;
	}

}
