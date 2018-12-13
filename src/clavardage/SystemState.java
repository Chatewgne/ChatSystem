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

    public int getCurrentConversations() {
        return currentConversations;
    }

    public void addOnlineUser(String adr,User us) {
        onlineUsers.put(adr, us);
        System.out.println("Added user : " + us.getID() + us.getUsername() + " available on host : " + adr );
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
}
