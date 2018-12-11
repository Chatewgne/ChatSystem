package clavardage;

import javax.swing.event.EventListenerList;

public class BroadcastServer implements LocalUsernameChangedListener {

    private User localUser;
    protected EventListenerList listenerList = new EventListenerList();
    public BroadcastServer(User user){
        this.localUser = user;
        this.localUser.addLocalUsernameChangedListener(this);
    }
    public void localUsernameChanged(LocalUsernameChangedEvent e){
        User whochanged = (User)e.getSource();
        System.out.println("Broadcast server detected local username changed to : "+ whochanged.getUsername());
    }
}
