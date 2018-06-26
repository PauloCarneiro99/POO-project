import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
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
	private int quantidadeJogos;
	private boolean kill = false;
	private double tempoTotal;

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
		kill = false;
		tempoTotal = 0;
	}

	/**
	 * Tenta ler as linhas de comando recebidas do cliente.
	 */
	public void run(){
		try {
			leID();
		} catch (Exception e) {
			servidor.addPontuacao("'I' ERRO", "'I' ERRO", 0);
			System.out.println(e.getMessage());
			return;
		}
		System.out.println(quantidadeJogos);
		do {
			try {
				if(kill) break;
				lePontuacao();
				quantidadeJogos--;
			} catch (Exception e) {
				if(kill) break;
				servidor.addPontuacao(nome, "'P' ERRO", 0);
				System.out.println(e.getMessage());
			}
		} while (quantidadeJogos > 0);
		
		System.out.println("acabou");
		if(servidor.isUsuarioOnline(oponente)) out.println("VOCÊ VENCEU!");
		else out.println("VOCÊ PERDEU!");
		out.println("Seu tempo total foi: "+(tempoTotal > 60 ? (int)((tempoTotal - (tempoTotal % 60))/60)+"m " : "")+((int)((tempoTotal%60)*1000))/1000+"s");
		out.println("Desconectanto você\nObrigado por jogar!");
		servidor.setUsuario(nome, false);
		System.out.println(nome+" offline");
	}

	private void lePontuacao() throws Exception {
		String read[] = null;
		if(in.hasNextLine()){
			read = in.nextLine().trim().split(";");
		}
		if(Servidor.verbose) System.out.println("Ser Leu: "+Arrays.toString(read));
		if(read[0].equals("P")){
			try {
				double tempo = Double.parseDouble(read[2]);
				tempoTotal += tempo;
				servidor.addPontuacao(nome, read[1], tempo);
			} catch (Exception e) {
				throw new Exception("Terceiro argumento do comando 'P' não é um numero");
			}
		}
		else if(read[0].equals("DE")){
			kill = true;
			servidor.setUsuario(nome, false);
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
		String read[] = null;
		if(in.hasNextLine()){
			read = in.nextLine().trim().split(";");
		}
		if(Servidor.verbose) System.out.println("Ser Leu: "+Arrays.toString(read));
		if(read[0].equals("I")){
			if(read[1].equals("0")){//sozinho
				try {
					quantidadeJogos = Integer.parseInt(read[2]);
					this.nome = toProper(read[3].trim().toLowerCase());
					servidor.setUsuario(nome, true);
					if(Servidor.verbose) System.out.println("Ser Env: "+"Bem-vindo "+nome);
					out.println("Bem-vindo "+nome);
					System.out.println(nome+" online");
				} catch (Exception e) {
					throw new Exception("Terceiro argumento do comando 'I' não é um numemro");
				}
			}
			else if(read[1].equals("1")){//dupla
				try {
					quantidadeJogos = Integer.parseInt(read[2]);
					this.nome = toProper(read[3].trim().toLowerCase());
					servidor.setUsuario(nome, true);
					this.oponente = toProper(read[4].trim().toLowerCase());
					if(Servidor.verbose) System.out.println("Ser Env: "+"Bem-vindo "+nome);
					out.println("Bem-vindo "+nome);
					if(Servidor.verbose) System.out.println("Ser Env: "+"Esperando "+oponente+" se conectar");
					out.println("Esperando "+oponente+" se conectar");
					while(!servidor.isUsuarioOnline(oponente)){
						Thread.sleep(3000);
					}
					if(Servidor.verbose) System.out.println("Ser Env: "+oponente+" conectado");
					out.println(oponente+" conectado");
					System.out.println(nome+" online jogando contra "+oponente);
				} catch (Exception e) {
					throw new Exception("Terceiro argumento do comando 'I' não é um numemro");
				}
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
