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
	private Random r;
	private double tempo;
	private String jogosNomes[] = {"Olhos de Aguia", "Sequencia Numerica", "Todos Iguais"};
	private boolean jogosJogados[] = {false, false, false};

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
		this.tempo = 0;
		this.r = new Random();
	}

	/**
	 * Tenta ler as linhas de comando recebidas do cliente.
	 */
	public void run(){
		try {
			leID();
		} catch (Exception e) {
			servidor.addPontuacao("I ERRO", "I ERRO", 0);
			System.out.println(e.getMessage());
			in.close();
			out.close();
			return;
		}
		sorteiaJogo();
		out.println("Desconectando você\nObrigado por jogar!");
		in.close();
		out.close();
		System.out.println(nome+" offline");
	}

	private void sorteiaJogo() {
		while(!jogouTodos()){
			int qual = r.getIntRandom(3);
			while(jogosJogados[qual])
				qual = r.getIntRandom(3);
			out.println("J "+qual);
			try {
				lePontuacao();
			} catch (Exception e) {
				servidor.addPontuacao(nome, jogosNomes[qual], 0);
				System.out.println(e.getMessage());
			}
		}
	}
	
	private boolean jogouTodos(){
		for(int i = 0; i < jogosJogados.length; i++)
			if(!jogosJogados[i])
				return false;
		return true;
	}

	private void lePontuacao() throws Exception {
		String[] read = in.nextLine().trim().split(" ");
		if(read[0].equals("P")){
			try {
				int qual = Integer.parseInt(read[1]);
				try {
					double tempo = Double.parseDouble(read[2]);
					servidor.addPontuacao(nome, jogosNomes[qual], tempo);
				} catch (Exception e) {
					throw new Exception("Terceiro argumento do comando 'R' não é um numero");
				}
			} catch (Exception e) {
				throw new Exception("Segundo argumento do comando 'R' não é um numero");
			}
		}
		else
			throw new Exception("Comando 'P' não encontrado ou fora de contexto");
	}

	/**
	 * Reads 'I' command from client.
	 * If successful, sends welcome message back to him.
	 * @throws BozoException
	 */
	private void leID() throws Exception {
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
