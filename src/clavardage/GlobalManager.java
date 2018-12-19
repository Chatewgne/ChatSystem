package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class GlobalManager implements RemoteConnexionListener, UserListGUIEventListener, WindowListener, LogInListener, UserListChangesListener{
    private UserListWindow userListWindow ;
    private LoggingWindow logWindow;
    private BroadcastServer bs;
    private ConversationServer cs;
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
        this.cs  = new ConversationServer();
        cs.addRemoteConnexionListener(this);
    }

    public void remoteDisconnection(){
        System.out.println("GlobalManager detected disconnection");
        userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
    }


    @Override
    public void remoteConnexion(RemoteConnexionEvent evt) {
        Socket sock = evt.sock;
        String remote = bs.getUserFromIP(sock.getInetAddress().toString()).getUsername();
        cs.acceptConv(remote,bs.getLocalUserame(),sock);
    }
    public void changedLocalUsername(String e){
        bs.localUsernameChanged(e);
        userListWindow.refreshNicknameLabel(e);
    }
    public void changedRemoteUsername(String id, String username){
        userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
    }

    public void newNicknameRequestFromGUI() {
        logWindow.start(false);

    }
    public void loggedIn(LogInEvent logged){
        bs.loggedIn(logged);
        this.userListWindow = new UserListWindow(bs.getLocalUserame());
        this.userListWindow.addUserListGUIEventListener(this);
        this.userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
        this.userListWindow.addWindowListener(this);
    }
    public void sessionRequestFromGUI(String userID){
        User u = bs.getUserFromId(userID) ;
        cs.requestNewConversation(u, bs.getLocalUserame());
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
