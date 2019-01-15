package clavardage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String content;
    private Date date;
    private String fromID ;
    private String fromNickname;
    private String toID ;

    public Message(String content, String fromID, String fromNickname, String toID,Date date){
        // public Message(String content){
        this.content = content;
        this.fromID = fromID;
        this.toID =toID;
        this.fromNickname = fromNickname;
      //  DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = date;
    }
    public String getContent() {
        return content;
    }


    public Date getDate() {
        return date;
    }

    public String getIDSender() { return fromID; }

    public String getSenderNickname() { return fromNickname; }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
