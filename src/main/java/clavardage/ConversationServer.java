package clavardage;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

public class ConversationServer extends Thread implements RemoteConnectionEventGenerator, UserListGUIEventGenerator {
    private static ArrayList<ConversationManager> convos;
    private static ServerSocket servsock ;
    private RemoteConnectionListener listener ;
    private UserListGUIEventListener list;
    private LogInListener listener2;
    private Connection mysql;

       public void addUserListGUIEventListener(UserListGUIEventListener list){
       this.list = list ;
   }


   public ConversationServer(LogInListener list, Connection mysql)
   {
       this.mysql = mysql;
       this.listener2 = list;
   }

    private static void initServer(int port){
        try {
            servsock = new ServerSocket(port);
            convos = new ArrayList<ConversationManager>();
        } catch (Exception e) {
            System.out.println("Conversation Server error : "+ e.toString());
        }
    }

  /*  public void waitForConnection(){
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

    }*/

    private void waitForConnection() {
        try {
            System.out.println("Waiting for connection on :" + servsock.toString());
            Socket sock = servsock.accept();
            listener.remoteConnection(new RemoteConnectionEvent(this, sock));
        } catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }
    }

public void acceptConv(User remote, User local, Socket sock) {
        try {
            if (existsConversationWith(remote)) {
                System.out.println("found pre-existing conv with "+ remote.getID());
                getConversationManager(remote).acceptConvo(sock);
               getConversationManager(remote).reopen();
               // getConversationManager(remote).start();
                System.out.println("Accepting connection from " + sock.toString());
            } else {
                System.out.println("creating conv with "+ remote.getID());
                convos.add(new ConversationManager(remote, local, listener2,mysql));
                System.out.println("Accepting connection from " + sock.toString());
                convos.get(convos.size() - 1).acceptConvo(sock);
                convos.get(convos.size()-1).start();
            }
        }
        catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }


    }



    public void requestNewConversation(User remoteuser, User local){
        if (existsConversationWith(remoteuser)){
            System.out.println("found pre-existing conv with "+ remoteuser.getID());
            getConversationManager(remoteuser).initConvo(remoteuser.getIP(),servsock.getLocalPort());
            getConversationManager(remoteuser).reopen();
            //getConversationManager(remoteuser).start();
        } else {
            System.out.println("creating conv with "+ remoteuser.getID());
            ConversationManager convman = new ConversationManager(remoteuser, local, listener2,mysql);
            convman.initConvo(remoteuser.getIP(), servsock.getLocalPort());
            convos.add(convman);
            convman.start();
        }
    }

    @Override
    public void addRemoteConnectionListener(RemoteConnectionListener listener) {
        this.listener = listener;
    }

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
                actualConvo.refreshRemoteUsername(username);
                conversationFound = true;
            }
        }
    }


    public void updateLocalNicknameInConvo(String username){

        Iterator<ConversationManager> itrConvos = convos.iterator();
        ConversationManager actualConvo;

        // Iteration on the conversation manager list until we find a corresponding conversation
        while(itrConvos.hasNext()){

            actualConvo = itrConvos.next();
            actualConvo.setLocalUsername(username);


        }

    }

    private boolean existsConversationWith(User remote){
        Iterator<ConversationManager> itrConvos = convos.iterator();
        ConversationManager actualConvo = new ConversationManager();
        boolean conversationFound = false;

        // Iteration on the conversation manager list until we find a corresponding conversation
        while(itrConvos.hasNext() && !conversationFound){

            actualConvo = itrConvos.next();

            if(remote.getID().equals(actualConvo.getRemoteUserID())){
                conversationFound = true;
            }
        }
        return conversationFound;
    }


    public ConversationManager getConversationManager(User remote) {
            Iterator<ConversationManager> itrConvos = convos.iterator();
            ConversationManager actualConvo = null;

            // Iteration on the conversation manager list until we find a corresponding conversation
            while (itrConvos.hasNext()) {

                actualConvo = itrConvos.next();

                if (remote.getID().equals(actualConvo.getRemoteUserID())) {
                    break;
                }
            }
            return actualConvo;
    }

    public ArrayList<Message> getMessagesWith(String remoteuserID,String myid){

        Iterator<ConversationManager> itrConvos = convos.iterator();
        ConversationManager actualConvo;

        ArrayList<Message> messages = null;

        // Iteration on the conversation manager list until we find a corresponding conversation
        while (itrConvos.hasNext() && messages == null) {

            actualConvo = itrConvos.next();

            if (actualConvo.getRemoteUserID() == remoteuserID) {
                messages = actualConvo.getConv().getMessages(remoteuserID, myid);
            }
        }
        return messages;
    }


    // public static void main(String[] args) {
    public void run(){
        try {
            initServer(4321);
            System.out.println("Conversation Server running...");
            while (true) {
                waitForConnection();
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }


}
