package clavardage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ConversationServer extends Thread implements RemoteConnexionGenerator, UserListGUIEventGenerator {
    private static ArrayList<ConversationManager> convos;
    private static ServerSocket servsock ;
    private RemoteConnexionListener listener ;
    private UserListGUIEventListener list;

   /* private static int findFreePort(){
        int res = -1 ;
        for (int o = 1024; o<5000; o++){
            try {
                Socket test = new Socket("localhost", o);
                //System.out.println("eeeh");
            } catch (Exception e){
                System.out.println("Found free port :" + o);
                res = o ;
                break;
            }
        }
        return res;
    }*/

   public void addUserListGUIEventListener(UserListGUIEventListener list){
       this.list = list ;
   }


    public static void initServer(int port){
        try {
            servsock = new ServerSocket(port);
            convos = new ArrayList<ConversationManager>();
        } catch (Exception e) {
            System.out.println("Conversation Server error : "+ e.toString());
        }
    }

    public void waitForConnection(){
        try {
            System.out.println("Waiting for connection on :" +servsock.toString());
            Socket sock = servsock.accept();
            convos.add(new ConversationManager());
            System.out.println ("Accepting connection from " + sock.toString());
            convos.get(convos.size()-1).acceptConvo(sock);
        }
        catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }

    }

    public void waitForConnection2() {
        try {
            System.out.println("Waiting for connection on :" + servsock.toString());
            Socket sock = servsock.accept();
            listener.remoteConnexion(new RemoteConnexionEvent(this, sock));
        } catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }
    }

public void acceptConv(String remote, String local, Socket sock) {
        try{
            convos.add(new ConversationManager(remote,local));
            System.out.println ("Accepting connection from " + sock.toString());
            convos.get(convos.size()-1).acceptConvo(sock);
        }
        catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }

    }


    public static void requestNewConversation(User remoteuser, String mynickname){
        ConversationManager convman = new ConversationManager(remoteuser.getUsername(),mynickname);
        convman.initConvo(remoteuser.getIP(),servsock.getLocalPort());
        convos.add(convman);
       // convos.get(convos.size()-1).initConvo(user.getIP(),servsock.getLocalPort());
    }

    @Override
    public void addRemoteConnexionListener(RemoteConnexionListener listener) {
        this.listener = listener;
    }

    //todo correct connection closure
    private static void closeConversation(int Num){
        convos.get(Num).closeConversation();
        convos.remove(Num);
    }




    // Updates the conversation with the user who changed his nickname
    public void updateRemoteNicknameInConvo(String id, String username){

        Iterator<ConversationManager> itrConvos = convos.iterator();
        ConversationManager actualConvo;

        boolean conversationFound = false;

        // Iteration on the conversation manager list until we find a corresponding conversation
        while(itrConvos.hasNext() && !conversationFound){

            actualConvo = itrConvos.next();

            if(id.equals(actualConvo.getRemoteUserID())){



            }

        }


    }



    // public static void main(String[] args) {
    public void run(){
        try {
            initServer(4321);
            System.out.println("Conversation Server running...");
            while (true) {
                waitForConnection2();
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }


}
