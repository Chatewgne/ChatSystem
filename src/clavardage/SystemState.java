package clavardage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SystemState {

    private int currentConversations;
    //private ArrayList<User> onlineUsers;
    private HashMap<String,User> onlineUsers;

    public SystemState(){
        this.onlineUsers = new HashMap<String, User>();
        currentConversations = 0;
    }

    public User getUser(int i){
        return onlineUsers.get(i);
    }
    public int getCurrentConversations() {
        return currentConversations;
    }
    public void setCurrentConversations(int i)
    {
        this.currentConversations = i;
    }

    public void addOnlineUser(String id,User us) {
        onlineUsers.put(id, us);
        System.out.println("Added user : " + us.getID() + us.getUsername() + " available on host : " + us.getIP() );
    }
    public void removeOnlineUser(User us){
        onlineUsers.remove(us);
    }
    public void incrementConversationCount(){
        this.currentConversations++;
    }
    public void decrementConversationCount(){
        this.currentConversations--;
    }

    public String toString(){ //2:192.1.1.0:20181412184312:Ptiteigne:194.6.1.2:20180501123241:Chatewgne
        String str = Integer.toString(currentConversations);
        User user ;
        Iterator entries = onlineUsers.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry entry = (Map.Entry)entries.next();
            user = (User) entry.getValue();
            str = str + ":"+ user.getIP() + ":" + user.getID() + ":"+ user.getUsername();
        }

        return str;
    }
}
