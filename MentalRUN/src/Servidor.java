import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class Servidor {
	
	private static HashMap<String, HashMap<String, Vector<Double>>> usuarios;
	public final static int port = 7777;

	/**
	 * Quando um servidor é criado, ele cria um HashMap para alocar todos os dados dos usuários.
	 * @param args
	 */
	public Servidor(){
		usuarios = new HashMap<String, HashMap<String, Vector<Double>>>();
	}
	
	/**
	 * Adiciona uma nova pontuação aos dados dos usuários.
	 * Também cria um novo usuário se não conseguir encontrar o nome do usuário especificado.
	 * @param usuario ID do usuário.
	 * @param pontuacao Pontuação do usuário a ser adicionada.
	 */
	public void addPontuacao(String usuario, String jogo, double pontuacao){
		if(!usuarios.containsKey(usuario)){
			HashMap<String, Vector<Double>> jogos = new HashMap<String, Vector<Double>>();
			usuarios.put(usuario, jogos);
		}
		if(!usuarios.get(usuario).containsKey(jogo)){
			Vector<Double> pontuacoes = new Vector<Double>();
			usuarios.get(usuario).put(jogo, pontuacoes);
		}
		usuarios.get(usuario).get(jogo).add(pontuacao);
	}

	/**
	 * Imprime todas as pontuações.
	 */
	synchronized static void printScores(){
		System.out.println();
		for(String usuario : usuarios.keySet()){
			System.out.println(usuario+":");
			for(String jogo : usuarios.get(usuario).keySet()){
				System.out.println("\tJogou "+jogo+" "+usuarios.get(usuario).get(jogo).size()+" vezes");
				double soma = 0;
				for(Double pontuacao : usuarios.get(usuario).get(jogo))
					soma += pontuacao;
				System.out.println("\t\tPontuacao total: "+soma+" pontos");
				System.out.println("\t\tMédia: "+(soma/usuarios.get(usuario).get(jogo).size())+" pontos");
			}
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
