package clavardage;


import sun.awt.WindowClosingListener;
import sun.reflect.annotation.ExceptionProxy;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;


public class ConversationManager extends Thread implements NewMessageToSendListener, WindowListener {

    private Socket sock;
    private BufferedReader in;
    private BufferedWriter out;
    private Conversation conv;
    private ChatWindow window;

    public ConversationManager(){
        //  this.window.addWindowListener(this);
    }
    public ConversationManager(User remote, User local){
      //  this.window.addWindowListener(this);
        this.conv = new Conversation(remote,local);
        this.window = new ChatWindow("-- You are speaking to "+remote.getUsername() +"--",remote.getUsername(),local.getUsername());
    }
    public ConversationManager(Socket sock){
        this.sock = sock ;
        //this.window.addWindowListener(this);
    }
    public void windowDeactivated(WindowEvent e){}

    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){
    }
    public void windowIconified(WindowEvent e){    }
    public void windowOpened(WindowEvent e){}
    public void windowClosing(WindowEvent e) {
        sendMessage("--end--string--");
        try {
            sock.close();
        } catch (Exception i) {
            System.out.println("ConvMan failed closin socket :  " + i.toString());
        }
    }
    public void windowClosed(WindowEvent e){
    }


    public String getRemoteUserID() { return conv.getRemoteID(); }

    public void refreshRemoteUsername(String remoteUsername) {
        this.window.refreshRemoteUsername(remoteUsername);
        this.conv.setRemoteID(remoteUsername);
    }


    public void NewMessageToSend(NewMessageToSendEvent evt){
        sendMessage(evt.msg);
    }
    public void closeConversation()
    {
        try {
            System.out.println("Closing communication");
            this.sock.close();
            this.window.displayInfo("Remote User a quitté la conv");
        }
        catch (Exception e) {
            System.out.println("Conv manager couldn't close socket : " + e.toString());
        }

    }
    public void acceptConvo(Socket socket){
        try {
            System.out.println("Accepting convo on socket :" + socket.toString());
            this.sock=socket;
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (Exception e) {
            System.out.println("Failed accepting convo:" + e.toString());
        }
        this.start();
    }

   /* public void initConvo(String ip, int port, Socket sock){
        try {
            System.out.println("Accepting convo from socket " + ip + " "+ port);
            this.sock = sock ;
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //TODO retrieve correct userid
        } catch (Exception e) {
            System.out.println("Failed initiating conversation :" + e.toString());
        }
        this.start();
    }*/

    public void initConvo(String ip, int port){
        try {
            System.out.println("Initiating convo from socket " + ip + " "+ port);
            String newip= ip.split("/")[1];
            this.sock = new Socket(newip, port);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //TODO retrieve correct userid
                    } catch (Exception e) {
            System.out.println("Failed initiating conversation :" + e.toString());
        }
        this.start();
    }
    private void sendMessage(String mess)
    {
        try {
            out.write(mess + "\n");
            out.flush();
            conv.addMessage(mess,true);
        }
        catch (Exception e) {
            System.out.println("Couldn't send message :" + e.toString());
        }
    }

    private void receiveAndStoreMessage(){   //BLOQUANTE
        Boolean keepgoing = true ;

        while (keepgoing){
            try {
        String textmess = in.readLine();
        if (!(textmess==null)) {
                    if (!(textmess.equals("--end--string--"))) {
                      //  Message mess = new Message(textmess,false);
                        conv.addMessage(textmess,false);
                        // DateFormat dateFormat  = new SimpleDateFormat("DD/MM HH:MM:SS") ;
                        // String date = dateFormat.format(mess.getDate());
                        window.displayReceivedMessage(textmess);
                    } else {
                        // if(textmess.equals("--end--string--")){
                        keepgoing = false;
                        closeConversation();
                    }
                }
            } catch (Exception e) {
             System.out.println("Coulnd't read message ____:" + e.toString());
            }
        }
    }

    /*private String receiveMessage(){   //BLOQUANTE
        String c ="" ;
        try {
        {

                c = in.readLine();

        }while(c.equals(""));
        } catch (Exception e) {
            System.out.println("Coulnd't read message :" + e.toString());
        }
        return c;
    }*/

    public void run(){


        this.window.setVisible(true);
        this.window.addWindowListener(this);
        window.addNewMessageToSendListener(this);

        ////////////////////////TEST LOCAL COMMUNICATION///////////////////////////////

        while(true) {
            try{
                receiveAndStoreMessage();
        } catch (Exception e) {
            System.out.println("Error on conversation " + e.toString() );
        }
        }
       /*
       try{
            System.out.println("Sending ping...");
            sendMessage("Ping");
            try {
                sleep(2000);
            }
            catch(Exception e) {
                System.out.println("Convo couldn't sleep : " + e.toString());
            }
           // String c = receiveMessage();
          //  System.out.println("ConvMan received : "+ c);
            receiveAndStoreMessage();
            System.out.println("ConvMan closing communication");
            closeConversation();
        } catch (Exception e) {
            System.out.println("Error on conversation " + e.toString() );
        }
      ////////////////////////TEST LOCAL COMMUNICATION///////////////////////////////

       /*try{
            System.out.println("Sending ping...");
            sendMessage("Ping");
            try {
                sleep(2000);
            }
            catch(Exception e) {
                System.out.println("Convo couldn't sleep : " + e.toString());
            }
            String c = receiveMessage();
            System.out.println("ConvMan received : "+ c);
            System.out.println("ConvMan closing communication");
            closeConversation();
        } catch (Exception e) {
            System.out.println("Error on conversation " + e.toString() );
        }*/


    }

}
