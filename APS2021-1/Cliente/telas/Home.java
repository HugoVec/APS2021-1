package telas;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;

class Home extends JFrame {

    private String connection_info;
    private Socket connection;
    private JLabel jl_title;
    private JButton jb_get_connected, jb_start_talk;
    private JList j_list;
    private JScrollPane scroll_pane;
    
    
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

    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }

}





















