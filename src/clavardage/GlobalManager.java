package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.HashMap;

public class GlobalManager implements RemoteConnectionListener, UserListGUIEventListener, WindowListener, LogInListener, UserListChangesListener{
    private UserListWindow userListWindow ;
    private LoggingWindow logWindow;
    private BroadcastServer bs;
    private ConversationServer cs;
    private int maxConvAllowed = 1;
  //  private User localUser;
   // private SystemState ss ;

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
        logWindow = new LoggingWindow("Chat System - Log in");
        this.logWindow.addLogInListener(this);

     //   this.localUser = new User("");
        this.bs = new BroadcastServer(this);
        bs.addLogInListener(this);
        this.cs  = new ConversationServer(this);
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

    public void displayHistoryRequestFromGUI(String userid){
        //TODO       cs.getConversation(bs.getUserFromID(userid));
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
