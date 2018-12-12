package clavardage;

import java.util.HashMap;

public class SystemState {

    private int currentConversations;
    private HashMap<String,User> onlineUsers;

    public HashMap<String, User> getOnlineUsers() {
        return onlineUsers;
    }

    public int getCurrentConversations() {
        return currentConversations;
    }

    public void setOnlineUsers(HashMap<String, User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public void setCurrentConversations(int currentConversations) {
        this.currentConversations = currentConversations;
    }

}
