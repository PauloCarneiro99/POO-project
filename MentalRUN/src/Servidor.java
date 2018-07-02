import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

public class Servidor {

	private static Vector<Usuario> usuarios;
	public static final int port = 7777;
	public static boolean verbose = false;

	/**
	 * Quando um servidor é criado, ele cria um HashMap para alocar todos os dados dos usuários.
	 * @param args
	 */
	public Servidor(){
		usuarios = new Vector<Usuario>(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean contains(Object o) {
				for(int i = 0; i < this.size(); i++)
					if(this.elementAt(i).getNome().equals(o))
						return true;
				return false;
			}
			@Override
			public int indexOf(Object o) {
				for(int i = 0; i < this.size(); i++)
					if(this.elementAt(i).getNome().equals(o))
						return i;
				return -1;
			}
		};
	}

	/**
	 * Adiciona uma nova pontuação aos dados dos usuários.
	 * @param usuario ID do usuário.
	 * @param pontuacao Pontuação do usuário a ser adicionada.
	 */
	synchronized public void addPontuacao(String usuario, String jogo, double pontuacao){
		if(usuarios.contains(usuario))
			usuarios.elementAt(usuarios.indexOf(usuario)).addPontuacoes(jogo, pontuacao);
	}
	
	synchronized public void newUsuario(String nome, String IP){
		if(!nome.isEmpty() && !IP.isEmpty()){
			if(!usuarios.contains(nome))
				usuarios.add(new Usuario(nome, IP));
			else{
				usuarios.elementAt(usuarios.indexOf(nome)).setIP(IP);
				usuarios.elementAt(usuarios.indexOf(nome)).setOnline(true);
			}
		}
	}

	synchronized public void setUsuarioOnline(String nome, boolean onoff){
		if(!nome.isEmpty() && usuarios.contains(nome))
			usuarios.elementAt(usuarios.indexOf(nome)).setOnline(onoff);
	}

	synchronized public boolean isUsuarioOnline(String nome){
		if(!nome.isEmpty() && usuarios.contains(nome))
			return usuarios.elementAt(usuarios.indexOf(nome)).isOnline();
		return false;
	}

	/**
	 * Imprime todas as pontuações.
	 */
	synchronized static void printPontuacoes(){
		System.out.println();
		for(int i = 0; i < usuarios.size(); i++){
			System.out.println(usuarios.elementAt(i).getNome()+":");
			printPontuacoes(usuarios.elementAt(i).getNome());
		}
	}

	/**
	 * Imprime as pontuacoes de uma pessoa
	 */
	synchronized static void printPontuacoes(String usuario){
		double somaTotal = 0;
		if(usuarios.contains(usuario)){
			for(String jogo : usuarios.elementAt(usuarios.indexOf(usuario)).getPontuacoes().keySet()){
				int vezes = usuarios.elementAt(usuarios.indexOf(usuario)).getPontuacoes().get(jogo).size();
				System.out.println("\tJogou "+jogo+" "+vezes+" vezes");
				double soma = 0;
				for(Double pontuacao : usuarios.elementAt(usuarios.indexOf(usuario)).getPontuacoes().get(jogo))
					soma += pontuacao;
				System.out.println("\t\tTempo total: "+parseTempo(soma));
				System.out.println("\t\tMédia: "+parseTempo(soma/vezes));
				somaTotal += soma;
			}
			System.out.println("\tTempo jogando esse jogo: "+parseTempo(somaTotal));
		}
	}
	
	public static String parseTempo(double tempo){
		String parse = "";
		if(tempo > 60){
			int minutos = (int)(tempo/60);
			parse += minutos + " minuto" + (minutos > 1 ? "s " : " ");
		}
		int segundos = (int)((tempo*1000)%60000);
		parse += ((double)segundos)/1000 + " segundos";
		return parse;
	}

	synchronized static void printOnline(){
		System.out.println();
		for(int i = 0; i < usuarios.size(); i++)
			System.out.println(usuarios.elementAt(i).getNome()+" ("+usuarios.elementAt(i).getIP()+") está "+(usuarios.elementAt(i).isOnline()?"online":"offline"));
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
