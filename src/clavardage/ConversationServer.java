package clavardage;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConversationServer extends Thread {
    private static ArrayList<ConversationManager> convos;
    private static ServerSocket servsock ;

    private static int findFreePort(){
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
    }

    public static void initServer(int port){
        try {
            servsock = new ServerSocket(port);
            convos = new ArrayList<ConversationManager>();
        } catch (Exception e) {
            System.out.println("Conversation Server error : "+ e.toString());
        }
    }

    public static void waitForConnection(){
        try {
            System.out.println("Waiting for connection on :" +servsock.toString());
            Socket sock = servsock.accept();
            convos.add(new ConversationManager());
            System.out.println("Accepting connection from " + sock.toString());
            convos.get(convos.size()-1).acceptConvo(sock);
        }
        catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }

    }
    public static void openNewConversation(User remoteuser, String mynickname){
        ConversationManager convman = new ConversationManager(remoteuser.getUsername(),mynickname);
        convman.initConvo(remoteuser.getIP(),servsock.getLocalPort());
        convos.add(convman);
       // convos.get(convos.size()-1).initConvo(user.getIP(),servsock.getLocalPort());
    }


    private static void closeConversation(int Num){
        convos.get(Num).closeConversation();
        convos.remove(Num);
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
