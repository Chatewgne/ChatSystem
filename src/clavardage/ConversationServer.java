package clavardage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConversationServer extends Thread{
    private static ArrayList<ConversationManager> Convos;
    private static ServerSocket servsock ;
    public int freeport1; //TODO this has to leave.
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
   /* public static void setPort(int port){
        this.freeport1=port;
    }
    public static int getPort(){
        return this.freeport1;
    }*/
    public static void initServer(int port){
        try {
            servsock = new ServerSocket(port);
            Convos = new ArrayList<ConversationManager>();
        } catch (Exception e) {
            System.out.println("Conversation Server error : "+ e.toString());
        }
    }
    public static void waitForConnection(){
        try {
            System.out.println("Waiting for connection on :" +servsock.toString());
            Socket sock = servsock.accept();
            Convos.add(new ConversationManager());
            System.out.println("Accepting connection from " + sock.toString());
            Convos.get(Convos.size()-1).acceptConvo(sock);
        }
        catch (Exception e) {
            System.out.println("Conv server error : " + e.toString());
        }

    }
    private static void openNewConversation(Socket sock){
        Convos.add(new ConversationManager(sock));
        //TODO raise new conversation opened
    }

    private static void closeConversation(int Num){
        Convos.remove(Num);
        //TODO raise conversation closed
    }

    public static void main(String[] args) {
        try {
            int freeport = findFreePort();
            if (freeport == -1) throw new Exception("Free port error in Conversation Manager");
            initServer(freeport);
           // this.setPort(freeport1);
            Convos.add(new ConversationManager());
          //  Convos.get(0).initConvo("localhost",freeport);
            waitForConnection();
           // Convos.get(0).run();
           // Convos.get(1).run();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
