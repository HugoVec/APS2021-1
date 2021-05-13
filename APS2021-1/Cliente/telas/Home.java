package telas;
import Common.Utils;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class Home extends JFrame {

    private ArrayList<String> connected_users;
    private String connection_info;
    private Socket connection;

    private JLabel jl_title;
    private JButton jb_get_connected, jb_start_talk;
    private JList j_list;
    private JScrollPane scroll_pane;
    
    // Quais chats estao abertos
 	private ArrayList<String> opened_chats;
 	// Referencia para os chatListeners dos Chats abertos
 	private Map<String, ClientListener> connected_listeners;
    
    
    public Home(Socket connection, String connection_info){
        super("CHAT - HOME");
        this.connection_info = connection_info;
        this.connection = connection;
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){

    	connected_listeners = new HashMap<String, ClientListener>();
    	opened_chats = new ArrayList<String>();
        connected_users = new ArrayList<>();
        jl_title = new JLabel("< USUARIO: " + connection_info.split(":")[0] + " >", SwingConstants.CENTER);
        jb_get_connected = new JButton("Atulizar Contatos");
        jb_start_talk = new JButton("Iniciar Conversa");
        j_list = new JList();
        scroll_pane = new JScrollPane(j_list);
    }

    private void configComponents(){
        this.setLayout(null);
        this.setMinimumSize(new Dimension(600,480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);

        jl_title.setBounds(10,10,370,40);
        jl_title.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        jb_get_connected.setBounds(400,10,180,40);
        jb_get_connected.setFocusable(false);

        jb_start_talk.setBounds(10,400,575,40);
        jb_start_talk.setFocusable(false);

        j_list.setBorder(BorderFactory.createTitledBorder("Usuarios Online"));
        j_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll_pane.setBounds(10,60,575,335);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane.setBorder(null);
    }

    private void insertComponents(){
        this.add(jl_title);
        this.add(jb_get_connected);
        this.add(scroll_pane);
        this.add(jb_start_talk);
    }

    private void insertActions(){
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                Utils.sendMessage(connection, "QUIT");
                System.out.println("Conexao encerrada.");
            }

            @Override
            public void windowClosing(WindowEvent e) {

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

        jb_get_connected.addActionListener(event -> getConnectedUsers());
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }

    private void getConnectedUsers (){
        Utils.sendMessage(connection, "Get_Connected_users");
        String response = Utils.receiveMessage(connection);
        j_list.removeAll();
        connected_users.clear();
        for(String info: response.split(";")){
            if(!info.equals(connection_info)){
                connected_users.add(info);
            }
            j_list.setListData(connected_users.toArray());
        }
    }
    
    // comenta esses 2
	public ArrayList<String> getOpened_chats() {
		return opened_chats;
	}

	public Map<String, ClientListener> getConnected_listeners() {
		return connected_listeners;
	}


}





















