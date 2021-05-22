
package servidor;

import Common.Utils;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.*;
import servidor.Server;


public class ClientListener implements Runnable {

    private boolean running;
    private Socket socket;
    private String nick;
    private Server server;

    public ClientListener(String nick, Socket socket, Server server) {
        this.server = server;
        running = false;
        this.socket = socket;
        this.nick = nick;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        running = true;
        String message;
        while (running) {
            message = Utils.receiveMessage(socket);
            if (message.toLowerCase().equals("quit")) {
                server.getClientes().remove(nick);
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.err.println("[ClientListener:Run] -> " + ex.getMessage());
                }
                running = false;
            } else if (message.equals("GET_CONNECTED_USERS")) {
                System.out.println("Solicitação de atualizar lista de contatos...");
                String response = "";
                for (Map.Entry<String, ClientListener> pair : server.getClientes().entrySet()) {
                    response += (pair.getKey() + ";");
                }
                Utils.sendMessage(socket, response);
            }
            System.out.println(" >> Mensagem: " + message);
        }
    }

}
