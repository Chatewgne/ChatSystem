package clavardage;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Conversation {

    private String remoteID;
    private String myID;
    private String currentRemoteNickname;
    private String currentLocalNickname;
    private ArrayList<Message> messages;
    private Connection mysql ;

    public Conversation(){}

    public Conversation(User remote, User local, Connection mysql){
        this.remoteID = remote.getID();
        this.myID = local.getID();
        this.currentRemoteNickname =  remote.getUsername();
        this.currentLocalNickname = local.getUsername();
        this.messages = new ArrayList<Message>();
        this.mysql = mysql;
    }


    public void setLocalUsername(String name) {
        this.currentLocalNickname = name;
    }

    public void setCurrentRemoteNickname(String remoteUsername){ this.currentRemoteNickname = remoteUsername; }


    public void addMessage(String mess, Boolean sent) {
        if (sent){
        this.messages.add(new Message(mess,currentLocalNickname,currentRemoteNickname));
        } else{
            this.messages.add(new Message(mess,currentRemoteNickname, currentLocalNickname));
        }
        try {
            Date date = new Date();
            Statement statement = mysql.createStatement();
            int res = statement.executeUpdate("INSERT INTO chat_system.Messages (remoteID, date, received, content) VALUES ("+remoteID+","+ date+", "+(!(sent))+", "+mess+");");
            if (!(res == 0)) {
                System.out.println("Couldn't write to database");
            }
        }catch (Exception e){
            System.out.println("Couldn't store message :"+ e.toString());
        }

    }

    public String getRemoteID() {
        return remoteID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }



}
