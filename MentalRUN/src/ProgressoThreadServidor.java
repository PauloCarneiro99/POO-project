import java.io.PrintStream;

public class ProgressoThreadServidor extends Thread {
	
	private Servidor servidor;
	private PrintStream out;
	private String oponente;
	
	public ProgressoThreadServidor(Servidor servidor, PrintStream out, String oponente) {
		this.servidor = servidor;
		this.out = out;
		this.oponente = oponente;
	}

	@Override
	public void run() {
		while(true){
			if(servidor.temAtualizacao(oponente)){
				out.println("POR2");
			}
			try {Thread.sleep(500);} catch (InterruptedException e) {}
		}
	}
	
}
