import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe do cliente para o servidor do jogo.
 * @see Server
 */
public class Cliente {

	private static Socket socket;
	private static Scanner in;
	private static PrintStream out;
	private static String nome = "", oponente = "", IPservidor = "";
	private static int rodada;
	private static Inicio jogo;

	/**
	 * Opens up connection with server,
	 * creates windows to display the game
	 * and writes command lines to it.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		while(true){
			try {
				//IPservidor = "192.168.182.167";
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
		in = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				out.println("DE");
				in.close();
				out.close();
			}
		}));
		jogo = new Inicio();
		rodada = 1;
		escreveID();
		while(rodada < 3){
			leJ();
		}
	}

	public void escreveP() {
		System.out.println("Cli Env: "+"P "+Cliente.jogo.getJogoID()+" "+Cliente.jogo.tempoDecorrido());
		out.println("P "+Cliente.jogo.getJogoID()+" "+Cliente.jogo.tempoDecorrido());
	}

	private static void leJ() {
		String read[] = in.nextLine().trim().split(" ");
		System.out.println("Cli Leu: "+Arrays.toString(read));
		if(read[0].equals("J")){
			try {
				int jogo = Integer.parseInt(read[1]);
				Cliente.jogo.setJogoID(jogo);
			} catch (Exception e) {}
		}
	}

	/**
	 * Opens up a window asking for user's name.
	 * Writes 'I' command and sends it to server.
	 */
	private static void escreveID() {
		JTextArea textArea = new JTextArea();
		while(nome.length() == 0){
			textArea.setEditable(true);
			JScrollPane scrollPane = new JScrollPane(textArea);
			JOptionPane.showMessageDialog(null, scrollPane, "Qual seu nome?", JOptionPane.PLAIN_MESSAGE);
			nome = textArea.getText().trim().split("\n")[0];
			textArea.setText("");
		}
		Object simnao[] = {"Sozinho", "Em dupla"};
		int Dupla = JOptionPane.showOptionDialog(null, "Você quer jogar sozinho ou em dupla?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, simnao, simnao[1]);
		if(Dupla == 1){
			while(oponente.length() == 0){
				textArea.setEditable(true);
				JScrollPane scrollPane = new JScrollPane(textArea);
				JOptionPane.showMessageDialog(null, scrollPane, "Qual o nome do seu oponente?", JOptionPane.PLAIN_MESSAGE);
				oponente = textArea.getText().trim().split("\n")[0];
			}
			System.out.println("Cli Env: "+"I "+Dupla+" "+nome+"\n"+oponente);
			out.println("I "+Dupla+" "+nome+"\n"+oponente);
			while(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
			}
		}
		else{
			System.out.println("Cli Env: "+"I "+Dupla+" "+nome);
			out.println("I "+Dupla+" "+nome);
			if(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
			}
		}
	}

}
