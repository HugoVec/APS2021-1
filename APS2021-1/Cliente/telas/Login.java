package telas;
import javax.swing.*;

public class Login extends JFrame {
	
	
	private JButton jb_login;
	private JLabel jl_usuario, jl_porta, jl_title;
	private JTextField jt_usuario, jt_porta;
	
	
	public Login() {
		super("Login");
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
		
	}
	
	private void inserirComponentes() {
	
	}
	
	private void start() {
		this.pack();
		this.setVisible(true);
	}

}
