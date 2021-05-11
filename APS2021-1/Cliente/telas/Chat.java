package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class Chat extends JFrame{
	
	private String connection;
	
	private JLabel title;
	private JTextField enviaMsg;
	private JEditorPane mensagens;
	private JButton botaoEnvia;
	private JPanel painel;
	private JScrollBar scroll;
	
	private ArrayList<String> lista_mensagens;
	
	
	
	public Chat(String connection, String title) {
		super("Bate papo" + title);
		this.connection = connection;
		iniciarComponentes();
		inserirComponentes();
		configurarComponentes();
		inserirAcoes();
		start();
	}
	

	private void iniciarComponentes() {
		lista_mensagens = new ArrayList<String>();
		}
	
	private void configurarComponentes() {	
	
	}
	
	private void inserirAcoes() {
		
	}
	
	private void inserirComponentes() {
;
	}
	
	
	private void start() {
		this.pack();
		this.setVisible(true);
	}
	

}
