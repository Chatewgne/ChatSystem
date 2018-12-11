package clavardage;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Conversation extends Thread {

    private String id1 ;
    private String id2 ;
    private ArrayList<Message> messages;

    public Conversation(String id1,String id2){
        this.id1 = id1;
        this.id2 = id2;
        this.messages = new ArrayList<Message>();
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public void addMessage(Message message) {
        this.messages.add(message);

    }


    public String getId2() {
        return id2;
    }

    public String getId1() {
        return id1;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }



}
