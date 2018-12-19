package clavardage;
import java.util.ArrayList;

public class Conversation {

    private String remoteID;
    private String myID;
    private ArrayList<Message> messages;

    public Conversation(String remoteID,String myID){
        this.remoteID = this.remoteID;
        this.myID = this.myID;
        this.messages = new ArrayList<Message>();
    }

    public void setRemoteID(String remoteID) {
        this.remoteID = remoteID;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    public void addMessage(Message message) {
        this.messages.add(message);

    }


    public String getMyID() {
        return myID;
    }

    public String getRemoteID() {
        return remoteID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }



}
