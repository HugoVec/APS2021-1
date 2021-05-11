package telas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

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
		jl_usuario =  new JLabel("Nick");
		jl_porta =  new JLabel("IP");
		jl_title =  new JLabel();
		jt_usuario = new JTextField();
		jt_porta = new JTextField();
		
	}
	
	private void configurarComponentes() {	
		this.setLayout(null);
		this.setMinimumSize(new Dimension(450, 400));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jb_login.setBounds(40,270,375,40);
		
		jl_usuario.setBounds(10,120,100,40);
		jl_usuario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		jl_porta.setBounds(10,160,100,40);
		jl_porta.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		
		jt_usuario.setBounds(120,120,265,40);
		jt_porta.setBounds(120,170,265,40);
	}
	
	private void inserirAcoes() {
		
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
