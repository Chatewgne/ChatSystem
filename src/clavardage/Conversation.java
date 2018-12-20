package clavardage;
import java.util.ArrayList;

public class Conversation {

    private String remoteID;
    private String myID;
    private String currentRemoteNickname;
    private String currentLocalNickname;
    private ArrayList<Message> messages;

    public Conversation(User remote, User local){
        this.remoteID = remote.getID();
        this.myID = local.getID();
        this.currentRemoteNickname =  remote.getUsername();
        this.currentLocalNickname = local.getUsername();
        this.messages = new ArrayList<Message>();
    }

    public void setRemoteID(String remoteID) {
        this.remoteID = remoteID;
    }

    public void setCurrentRemoteNickname(String remoteUsername){ this.currentRemoteNickname = remoteUsername; }


    public void addMessage(String mess, Boolean sent) {
        if (sent){
        this.messages.add(new Message(mess,currentLocalNickname,currentRemoteNickname));
        } else{
            this.messages.add(new Message(mess,currentRemoteNickname, currentLocalNickname));
        }

    }

    public String getRemoteID() {
        return remoteID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }



}
