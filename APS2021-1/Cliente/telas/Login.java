package telas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import javax.swing.*;
import Common.Utils;
import servidor.Servidor;
import java.net.Socket;

public class Login extends JFrame {
	
	
	private JButton jb_login;
	private JLabel jl_usuario, jl_porta, jl_title;
	private JTextField jt_usuario, jt_porta;
	
	
	public Login() {
		super("Login");
		iniciarComponentes();
		inserirComponentes();
		configurarComponentes();
		inserirAcoes();
		start();
		
	}
	
	private void iniciarComponentes() {
		jb_login = new JButton("Logar");
		jl_usuario =  new JLabel("Nick", SwingConstants.CENTER);
		jl_porta =  new JLabel("IP", SwingConstants.CENTER);
		jl_title =  new JLabel();
		jt_usuario = new JTextField();
		jt_porta = new JTextField();
		
	}
	
	private void configurarComponentes() {	
		this.setLayout(null);
		this.setMinimumSize(new Dimension(450, 400));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jl_title.setBounds(10, 10, 375, 100);
		ImageIcon icon = new ImageIcon("logo.png");
		jl_title.setIcon(new ImageIcon(icon.getImage().getScaledInstance(125, 100, Image.SCALE_SMOOTH)));
		
		
		jb_login.setBounds(40,270,375,40);
		
		jl_usuario.setBounds(10,120,100,40);
		jl_usuario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		jl_porta.setBounds(10,160,100,40);
		jl_porta.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		
		jt_usuario.setBounds(120,120,265,40);
		jt_porta.setBounds(120,170,265,40);
	}
	
	private void inserirAcoes() {
		jb_login.addActionListener(event -> {
			try {
			String nickname = jt_usuario.getText();
			jt_usuario.setText("");
			int porta = Integer.parseInt(jt_porta.getText());
			jt_porta.setText("");
			Socket connection = new Socket(Servidor.HOST, Servidor.PORTA);
			String connection_info = (nickname + ":" + connection.getLocalAddress().getHostAddress() + ":" + porta);
			Utils.sendMessage(connection, connection_info);
			if(Utils.receiveMessage(connection).equals("SUCESS")) {
				new Home(connection, connection_info);
				this.dispose();			
			}else {
				JOptionPane.showMessageDialog(null, "Usuário já conectado tente em outra porta");
			}
			}catch(IOException ex){
				JOptionPane.showMessageDialog(null, "Erro ao conectar");
			}
		});
		
	}
	
	private void inserirComponentes() {
		this.add(jb_login);
		this.add(jl_porta);
		this.add(jl_usuario);
		this.add(jl_title);
		this.add(jt_porta);
		this.add(jt_usuario);
	}
	
	
	private void start() {
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Login login = new Login();
	}
	
}
