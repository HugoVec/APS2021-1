
package telas;

import Common.GUI;
import Common.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

public class Chat extends GUI {

    private JLabel titulo;
    private JEditorPane mensagemUser;
    private JTextField campoMensagem;
    private JButton botaoMensagem;
    
    private JPanel panel;
    private JScrollPane scroll;

    private ArrayList<String> message_list;
    private Home home;
    private Socket connection;
    private String connection_info;

    public Chat(Home home, Socket connection, String connection_info, String title) {
        super("Chat " + title);
        this.home = home;
        this.connection_info = connection_info;
        message_list = new ArrayList<String>();
        this.connection = connection;
        this.titulo.setText(connection_info.split(":")[0]);
        this.titulo.setHorizontalAlignment(SwingConstants.CENTER);

    }

    public Chat() {
        super("chat");
    }
    

    /** 
     * Essa funcao inicia os componentes visuais
     * **/
    @Override
    protected void initComponents() {
        Color myWhite = new Color(39, 49, 184);
        titulo = new JLabel();
        mensagemUser = new JEditorPane();
        scroll = new JScrollPane(mensagemUser);
        campoMensagem = new JTextField();
        botaoMensagem = new JButton("Enviar");
        botaoMensagem.setForeground(Color.white);
        botaoMensagem.setBackground(myWhite);
        panel = new JPanel(new BorderLayout());
    }
    
    /** 
     * Essa funcao configura os componentes visuais
     * **/
    @Override
    protected void configComponents() {
        this.setMinimumSize(new Dimension(480, 720));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mensagemUser.setContentType("text/html");
        mensagemUser.setEditable(false);
        
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        botaoMensagem.setSize(100, 40);
    }
    
    /** 
     * Essa funcao insere os componentes visuais
     * nas posiçoes adequadas do BorderLayout
     * **/
    @Override
    protected void insertComponents() {
        this.add(titulo, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
        panel.add(campoMensagem, BorderLayout.CENTER);
        panel.add(botaoMensagem, BorderLayout.EAST);
    }

    /** 
     * Essa funcao configura as acoes da tela de Chat
     * 
     * **/
    @Override
    protected void insertActions() {
    	/** 
         * Serve para enviar a mensagem quando
         * o usuario clicar a tecla ENTER
         * 
         * **/
        campoMensagem.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    send();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        
        /** 
         * Quando clicar no botão de enviar 
         * ele chama a funcao send e envia a mensagem
         * 
         * **/
        botaoMensagem.addActionListener(event -> send());
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Utils.sendMessage(connection, "CHAT_CLOSE");
                home.getOpened_chats().remove(connection_info);
                home.getConnected_listeners().get(connection_info).setChatAberto(false);
                home.getConnected_listeners().get(connection_info).setRodando(false);
                home.getConnected_listeners().remove(connection_info);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

        });
    }
    
    /** 
     * Essa funcao serve para atualizar o input da 
     * mensagem depois de enviala
     * 
     * **/
    public void append_message(String received) {
        message_list.add(received);
        String message = "";
        for (String str : message_list) {
            message += str;
        }
        mensagemUser.setText(message);
    }

    @Override
    protected void start() {
        this.pack();
        this.setVisible(true);
    }
    
    /** 
     * Essa funcao serve para quando
     * a mensagem vai ser enviada
     * 
     * **/
    private void send() {
        DateFormat df = new SimpleDateFormat("hh:mm");
        message_list.add("<b>[" + df.format(new Date()) + "] Eu: </b><i>" + campoMensagem.getText() + "</i><br>");
        Utils.sendMessage(connection, "MESSAGE;<b>[" + df.format(new Date()) + "] " + home.getinfoConexao().split(":")[0] + ": </b><i>" + campoMensagem.getText() + "</i><br>");
        String message = "";
        for (String str : message_list) {
            message += str;
        }
        mensagemUser.setText(message);
        campoMensagem.setText("");

    }

    public static void main(String[] args) {
        Chat chat = new Chat();
    }


}
