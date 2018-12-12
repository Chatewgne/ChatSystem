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
            //TODO retrieve correct userid
            this.conv = new Conversation("First","Second");
        } catch (Exception e) {
            System.out.println("Failed accepting convo:" + e.toString());
        }
        this.run();
    }
    public void initConvo(String ip, int port){
        try {
            System.out.println("Initiating convo from socket " + ip + " "+ port);
            this.sock = new Socket(ip, port);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            //TODO retrieve correct userid
            this.conv = new Conversation("First","Second");
                    } catch (Exception e) {
            System.out.println("Failed initiating conversation :" + e.toString());
        }
    }
    private void sendMessage(String mess)
    {
        try {
            out.write(mess + "\n");
            out.flush();
        }
        catch (Exception e) {
            System.out.println("Coulnd't send message :" + e.toString());
        }
    }


    private void receiveAndStoreMessage(){   //BLOQUANTE
        Boolean keepgoing = true ;

        while (keepgoing){
        String textmess = receiveMessage();
        if (!(textmess.equals("--end--string--"))){
                Message mess = new Message(textmess);
                conv.addMessage(mess);
                // DateFormat dateFormat  = new SimpleDateFormat("DD/MM HH:MM:SS") ;
                // String date = dateFormat.format(mess.getDate());
                window.displayReceivedMessage(mess.getContent());
            } else {
           // if(textmess.equals("--end--string--")){
            keepgoing = false;
            closeConversation();
                    }
        }
    }

    private String receiveMessage(){   //BLOQUANTE
        String c ="" ;
        {
            try {
                c = in.readLine();
            } catch (Exception e) {
                System.out.println("Coulnd't read message :" + e.toString());
            }
        }while(c.equals(""));
        return c;
    }

    public void run(){

        this.window = new ChatWindow("<<<Your Username Here Soon>>");
        this.window.addWindowListener(this);
        window.addNewMessageToSendListener(this);
        //TODO : coherent window name (with remote username)

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
