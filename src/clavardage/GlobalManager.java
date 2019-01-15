package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

public class GlobalManager implements RemoteConnectionListener, UserListGUIEventListener, WindowListener, LogInListener, UserListChangesListener{
    private UserListWindow userListWindow ;
    private LoggingWindow logWindow;
    private BroadcastServer bs;
    private ConversationServer cs;
    private int maxConvAllowed = 1000;
  //  private User localUser;
   // private SystemState ss ;

    //BDD management
    String url = "jdbc:mysql://localhost:3306/chat_system?useLegacyDatetimeCode=false&serverTimezone=UTC";
    String mysqluser = "java";
    String pswd = "chat_system";
    Connection SQLconnection = null;

    public void windowDeactivated(WindowEvent e){}

    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){
    }
    public void windowIconified(WindowEvent e){    }
    public void windowOpened(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowClosing(WindowEvent e) {
        bs.broadcastDisconnection();
    }

    public GlobalManager(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            SQLconnection = DriverManager.getConnection(url, mysqluser, pswd);
            // System.out.println("it's ok");
        } catch (Exception e) {
            System.out.println("Java couldn't connect to MySQL : " + e.toString());
        }
        logWindow = new LoggingWindow("Chat System - Log in");
        this.logWindow.addLogInListener(this);

     //   this.localUser = new User("");
        this.bs = new BroadcastServer(this, SQLconnection);
        bs.addLogInListener(this);
        this.cs  = new ConversationServer(this,SQLconnection);
        cs.addRemoteConnectionListener(this);


    }

    public void remoteDisconnection(){
        System.out.println("GlobalManager detected disconnection");
        userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
    }

    public void conversationClosed(){
        bs.broadcastConversationClosed();
    }


    @Override
    public void remoteConnection(RemoteConnectionEvent evt) {
        Socket sock = evt.sock;
        User remote = bs.getUserFromIP(sock.getInetAddress().toString());
        bs.broadcastNewConversation();
        cs.acceptConv(remote,bs.getLocalUser(),sock);
    }

    @Override
    public void changedLocalUsername(String e){
        bs.broadcastUsernameChanged(e);
        cs.updateLocalNicknameInConvo(e);
        userListWindow.refreshNicknameLabel(e);
    }

    @Override
    public void changedRemoteUsername(String id, String username){
        cs.updateRemoteNicknameInConvo(id,username);
        userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
    }

    @Override
    public void displayHistoryRequestFromGUI(String userID, String remoteUsername){

        ArrayList<Message> messages = cs.getMessagesWith(userID,bs.getLocalUser().getID());

        if (messages != null)
            new HistoryWindow("History of conversation with " + remoteUsername, messages);

        else
            new NotificationWindow("There is no history of conversation with this user yet.");

    }

    public void newNicknameRequestFromGUI() {
        logWindow.start(false);

    }
    public void loggedIn(LogInEvent logged){
        bs.loggedIn(logged);
        this.userListWindow = new UserListWindow(bs.getLocalUsername());
        this.userListWindow.addUserListGUIEventListener(this);
        this.userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
        this.userListWindow.addWindowListener(this);
    }


    public void sessionRequestFromGUI(String userID){
        if (bs.getConversationCount() < maxConvAllowed) {
            User u = bs.getUserFromId(userID);
            cs.requestNewConversation(u, bs.getLocalUser());
            bs.broadcastNewConversation();
        }
        else { System.out.println("Il y a trop de conversations en cours. Veuillez réessayer plus tard."); }
    }

    public void start(){

        logWindow.start(true);
        bs.start();
        cs.start();

        while(true);

    }

  /*  private String getMyIp(){
        String myip = "";
       try {
         //   URL url = new URL("http://checkip.amazonaws.com/");
           // BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            //myip= (br.readLine());
         //  myip = InetAddress.getLocalHost().getHostAddress();
           DatagramPacket packet = new DatagramPacket()
        } catch (Exception e){
            System.out.println("Couldn't retrieve my IP adress: "+e.toString());
        }
        return myip;
    }*/

    @Override
    public void userListHasChanged(HashMap<String, User> onlineUsers) {
        this.userListWindow.refreshUserListInGUI(onlineUsers);
    }
}
