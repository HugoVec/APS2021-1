package servidor;

import java.net.Socket;
import java.util.Map;

import Common.Utils;

public class LeituraCliente implements Runnable {
	private String connection_info;
	private Socket connection;
	private Servidor servidor;
	private boolean rodando;
	
	
	public LeituraCliente(String connection_info, Socket connection, Servidor servidor){
		this.connection_info = connection_info;
		this.connection = connection;
		this.servidor = servidor;
		this.rodando = false;
	}
		
	public boolean isRodando() {
		return rodando;
	}
	
	public void setRodando(boolean rodando) {
		this.rodando = rodando;
	}
	
	public void run() {
		rodando = true;
		String mensagem;
		while(rodando) {
			mensagem = Utils.receiveMessage(connection);
			if(mensagem.equals("QUIT")) {
				rodando = false;
			}else if(mensagem.equals("GET_CONNECTED_USERS")){
				System.out.println("Solicitação de atualizar lista de contatos...");
				String response = "";
				for(Map.Entry<String,LeituraCliente> pair: servidor.getClientes().entrySet()) {
					response += (pair.getKey() + ";");
				}
				Utils.sendMessage(connection, response);
			}else {
				System.out.println("Recebido: " + mensagem);

			}
		
		}		
	}	
}
