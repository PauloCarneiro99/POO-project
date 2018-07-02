import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Thread para ler o terminal do servidor.
 * Quando alguém digita 'pontuações', imprime todas as pontuações.
 * @see Thread
 */
public class ServidorComandosThread extends Thread {

	public void run(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String x = "";
			try {
				x = br.readLine();
				if(x.equalsIgnoreCase("pontos")){
					Servidor.printPontuacoes();
				}
				if(x.split(";")[0].equalsIgnoreCase("pontos")){
					Servidor.printPontuacoes(x.split(";")[1]);
				}
				if(x.equalsIgnoreCase("online")){
					Servidor.printOnline();
				}
				if(x.equalsIgnoreCase("v")){
					Servidor.verbose = !Servidor.verbose;
				}
			} catch (Exception e) {}
		}
	}

}
