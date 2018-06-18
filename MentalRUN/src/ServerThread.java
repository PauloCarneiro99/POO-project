import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Thread para lidar com cada usuário.
 * @see Servidor
 * @see Cliente
 * @see Thread
 */
public class ServerThread extends Thread {

	private Servidor servidor;
	private String nome;
	private Scanner in;
	private PrintStream out;
	private double tempoTotal;

	/**
	 * Abre canais de comunicação.
	 * Recebe endereço de soquete e servidor.
	 * @param socket Porta para comunicação com o servidor.
	 * @param servidor Endereço do servidor para registro de log.
	 * @throws Exception
	 */
	public ServerThread(Socket socket, Servidor servidor) throws Exception {
		this.servidor = servidor;
		this.in = new Scanner(socket.getInputStream());
		this.out = new PrintStream(socket.getOutputStream());
		this.tempoTotal = 0;
	}

	/**
	 * Tries to read command lines received from client.
	 */
	public void run(){
		try {
			readI();
		} catch (Exception e) {
			servidor.addPontuacao("I ERRO", 0);
			System.out.println(e.getMessage());
			in.close();
			out.close();
			return;
		}
		try {
			//readF();
		} catch (Exception e) {
			servidor.addPontuacao(nome, 0);
			System.out.println(e.getMessage());
			in.close();
			out.close();
			System.out.println(nome+" offline");
			return;
		}
		servidor.addPontuacao(nome, tempoTotal);
		out.println("Desconectando você\nObrigado por jogar!");
		in.close();
		out.close();
		System.out.println(nome+" offline");
	}

	/**
	 * Reads 'I' command from client.
	 * If successful, sends welcome message back to him.
	 * @throws BozoException
	 */
	private void readI() throws Exception {
		String read = in.nextLine().trim();
		if(read.split(" ")[0].equals("I")){
			this.nome = toProper(read.substring(4, read.length()).trim().toLowerCase());
			out.println("Bem-vindo "+nome);
			System.out.println(nome+" online");
			if(read.split(" ")[1].equals("1")){//dupla
			}
			else{//sozinho
				
			}
		}
		else
			throw new Exception("Comando 'I' não encontrado");
	}

	/**
	 * Converte uma string para Nome Prorpio.
	 * @param input String a ser convertida.
	 * @return String convertida.
	 */
	private String toProper(String input) {
		StringBuilder proper = new StringBuilder();
		boolean space = true;
		for(char c : input.toCharArray()) {
			if(Character.isSpaceChar(c) || Character.isWhitespace(c))
				space = true;
			else if(space) {
				c = Character.toUpperCase(c);
				space = false;
			}
			proper.append(c);
		}
		return proper.toString();
	}

}
