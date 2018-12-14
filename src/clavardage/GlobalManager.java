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

public class GlobalManager implements WindowListener,UserListGUIEventListener, LogInListener, UserListChangesListener{
    private UserListWindow userListWindow ;
    private LoggingWindow logWindow;
    private BroadcastServer bs;
    private ConversationServer cs;
  //  private User localUser;
   // private SystemState ss ;

    public GlobalManager(){
        logWindow = new LoggingWindow("Chat System - Log in");
        this.logWindow.addLogInListener(this);
     //   this.localUser = new User("");
        this.bs = new BroadcastServer(this);
        this.cs  = new ConversationServer();
    }

    public void newNicknameRequestFromGUI(){
    }

    public void loggedIn(LogInEvent logged){
        bs.loggedIn(logged);
        this.userListWindow = new UserListWindow(bs.getLocalUserame());
        this.userListWindow.addUserListGUIEventListener(this);
        this.userListWindow.refreshUserListInGUI(bs.getOnlineUsers());
    }
    public void sessionRequestFromGUI(String userID){
        User u = bs.getUserFromId(userID) ;
        cs.openNewConversation(u, bs.getLocalUserame());
    }

    public void start(){

        logWindow.start();
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

    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){ }
    public void windowIconified(WindowEvent e){    }
    public void windowOpened(WindowEvent e){}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e){ }

    @Override
    public void userListHasChanged(HashMap<String, User> onlineUsers) {
        this.userListWindow.refreshUserListInGUI(onlineUsers);
    }
}
