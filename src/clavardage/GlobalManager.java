package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){ }
    public void windowIconified(WindowEvent e){    }
    public void windowOpened(WindowEvent e){}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e){ }
}
