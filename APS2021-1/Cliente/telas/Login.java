/*
D * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import Common.GUI;
import Common.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import servidor.Server;


public class Login extends GUI {

    private JButton jb_login;
    private JLabel jl_user, jl_port, jl_title;
    private JTextField jt_user, jt_port;

    public Login() {
        super("Login");
    }

    @Override
    protected void initComponents() {
        Color myWhite = new Color(39, 49, 184);
        jl_title = new JLabel("CHAT", SwingConstants.CENTER);
        jl_title.setForeground(Color.black);
        jb_login = new JButton("Entrar");
        jb_login.setForeground(Color.white);
        jb_login.setBackground(myWhite);
        jl_user = new JLabel("Apelido", SwingConstants.CENTER);
        jl_user.setForeground(Color.black);
        jl_port = new JLabel("Porta", SwingConstants.CENTER);
        jl_port.setForeground(Color.black);
        jt_user = new JTextField();
        jt_port = new JTextField();
    }

    @Override
    protected void configComponents() {
        this.setLayout(null);
        this.setMinimumSize(new Dimension(450, 350));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);

        jl_title.setBounds(10, 10, 375, 100);
        ImageIcon icon = new ImageIcon("logo.png");
        jl_title.setIcon(new ImageIcon(icon.getImage().getScaledInstance(150, 125, Image.SCALE_SMOOTH)));

        jb_login.setBounds(10, 220, 375, 40);

        jl_user.setBounds(10, 120, 100, 40);
        jl_user.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        jl_port.setBounds(10, 170, 100, 40);
        jl_port.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        jt_user.setBounds(120, 120, 265, 40);
        jt_port.setBounds(120, 170, 265, 40);
       
    }

    @Override
    protected void insertComponents() {
        this.add(jl_title);
        this.add(jb_login);
        this.add(jl_port);
        this.add(jl_user);
        this.add(jt_port);
        this.add(jt_user);
    }

    @Override
    protected void insertActions() {
        jb_login.addActionListener(event -> {
            Socket connection;
            try {
                String nickname = jt_user.getText();
                int port = Integer.parseInt(jt_port.getText());
                jt_user.setText("");
                jt_port.setText("");
                connection = new Socket(Server.HOST, Server.PORT);
                String request = nickname + ":" + connection.getLocalAddress().getHostAddress() + ":" + port;
                Utils.sendMessage(connection, request);
                if (Utils.receiveMessage(connection).toLowerCase().equals("sucess")) {
                    new Home(connection, request);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Algum usuário já está conectado com este apelido ou tem alguém na mesma rede utilizando a mesma porta que você.");
                }
            } catch (IOException ex) {
                System.err.println("[ERROR:login] -> " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao conectar. Verifique se o servidor está em execução.");
            }

        });
    }

    @Override
    protected void start() {
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Login login = new Login();
    }

}
