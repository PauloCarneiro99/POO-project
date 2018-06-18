import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class Servidor {
	
	private static HashMap<String, Vector<Double>> usuarios;
	public final static int port = 7777;

	/**
	 * Quando um servidor é criado, ele cria um HashMap para alocar todos os dados dos usuários.
	 * @param args
	 */
	public Servidor(){
		usuarios = new HashMap<String, Vector<Double>>();
	}
	
	/**
	 * Adiciona uma nova pontuação aos dados dos usuários.
	 * Também cria um novo usuário se não conseguir encontrar o nome do usuário especificado.
	 * @param nome ID do usuário.
	 * @param pontuacao Pontuação do usuário a ser adicionada.
	 */
	public void addPontuacao(String nome, double pontuacao){
		if(!usuarios.containsKey(nome)){
			Vector<Double> pontuacoes = new Vector<Double>();
			usuarios.put(nome, pontuacoes);
		}
		usuarios.get(nome).add(pontuacao);
	}

	/**
	 * Imprime todas as pontuações.
	 */
	synchronized static void printScores(){
		System.out.println();
		for(String nome : usuarios.keySet()){
			double soma = 0;
			for(Double pontuacao : usuarios.get(nome))
				soma += pontuacao;
			System.out.println(nome+":\n\tJogou "+usuarios.get(nome).size()+" vezes");
			System.out.println("\tPontuacao total: "+soma+" pontos");
			System.out.println("\tMédia: "+(soma/usuarios.get(nome).size())+" pontos");
		}
	}

	/**
	 * Abre um servidor e uma porta para o jogo ser jogado.
	 * Cada nova conexão é tratada como uma thread.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(port);
		System.out.println("Porta "+port+" aberta");
		Servidor servidor = new Servidor();
		new ThreadPontuacao().start();
		while(true){
			Socket client = socket.accept();
			new ServerThread(client, servidor).start();
			System.out.println("Nova conexão com o cliente "+client.getInetAddress().getHostAddress());
		}
	}
	
}
