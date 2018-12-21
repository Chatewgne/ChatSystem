package clavardage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String content;
    private Date date;
    private String from ;
    private String to ;

    public Message(String content, String from, String to){
   // public Message(String content){
        this.content = content;
        this.from = from;
        this.to =to;
       // DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = new Date();
      //  this.date = dateF.format(d);
    }

    public String getContent() {
        return content;
    }


    public Date getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
