package clavardage;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemState {

    private int currentConversations;
    private ArrayList<User> onlineUsers;


    public int getCurrentConversations() {
        return currentConversations;
    }

    public void addOnlineUser(User us) {
        onlineUsers.add(us);
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

    public String toString(){
        int people = onlineUsers.size() ;
        String str = Integer.toString(currentConversations);
        for (int i =0;i<people-1;i++){
            str = str +":"+ onlineUsers.get(i).getID() +":"+ onlineUsers.get(i).getUsername();
        }
        return str;
    }
}
