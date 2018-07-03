
public class ProgressoThreadCliente extends Thread {

	private static Cliente cliente;

	public ProgressoThreadCliente(Cliente cliente){
		ProgressoThreadCliente.cliente = cliente;
	}

	@Override
	public void run() {
		if(cliente != null)
			while(true)
				if(cliente.in.hasNext())
					if(cliente.in.hasNext("POR2")){
						cliente.in.nextLine();
						Inicio.increasePorcentagem2();
					}
	};

}
