
package Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class Utils {
	// Recebe a Conexao e a mensagem a ser enviada
    public static boolean sendMessage(Socket sock, String message)
    {     
        try {
        	
            ObjectOutputStream output = new ObjectOutputStream(sock.getOutputStream());
            output.flush();
            //Escreve a mensagem e retorna true
            output.writeObject(message);
            
            return true;
        } catch (IOException ex) {
        	//Exibe a mensagem de ERRO
            System.err.println("[ERRO:sendMessage] -> " + ex.getMessage());
        }
        return false;
    }
    
    // Recebe a mensagem
    public static String receiveMessage(Socket sock)
    {
        String response = null;
        try {
            ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
            //Recebe o objeto da resposta
            response = (String) input.readObject();
        } catch (IOException ex) {
        	//Exibe a mensagem de ERRO
            System.err.println("[ERRO:receiveMessage] -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
        	//Exibe a mensagem de ERRO da Classe
            System.err.println("[ERRO:receiveMessage] -> " + ex.getMessage());
        }
         return response;
    }
}
