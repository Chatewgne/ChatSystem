package clavardage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SystemState implements UserListChangesEventGenerator{

    private int currentConversations;

    //private ArrayList<User> onlineUsers;
    private HashMap<String,User> onlineUsers;

    // The object listening to the user list changes (notified here by the SystemState)
    private ArrayList<UserListChangesListener> userListChangesListeners;

    public SystemState(){
        this.onlineUsers = new HashMap<String, User>();
        currentConversations = 0;
    }

    public int getCurrentConversations() {
        return currentConversations;
    }

    public void addOnlineUser(String adr,User us) {
        onlineUsers.put(adr, us);
        System.out.println("Added user : " + us.getID() + us.getUsername() + " available on host : " + adr );
        this.reportNewUserListToListeners(onlineUsers);
    }
    public void removeOnlineUser(User us){
        onlineUsers.remove(us);
        this.reportNewUserListToListeners(onlineUsers);
    }
    public void incrementConversationCount(){
        this.currentConversations++;
        this.reportNewUserListToListeners(onlineUsers);
    }
    public void decrementConversationCount(){
        this.currentConversations--;
        this.reportNewUserListToListeners(onlineUsers);
    }

    public String toString(){ //2:192.1.1.0:20181412184312:Ptiteigne:194.6.1.2:20180501123241:Chatewgne
        int people = onlineUsers.size() ;
        String str = Integer.toString(currentConversations);
        String adr;
        User user ;
        Iterator entries = onlineUsers.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry entry = (Map.Entry)entries.next();
            adr = (String) entry.getKey();
            user = (User) entry.getValue();
            str = str + ":"+ adr + ":" + user.getID() + ":"+ user.getUsername();
        }

        return str;
    }

    @Override
    public void addUserListChangesListener(UserListChangesListener listener) {
        this.userListChangesListeners.add(listener);
    }


    // Give the new user list to every listeners of the SystemState
    @Override
    public void reportNewUserListToListeners(HashMap<String, User> onlineUsers) {

        Iterator<UserListChangesListener> listeners = this.userListChangesListeners.iterator();

        while(listeners.hasNext()){
            listeners.next().userListHasChanged(onlineUsers);
        }
    }
}
