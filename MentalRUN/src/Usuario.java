import java.util.HashMap;
import java.util.Vector;

public class Usuario {
	
	private String nome = "", IP = "";
	private boolean online;
	private HashMap<String, Vector<Double>> pontuacoes;
	private boolean atualizou;
	
	public Usuario(String nome, String IP){
		this.nome = nome;
		this.IP = IP;
		this.online = true;
		this.pontuacoes = new HashMap<String, Vector<Double>>();
	}
	
	public synchronized String getNome() {
		return nome;
	}
	
	public synchronized String getIP() {
		return IP;
	}
	
	public synchronized void setIP(String IP) {
		this.IP = IP;
	}
	
	public synchronized boolean isOnline() {
		return online;
	}
	
	public synchronized void setOnline(boolean online) {
		this.online = online;
	}
	
	public synchronized HashMap<String, Vector<Double>> getPontuacoes() {
		return pontuacoes;
	}
	
	public synchronized void addPontuacoes(String jogo, double pontuacao) {
		if(!pontuacoes.containsKey(jogo))
			pontuacoes.put(jogo, new Vector<Double>());
		pontuacoes.get(jogo).add(pontuacao);
	}

	public synchronized boolean temAtualizacao() {
		return atualizou;
	}

	public synchronized void setAtualizou(boolean atualizou) {
		this.atualizou = atualizou;
	}
	
	@Override
	public String toString() {
		String fim = nome+" ("+IP+") ";
		fim += online ? "Online\n" : "Offline\n";
		return fim;
	}
	
}
