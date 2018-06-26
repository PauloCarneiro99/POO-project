import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class Servidor {
	
	private static HashMap<String, HashMap<String, Vector<Double>>> usuarios;
	private static HashMap<String, Boolean> usuariosOnline;
	public static final int port = 7777;
	public static boolean verbose = false;

	/**
	 * Quando um servidor é criado, ele cria um HashMap para alocar todos os dados dos usuários.
	 * @param args
	 */
	public Servidor(){
		usuarios = new HashMap<String, HashMap<String, Vector<Double>>>();
		usuariosOnline = new HashMap<String, Boolean>();
	}
	
	/**
	 * Adiciona uma nova pontuação aos dados dos usuários.
	 * Também cria um novo usuário se não conseguir encontrar o nome do usuário especificado.
	 * @param usuario ID do usuário.
	 * @param pontuacao Pontuação do usuário a ser adicionada.
	 */
	synchronized public void addPontuacao(String usuario, String jogo, double pontuacao){
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
	
	synchronized public void setUsuario(String nome, boolean onoff){
		if(!nome.isEmpty())
			usuariosOnline.put(nome, onoff);
	}
	
	synchronized public boolean isUsuarioOnline(String nome){
		if(!usuariosOnline.containsKey(nome))
			setUsuario(nome, false);
		return usuariosOnline.get(nome);
	}

	/**
	 * Imprime todas as pontuações.
	 */
	synchronized static void printPontuacoes(){
		System.out.println();
		for(String usuario : usuarios.keySet()){
			System.out.println(usuario+":");
			printPontuacoes(usuario);
		}
	}
	
	/**
	 * Imprime as pontuacoes de uma pessoa
	 */
	synchronized static void printPontuacoes(String usuario){
		if(usuarios.containsKey(usuario))
			for(String jogo : usuarios.get(usuario).keySet()){
				System.out.println("\tJogou "+jogo+" "+usuarios.get(usuario).get(jogo).size()+" vezes");
				double soma = 0;
				for(Double pontuacao : usuarios.get(usuario).get(jogo))
					soma += pontuacao;
				System.out.println("\t\tPontuacao total: "+soma+" segundos");
				System.out.println("\t\tMédia: "+(soma/usuarios.get(usuario).get(jogo).size())+" segundos");
			}
	}
	
	synchronized static void printOnline(){
		System.out.println();
		for(String usuario : usuariosOnline.keySet())
			System.out.println(usuario+" está "+(usuariosOnline.get(usuario)?"online":"offline"));
	}

	/**
	 * Abre um servidor e uma porta para o jogo ser jogado.
	 * Cada nova conexão é tratada como uma thread.
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ServerSocket socket = new ServerSocket(port);
		System.out.printf("Porta "+port+" aberta com o IP: ");
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		int count = 0;
		while(e.hasMoreElements()){
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    while (ee.hasMoreElements()){
		        count++;
		        InetAddress i = (InetAddress) ee.nextElement();
		        if(count == 2) System.out.println(i.getHostAddress());
		    }
		}
		Servidor servidor = new Servidor();
		new ThreadPontuacao().start();
		while(true){
			Socket client = socket.accept();
			new ServerThread(client, servidor).start();
			System.out.println("Nova conexão com o cliente "+client.getInetAddress().getHostAddress());
		}
	}
	
}
