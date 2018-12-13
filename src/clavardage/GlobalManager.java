package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.URL;

public class GlobalManager implements WindowListener{
    private UserListWindow userListWindow ;
    private LoggingWindow logWindow;
    private BroadcastServer bs;
    private ConversationServer cs;
    private User localUser;
    private SystemState ss ;
    public GlobalManager(){
        logWindow = new LoggingWindow("Chat System - Log in");
        this.localUser = new User(""); //TODO retrieve nickname on login
        this.bs = new BroadcastServer(logWindow);
        this.cs  = new ConversationServer();
    }

    public void start(){
      //  cs.run();
        logWindow.start();
        bs.run();

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
}