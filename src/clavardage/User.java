package clavardage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class User {

    private String id ;
    private String username ;
    private String ip;


    public User(String id, String username, String ip){
        this.id = id ;
        this.username = username;
        this.ip = ip;
    }
    public User(String id, String username){
        this.id = id ;
        this.username = username;
    }
    public User(){
    }


    public String getIP(){
        return this.ip;
    }

    public User(String username){
        this.username = username;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        this.id = dateFormat.format(date);
    }

    public String getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public void setID(String ID) {
        this.id = ID;
    }

    public void setUsername(String username){
        this.username = username;
    }

}
