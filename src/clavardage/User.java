package clavardage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class User implements LocalUsernameChangedGenerator {
//TODO is abstract correct ? Why ?
    private String id ;
    private String username ;
    private String ip;
    private ArrayList<Conversation> convos ;

    public User(String id, String username, String ip){
        this.id = id ;
        this.username = username;
        this.ip = ip;
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

    public ArrayList<Conversation> getConvos() {
        return convos;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public void setUsername(String username) {
        this.username = username;
        this.FireLocalUsernameChangedEvent(new LocalUsernameChangedEvent(this));
    }

    public void setConvos(ArrayList<Conversation> convos) {
        this.convos = convos;
    }

    private LocalUsernameChangedListener localUsernameChangedListener ;
    private void FireLocalUsernameChangedEvent(LocalUsernameChangedEvent e){
            localUsernameChangedListener.localUsernameChanged(e);
    }
    public void addLocalUsernameChangedListener(LocalUsernameChangedListener listener){
        localUsernameChangedListener = listener;
    }

}
