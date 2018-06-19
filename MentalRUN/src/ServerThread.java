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
	private String nome = "", oponente = "";
	private Scanner in;
	private PrintStream out;
	private Random r;
	private String jogosNomes[] = {"Olhos de Aguia", "Sequencia Numerica", "Todos Iguais"};
	private boolean jogosJogados[];

	/**
	 * Abre canais de comunicação.
	 * Recebe endereço de soquete e servidor.
	 * @param socket Porta para comunicação com o servidor.
	 * @param servidor Endereço do servidor para registro de log.
	 * @throws Exception
	 */
	public ServerThread(Socket socket, final Servidor servidor) throws Exception {
		this.servidor = servidor;
		this.in = new Scanner(socket.getInputStream());
		this.out = new PrintStream(socket.getOutputStream());
		this.r = new Random();
		this.jogosJogados = new boolean[jogosNomes.length];
		for(int i = 0; i < jogosJogados.length; i++)
			jogosJogados[i] = false;
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
			return;
		}
		escreveJ();
		out.println("Desconectando você\nObrigado por jogar!");
		servidor.setUsuario(nome, false);
		System.out.println(nome+" offline");
	}

	private void escreveJ() {
		while(!jogouTodos()){
			int qual = r.getIntRandom(jogosJogados.length);
			while(jogosJogados[qual])
				qual = r.getIntRandom(jogosJogados.length);
			out.println("J "+qual);
			System.out.println(nome+" jogando "+jogosNomes[qual]);
			try {
				lePontuacao();
			} catch (Exception e) {
				servidor.addPontuacao(nome, jogosNomes[qual], 0);
				System.out.println("erro p");
				System.out.println(e.getMessage());
			}
			jogosJogados[qual] = true;
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
		else if(read[0].equals("DE"))
			servidor.setUsuario(nome, false);
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
			if(read.split(" ")[1].equals("0")){//sozinho
				this.nome = toProper(read.substring(4, read.length()).trim().toLowerCase());
				servidor.setUsuario(nome, true);
				out.println("Bem-vindo "+nome);
				System.out.println(nome+" online");
			}
			else if(read.split(" ")[1].equals("1")){//dupla
				this.nome = toProper(read.substring(4, read.length()).trim().toLowerCase());
				servidor.setUsuario(nome, true);
				this.oponente = toProper(in.nextLine().trim().toLowerCase());
				out.println("Bem-vindo "+nome);
				out.println("Esperando "+oponente+" se conectar");
				while(!servidor.isUsuarioOnline(oponente)){
					Thread.sleep(3000);
				}
				out.println(oponente+" conectado");
				System.out.println(nome+" online jogando contra "+oponente);
			}
			else{
				throw new Exception("Segundo argumento do comando 'I' não é '0' nem '1'");
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
