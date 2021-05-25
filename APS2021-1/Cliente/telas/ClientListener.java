
package telas;

import Common.Utils;
import java.io.IOException;
import java.net.Socket;
import java.util.*;



public class ClientListener implements Runnable {

    //variável para verificar a conexão da trad
    private boolean rodando;
    private Socket socket;
    //referencia da pagina home
    private Home home;
    //variável para verificar se o chat está aberto
    private boolean chatAberto;
    
    private String connection_info;

    private Chat chat;

    //método construtor que recebe a conexão e a referência da pagina HOME
    public ClientListener(Home home, Socket socket) {
        chatAberto = false;
        this.home = home;
        rodando = false;
        this.socket = socket;
    }

    //getters and setters das variáves chat, chatAberto, rodando
    public boolean isRodando() {
        return rodando;
    }

    public void setRodando(boolean rodando) {
        this.rodando = rodando;
    }

    public boolean isChatAberto() {
        return chatAberto;
    }

    public void setChatAberto(boolean chatAberto) {
        this.chatAberto = chatAberto;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    //métode sobrescrito da classe ClientListener
    @Override
    public void run() {
        rodando = true;
        String message;
        //estrutura para verificar a conexão da trad
        while (rodando) {
            message = Utils.receiveMessage(socket);
            if (message == null || message.equals("CHAT_CLOSE")) {
                if (chatAberto) {
                    home.getOpened_chats().remove(connection_info);
                    home.getConnected_listeners().remove(connection_info);
                    chatAberto = false;
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        System.err.println("[ClientListener:run] -> " + ex.getMessage());
                    }
                    chat.dispose();
                }
                rodando = false;
            } else {
                String[] fields = message.split(";");
                if (fields.length > 1) {
                    if (fields[0].equals("OPEN_CHAT")) {
                        String[] splited = fields[1].split(":");
                        connection_info = fields[1];
                        if (!chatAberto) {
                            home.getOpened_chats().add(connection_info);
                            home.getConnected_listeners().put(connection_info, this);
                            chatAberto = true;
                            chat = new Chat(home, socket, connection_info, home.getinfoConexao().split(":")[0]);
                        }
                    } else if (fields[0].equals("MESSAGE")) {
                        chat.append_message(fields[1]);
                    }
                }
            }
            System.out.println(" >> Mensagem: " + message);
        }
    }
}
