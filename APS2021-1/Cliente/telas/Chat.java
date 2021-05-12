package telas;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

public class Chat extends JFrame{
	
	private String connection;
	
	private JLabel title;
	private JTextField enviaMsg;
	private JEditorPane mensagens;
	private JButton botaoEnvia;
	private JPanel painel;
	private JScrollPane scroll;
	
	private ArrayList<String> lista_mensagens;
	
	
	
	public Chat(String connection, String title) {
		super("Bate papo " + title);
		this.connection = connection;
		iniciarComponentes();
		configurarComponentes();
		inserirComponentes();
		inserirAcoes();
		start();
	}
	

	private void iniciarComponentes() {
		lista_mensagens = new ArrayList<String>();
		title = new JLabel(connection.split(":")[0], SwingConstants.CENTER);
		mensagens = new JEditorPane();
		scroll = new JScrollPane(mensagens);
		enviaMsg = new JTextField();
		botaoEnvia = new JButton("Envia");
		painel = new JPanel(new BorderLayout());
		
		
	}
	
	private void configurarComponentes() {	
		this.setMinimumSize(new Dimension(480, 720));
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mensagens.setContentType("text/html");
		mensagens.setEditable(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		botaoEnvia.setSize(100,40);
	
	}
	

	private void inserirComponentes() {
		this.add(title, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
		this.add(painel, BorderLayout.SOUTH);
		painel.add(enviaMsg, BorderLayout.CENTER);
		painel.add(botaoEnvia, BorderLayout.EAST);

	}
	
	private void inserirAcoes() {
		botaoEnvia.addActionListener(event -> enviaMensagem());
	}
	
	private void atualizaMensagem(String msg) {
		lista_mensagens.add(msg);
		String vazio = "";
		
		for (String a: lista_mensagens) {
			vazio += a;
		}
		mensagens.setText(vazio);
	}
	
	private void enviaMensagem() {
		if(enviaMsg.getText().length() > 0) {
			DateFormat dt = new SimpleDateFormat("hh:mm");
			atualizaMensagem("[" + dt.format(new Date()) + "] <b>Eu:</b> " + enviaMsg.getText() + "<br>");
			enviaMsg.setText("");
		}
	}
	

	
	
	
	
	private void start() {
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Chat chat = new Chat("Dolin:127.0.0.1:9999", "Jão");
	}

}
