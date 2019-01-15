package clavardage;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    // Object managing the prepared statement saving the messages into the database
    private PreparedStatement preparedStatement;

    public Conversation(){}

    public Conversation(User remote, User local, Connection mysql){
        this.remoteID = remote.getID();
        this.myID = local.getID();
        this.currentRemoteNickname =  remote.getUsername();
        this.currentLocalNickname = local.getUsername();
        this.messages = new ArrayList<Message>();
        this.mysql = mysql;

        try {
            // Preparing the statement saving the messages
            preparedStatement = mysql.prepareStatement(
                    "INSERT INTO chat_system.messages (remoteID, date, received, content) VALUES (?, ?, ?, ?);"
            );
        }
        catch (SQLException sqlException){
            System.out.println("Could't bake the prepared statement : " + sqlException);
        }

    }


    public void setLocalUsername(String name) {
        this.currentLocalNickname = name;
    }

    public void setCurrentRemoteNickname(String remoteUsername){ this.currentRemoteNickname = remoteUsername; }


    public void addMessage(String mess, Boolean sent) {

        Date date = new Date();

        if (sent){
        this.messages.add(new Message(mess,myID,remoteID,currentLocalNickname,date));
        } else{
            this.messages.add(new Message(mess,remoteID, myID,currentRemoteNickname,date));
        }
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime= sdf.format(date);
            // Setting the values inside the preparedStatement
            preparedStatement.setString(1, remoteID);
            preparedStatement.setString(2, currentTime);
            preparedStatement.setBoolean(3, !sent);
            preparedStatement.setString(4, mess);

            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Couldn't store message :"+ e.toString());
        }

    }

    public String getRemoteID() {
        return remoteID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> getMessages(String remoteID, String myid) {
        getMessagesFromBDD(myid,remoteID);
        return messages;
    }

    public void getMessagesFromBDD(String myID, String remoteID){
        try {
            Statement statement = mysql.createStatement();
            ArrayList messages = new ArrayList<Message>();
            ResultSet res = statement.executeQuery("SELECT * FROM messages WHERE remoteID='"+remoteID+"' ORDER BY date");


            String myNickname = getNicknameWithIDFromDB(myID);
            String remoteNickname = getNicknameWithIDFromDB(remoteID);


            while (res.next()) {
                String mess = res.getString(4);
                Date date = res.getTimestamp(2);
                Boolean sent = res.getBoolean(3);
                if (sent){
                messages.add(new Message(mess,myID,myNickname,remoteID,date));
                }
                else{
                    messages.add(new Message(mess,remoteID,remoteNickname,myID,date));
                }
            }
            this.messages=messages;

        } catch (Exception e){
            System.out.println("Couldn't retrieve messages from BDD : "+e.toString());
        }
    }



    private String getNicknameWithIDFromDB(String userID){

        String nickname;

        try {
            Statement statement = mysql.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM users WHERE userid='" + userID+"'");

            if(res.next()){
                nickname = res.getString("nickname");
                System.out.println("Nickname found for this userID : " + userID + "is :" + nickname);
            }
            else{
                System.out.println("Couldn't find the nickname corresponding to this userID : " + userID);
                nickname = userID;
            }

        }
        catch (Exception e){
            System.out.println("Couldn't retrieve nickname from DB of " + userID +": " + e.toString());
            nickname = userID;
        }

        return nickname;

    }



}
