import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe do cliente para o servidor do jogo.
 * @see Server
 */
public class Cliente {

	private Socket socket;
	public Scanner in;
	public PrintStream out;
	private String IPservidor = "";
	private static Inicio jogo;
	private boolean esperandoOp = true;
	private double tempoTotal;
	private static Vector<String> jogosNomes = new Vector<String>();
	private static Vector<Boolean> jogosJogados = new Vector<Boolean>();
	private static Random r;

	/**
	 * Opens up connection with server,
	 * creates windows to display the game
	 * and writes command lines to it.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new Cliente();
	}
	
	public Cliente(){
		r = new Random();

		jogosNomes.add("Caça Palavras");
		jogosNomes.add("Encontre o Par");
		jogosNomes.add("Encontre o Único");
		jogosNomes.add("Olhos de Águia");
		jogosNomes.add("Ordem Crescente");
		jogosNomes.add("Qual Tem Mais");
		jogosNomes.add("Sequência Númerica");
		jogosNomes.add("Todos Iguais");
		
		for(int i=0;i<jogosNomes.size();i++){
			jogosJogados.add(false);
		}
		tempoTotal = 0;
		while(true){
			try {
				IPservidor = "192.168.182.219";
				JTextArea textArea = new JTextArea();
				while(IPservidor.length() == 0){
					textArea.setEditable(true);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(null, scrollPane, "Digite o IP do servidor", JOptionPane.PLAIN_MESSAGE);
					IPservidor = textArea.getText().trim().split("\n")[0];
				}
				socket = new Socket(IPservidor, Servidor.port);
				break;
			} catch (Exception e) {
				IPservidor = "";
				JOptionPane.showMessageDialog(null, "Falha ao conectar-se.\nTente novamente");
			}
		}
		System.out.println("Você está conectado");
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintStream(socket.getOutputStream());
		} catch (Exception e) {}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				out.println("DE");
				in.close();
				out.close();
			}
		}));
		jogo = new Inicio(this);
	}

	public void escreveP(String nomeJogo, double tempoDecorrido) {
		System.out.println("Cli Env: "+"P;"+nomeJogo+";"+tempoDecorrido);
		out.println("P;"+nomeJogo+";"+tempoDecorrido);
		tempoTotal += tempoDecorrido;
		proximoJogo();
	}

	private static boolean jogouTodos(){
		System.out.println(jogosJogados);
		for(int i = 0; i < jogosJogados.size(); i++)
			if(!jogosJogados.elementAt(i))
				return false;
		return true;
	}
	
	public void proximoJogo(){
		if(!jogouTodos()){
			int jogo = r.getIntRandom(jogosNomes.size());
			while(jogosJogados.elementAt(jogo))
				jogo = r.getIntRandom(jogosNomes.size());
			if(jogo == 0)
				new CacaPalavras();
			else if(jogo == 1)
				new EncontrePar();
			else if(jogo == 2)
				new EncontreUnico();
			else if(jogo == 3)
				new OlhoDeAguia();
			else if(jogo == 4)
				new OrdemCrescente();
			else if(jogo == 5)
				new QualTemMais();
			else if(jogo == 6)
				new SequenciaNumerica();
			else if(jogo == 7)
				new TodosIguais();
			jogosJogados.setElementAt(true, jogo);
		}
	}

	public void escreveID(boolean Dupla, int qtd, String nome, String oponente) {
		int dupla = Dupla ? 1 : 0;
		if(Dupla){
			System.out.println("Cli Env: "+"I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			out.println("I;"+dupla+";"+qtd+";"+nome+";"+oponente);
			while(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
				System.out.println(oponente+" conectado");
				
				if(read.equalsIgnoreCase(oponente+" conectado")){
					proximoJogo();
				}
			}
		}
		else{
			System.out.println("Cli Env: "+"I;"+dupla+";"+qtd+";"+nome);
			out.println("I;"+dupla+";"+qtd+";"+nome);
			if(in.hasNextLine()){
				String read = in.nextLine();
				System.out.println("Cli Leu: "+read);
				JOptionPane.showMessageDialog(null, read);
			}
		}
	}
	
	public boolean isEsperandoOp() {
		return esperandoOp;
	}

}
