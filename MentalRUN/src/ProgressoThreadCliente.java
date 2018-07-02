import java.util.Scanner;

public class ProgressoThreadCliente extends Thread {

	private Scanner in;

	public ProgressoThreadCliente(Scanner in){
		this.in = in;
	}

	@Override
	public void run() {
		while(true){
			if(in.hasNext("POR2")){
				in.nextLine();
				Inicio.increasePorcentagem2();
			}
			try {Thread.sleep(500);} catch (InterruptedException e) {}
		}
	};

}
