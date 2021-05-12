package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import Common.Utils;

public class Servidor {

	public static final String HOST = "127.0.0.1";
	public static final int PORTA = 4444;
	
	private ServerSocket servidor;
	private Map<String, LeituraCliente> clientes;
	
	
	public Servidor() {
		
		try {
			String connection_info;
			clientes = new HashMap<String, LeituraCliente>();
			servidor = new ServerSocket(PORTA);
			System.out.println("Iniciado a conexão, HOST: " + HOST + "PORTA: " + PORTA);
			while(true) {
				Socket connection = servidor.accept();
				connection_info = Utils.receiveMessage(connection);
				if(checkLogin(connection_info)) {
					LeituraCliente cl = new LeituraCliente(connection_info, connection, this);
					clientes.put(connection_info, cl);
					Utils.sendMessage(connection, "SUCESS");
					new Thread(cl).start();
				}else {
					Utils.sendMessage(connection, "ERROR");
					
				}
			}
		
		}catch(IOException ex) {
			System.err.println("[ERROR:servidor] -> " + ex.getMessage());
			
		}
	}	
	
	public Map<String, LeituraCliente> getClientes(){
		return clientes;
	}
	
	
	
	private boolean checkLogin(String connection_info) {
		String[] splited = connection_info.split(":");	
		for(Map.Entry<String,LeituraCliente> pair: clientes.entrySet()) {
			String[] parts = pair.getKey().split(":");
			if(parts[0].toLowerCase().equals(splited[0].toLowerCase())) {
				return false;
			}else if((parts[1] + parts[2]).equals(splited[1] + splited[2])) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Servidor servidor = new Servidor();
	}
	
}
