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
public class Client {

	private static Socket socket;
	private static Scanner in;
	private static PrintStream out;
	private static String nome = "";
	private static int rodada, tempoTotal;
	private static Inicio jogo;

	/**
	 * Opens up connection with server,
	 * creates windows to display the game
	 * and writes command lines to it.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		socket = new Socket("127.0.0.1", Servidor.port);
		System.out.println("Você está conectado");
		in = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
		new Inicio();
		rodada = 1;
		tempoTotal = 0;
		writeI();
	}

	/**
	 * Opens up a window asking for user's name.
	 * Writes 'I' command and sends it to server.
	 */
	private static void writeI() {
		JTextArea textArea = new JTextArea();
		while(nome.length() == 0){
			textArea.setEditable(true);
			JScrollPane scrollPane = new JScrollPane(textArea);
			JOptionPane.showMessageDialog(null, scrollPane, "Qual seu nome?", JOptionPane.PLAIN_MESSAGE);
			nome = textArea.getText().trim().split("\n")[0];
		}
		Object simnao[] = {"Sozinho", "Em dupla"};
		int Dupla = JOptionPane.showOptionDialog(null, "Você quer jogar sozinho ou em dupla?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, simnao, simnao[1]);
		out.println("I "+Dupla+" "+nome);
		if(in.hasNextLine())
			JOptionPane.showMessageDialog(null, in.nextLine());
	}

}
