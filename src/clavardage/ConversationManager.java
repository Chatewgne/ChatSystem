package clavardage;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;


public class ConversationManager extends Thread implements LogInEventGenerator, NewMessageToSendListener, WindowListener {

    private Socket sock;
    private BufferedReader in;
    private BufferedWriter out;
    private Conversation conv;
    private ChatWindow window;

    private boolean keepgoing;
    private LogInListener list ;

    public ConversationManager(){
        //  this.window.addWindowListener(this);
    }
    public ConversationManager(User remote, User local, LogInListener list){
      //  this.window.addWindowListener(this);
        this.conv = new Conversation(remote,local);
        this.window = new ChatWindow("-- You are speaking to "+remote.getUsername() +"--",remote.getUsername(),local.getUsername());
        addLogInListener(list);
        keepgoing = true;
    }
    public ConversationManager(Socket sock){
        this.sock = sock ;
        //this.window.addWindowListener(this);
    }

    public void addLogInListener(LogInListener list){
        this.list = list;
    }
    public void windowDeactivated(WindowEvent e){}

    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){
    }
    public void windowIconified(WindowEvent e){    }
    public void windowOpened(WindowEvent e){}
    public void windowClosing(WindowEvent e) {
        keepgoing = false;

        sendEnd();
        try {
            in.close();
            out.close();
            sock.close();
        } catch (Exception i) {
            System.out.println("ConvMan failed closin socket :  " + i.toString());
        }
    }
    public void windowClosed(WindowEvent e){
    }

    public Conversation getConv(){
        return conv;
    }

    public String getRemoteUserID() { return conv.getRemoteID(); }

    public void refreshRemoteUsername(String remoteUsername) {
        this.window.refreshRemoteUsernameinChatWindow(remoteUsername);
        this.conv.setCurrentRemoteNickname(remoteUsername);
    }

    public void setLocalUsername(String localUsername){
        this.conv.setLocalUsername(localUsername);
        this.window.refreshLocalUsernameInChatWindow(localUsername);
    }


    public void NewMessageToSend(NewMessageToSendEvent evt){
        sendMessage(evt.msg);
    }
    public void closeConversation()
    {
        try {
            keepgoing = false;
            System.out.println("Closing communication");
            in.close();
            out.close();
            this.sock.close();

            //
            this.window.notifyRemoteUserLeftInGUI();
        }
        catch (Exception e) {
            System.out.println("Conv manager couldn't close socket : " + e.toString());
        }

    }
    public void acceptConvo(Socket socket){
        try {
            keepgoing = true;
            System.out.println("Accepting convo on socket :" + socket.toString());
            this.sock=socket;
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (Exception e) {
            System.out.println("Failed accepting convo:" + e.toString());
        }
       // this.start();
    }


    public void reopen(){
        this.window.setVisible(true);
    }
   /* public void initConvo(String ip, int port, Socket sock){
        try {
            System.out.println("Accepting convo from socket " + ip + " "+ port);
            this.sock = sock ;
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

        } catch (Exception e) {
            System.out.println("Failed initiating conversation :" + e.toString());
        }
        this.start();
    }*/

    public void initConvo(String ip, int port){
        try {
            keepgoing = true;
            System.out.println("Initiating convo from socket " + ip + " "+ port);
            String newip= ip;
            if (ip.contains("/")){newip= ip.split("/")[1];}
            this.sock = new Socket(newip, port);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    } catch (Exception e) {
            System.out.println("Failed initiating conversation :" + e.toString());
        }
        //this.start();
    }

    private void sendEnd(){
        try {
            out.write("--end--string--" + "\n");
            out.flush();
        }
        catch (Exception e) {
            System.out.println("Couldn't send END message :" + e.toString());
        }
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
                        list.conversationClosed();
                        closeConversation();
                    }
                }
            } catch (Exception e) {
             System.out.println("Coulnd't read message ____:" + e.toString());
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
           if (keepgoing) {
               try {
                   receiveAndStoreMessage();
               } catch (Exception e) {
                   System.out.println("Error on conversation " + e.toString());
               }
           }
           //  }
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

}
