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
	private Inicio jogo;
	private boolean esperandoOp = true;

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
				//IPservidor = "192.168.182.219";//yoooo
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
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				out.println("DE");
				in.close();
				out.close();
			}
		}));
		jogo = new Inicio(this);
	}

	public void escreveP(String nomeJogo, double tempoDecorrido) {
		System.out.println("Cli Env: "+"P;"+nomeJogo+";"+tempoDecorrido);
		out.println("P;"+nomeJogo+";"+tempoDecorrido);
		//jogo.proximoJogo();
	}

	public void escreveID(boolean Dupla, int qtd, String nome, String oponente) {
		int dupla = Dupla ? 1 : 0;
		if(Dupla){
			System.out.println("Cli Env: "+"I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			out.println("I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			while(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
				if(read.equalsIgnoreCase(oponente + " conectado")) esperandoOp = false;
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
