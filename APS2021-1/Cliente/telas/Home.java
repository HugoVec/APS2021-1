package telas;

import Common.GUI;
import Common.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import servidor.Server;


public class Home extends GUI {

    private JLabel titulo;
    private ServerSocket server;
    
    private final Socket connection;
    private final String infoConexao;
    
    private JButton botao_conexao, init_talk;
    private JList lista;
    
    private JScrollPane scroll;

    private ArrayList<String> connected_users;
    private ArrayList<String> opened_chats;
    private Map<String, ClientListener> connected_listeners;

    public Home(Socket connection, String infoConexao) {
        super("Chat - Home");
        titulo.setText("< Usuário : " + infoConexao.split(":")[0] + " >");
        this.connection = connection;
        this.setTitle("Home - " + infoConexao.split(":")[0]);
        this.infoConexao = infoConexao;
        connected_users = new ArrayList<String>();
        opened_chats = new ArrayList<String>();
        connected_listeners = new HashMap<String, ClientListener>();
        iniciarServidor(this, Integer.parseInt(infoConexao.split(":")[2]));
    }

    @Override
    protected void initComponents() {
        titulo = new JLabel();
        botao_conexao = new JButton("Atualizar contatos");
        lista = new JList();
        scroll = new JScrollPane(lista);
        init_talk = new JButton("Abrir Conversa");
    }

    @Override
    protected void configComponents() {
        this.setLayout(null);
        this.setMinimumSize(new Dimension(600, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);

        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(10, 10, 370, 40);
        titulo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        botao_conexao.setBounds(400, 10, 180, 40);
        botao_conexao.setFocusable(false);

        init_talk.setBounds(10, 400, 575, 40);
        init_talk.setFocusable(false);

        lista.setBorder(BorderFactory.createTitledBorder("Usuarios online"));
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll.setBounds(10, 60, 575, 335);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
    }

    @Override
    protected void insertComponents() {
        this.add(titulo);
        this.add(botao_conexao);
        this.add(scroll);
        this.add(init_talk);
    }

    @Override
    protected void insertActions() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("ConexÃ£o encerrada...");
                Utils.sendMessage(connection, "QUIT");
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
        botao_conexao.addActionListener(event -> getConnectedUsers());
        init_talk.addActionListener(event -> abrirChat());
    }

    @Override
    protected void start() {
        this.pack();
        this.setVisible(true);
    }

    private void getConnectedUsers() {
        Utils.sendMessage(connection, "GET_CONNECTED_USERS");
        String response = Utils.receiveMessage(connection);
        lista.removeAll();
        connected_users.clear();
        for (String user : response.split(";")) {
            if (!user.equals(infoConexao)) {
                connected_users.add(user);
            }

        }
        lista.setListData(connected_users.toArray());
    }

    private void abrirChat() {
        int index = lista.getSelectedIndex();
        if (index != -1) {
            String value = lista.getSelectedValue().toString();
            String[] splited = value.split(":");
            if (!opened_chats.contains(value)) {
                try {
                    Socket socket = new Socket(splited[1], Integer.parseInt(splited[2]));
                    Utils.sendMessage(socket, "OPEN_CHAT;" + infoConexao); // manda a mensagem para o outro lado da conversa abrir minha janela
                    ClientListener cl = new ClientListener(this, socket);
                    cl.setChat(new Chat(this, socket, value, this.infoConexao.split(":")[0]));
                    cl.setChatOpen(true);
                    connected_listeners.put(value, cl);
                    opened_chats.add(value);
                    new Thread(cl).start();

                } catch (IOException ex) {
                }
            }

        }
    }

    private void iniciarServidor(Home home, int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(port);
                    System.out.println("Servidor cliente iniciado na porta: " + port);
                    while (true) {
                        Socket client = server.accept();
                        ClientListener cl = new ClientListener(home, client);
                        new Thread(cl).start();
                    }
                } catch (IOException ex) {
                    System.err.println("[ERROR:startServer] -> " + ex.getMessage());
                }
            }
        }.start();
    }
    
    

    public ArrayList<String> getOpened_chats() {
        return opened_chats;
    }

    public String getinfoConexao() {
        return infoConexao;
    }

    public Map<String, ClientListener> getConnected_listeners() {
        return connected_listeners;
    }

}
