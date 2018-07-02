import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class Servidor {

	private static HashMap<String, HashMap<String, Vector<Double>>> usuariosPontuacoes;
	private static HashMap<String, Boolean> usuariosOnline;
	private static HashMap<String, String> usuariosIPs;
	public static final int port = 7777;
	public static boolean verbose = false;

	/**
	 * Quando um servidor é criado, ele cria um HashMap para alocar todos os dados dos usuários.
	 * @param args
	 */
	public Servidor(){
		usuariosPontuacoes = new HashMap<String, HashMap<String, Vector<Double>>>();
		usuariosOnline = new HashMap<String, Boolean>();
		usuariosIPs = new HashMap<String, String>();
	}

	/**
	 * Adiciona uma nova pontuação aos dados dos usuários.
	 * Também cria um novo usuário se não conseguir encontrar o nome do usuário especificado.
	 * @param usuario ID do usuário.
	 * @param pontuacao Pontuação do usuário a ser adicionada.
	 */
	synchronized public void addPontuacao(String usuario, String jogo, double pontuacao){
		if(!usuariosPontuacoes.containsKey(usuario)){
			HashMap<String, Vector<Double>> jogos = new HashMap<String, Vector<Double>>();
			usuariosPontuacoes.put(usuario, jogos);
		}
		if(!usuariosPontuacoes.get(usuario).containsKey(jogo)){
			Vector<Double> pontuacoes = new Vector<Double>();
			usuariosPontuacoes.get(usuario).put(jogo, pontuacoes);
		}
		usuariosPontuacoes.get(usuario).get(jogo).add(pontuacao);
	}
	
	synchronized public void newUsuario(String nome, String IP){
		if(!nome.isEmpty() && !IP.isEmpty()){
			usuariosIPs.put(nome, IP);
			usuariosOnline.put(nome, true);
		}
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
		for(String usuario : usuariosPontuacoes.keySet()){
			System.out.println(usuario+":");
			printPontuacoes(usuario);
		}
	}

	/**
	 * Imprime as pontuacoes de uma pessoa
	 */
	synchronized static void printPontuacoes(String usuario){
		double somaTotal = 0;
		if(usuariosPontuacoes.containsKey(usuario)){
			for(String jogo : usuariosPontuacoes.get(usuario).keySet()){
				System.out.println("\tJogou "+jogo+" "+usuariosPontuacoes.get(usuario).get(jogo).size()+" vezes");
				double soma = 0;
				for(Double pontuacao : usuariosPontuacoes.get(usuario).get(jogo))
					soma += pontuacao;
				System.out.println("\t\tTempo total: "+soma+" segundos");
				System.out.println("\t\tMédia: "+(soma/usuariosPontuacoes.get(usuario).get(jogo).size())+" segundos");
				somaTotal += soma;
			}
			System.out.println("\tTempo jogando esse jogo: "+somaTotal+" segundos");
		}
	}

	synchronized static void printOnline(){
		System.out.println();
		for(String usuario : usuariosIPs.keySet())
			System.out.println(usuario+" ("+usuariosIPs.get(usuario)+") está "+(usuariosOnline.get(usuario)?"online":"offline"));
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
		new ServidorComandosThread().start();
		while(true){
			Socket client = socket.accept();
			new ServidorThread(client, servidor).start();
			System.out.println("Nova conexão com o cliente "+client.getInetAddress().getHostAddress());
		}
	}

}
