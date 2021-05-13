package telas;

import java.net.Socket;

import Common.Utils;

public class ClientListener implements Runnable{
	
	private boolean running;
	private boolean chatOpen;
	private Socket connection;
	private Home home;
	private String connection_info;
	private Chat chat;
	
	// Representa a escuta entre os chats
	public ClientListener(Home home, Socket connection) {
		chatOpen = false;
		running = false;
		this.home = home;
		this.connection = connection;
		this.connection_info = null;
		this.chat = null;
	}
	
	

	public boolean isRunning() {
		return running;
	}



	public void setRunning(boolean running) {
		this.running = running;
	}



	public boolean isChatOpen() {
		return chatOpen;
	}



	public void setChatOpen(boolean chatOpen) {
		this.chatOpen = chatOpen;
	}



	public Chat getChat() {
		return chat;
	}



	public void setChat(Chat chat) {
		this.chat = chat;
	}

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		String message;
		while(running) {
			message = Utils.receiveMessage(connection);
			if(message == null || message.equals("CHAT_CLOSE")) {
				if(chatOpen) {
					home.getOpened_chats().remove(connection_info);
					home.getConnected_listeners().remove(connection_info);
					chatOpen = false;
					try {
						connection.close();
					} catch (Exception e) {
						System.out.println("Erro: " + e.getMessage());
						// TODO: handle exception
					}
					chat.dispose();
				}
				running = false;
			} else {
				// NÃO SEI SE PRECISA....
			}
			
		}
	}

}
