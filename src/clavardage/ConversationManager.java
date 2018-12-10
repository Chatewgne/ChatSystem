package clavardage;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

public class ConversationManager extends Thread {

    private Socket sock;
    private BufferedReader in;
    private BufferedWriter out;

    public ConversationManager(){};

    public ConversationManager(Socket sock){
        this.sock = sock ;
    }

    public void closeConversation()
    {
        try {
            this.sock.close();
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
            System.out.println("Conversation exception :" + e.toString());
        }
        this.run();
    }
    public void initConvo(String ip, int port){

        try {
            System.out.println("Initiating convo from socket " + ip + " "+ port);
            this.sock = new Socket(ip, port);
            this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    } catch (Exception e) {
            System.out.println("Fail initiation conv Conversation exception :" + e.toString());
        }
    }

    public void run(){
        try{
            System.out.println("Conv sending ping");
            // EQUIVALENT AUX DEUX LIGNES : out.println("truc");
            this.out.write("Ping"+"\n");
            out.flush();

            try {
                sleep(2000);
            }
            catch(Exception e){
                System.out.println("Convo couldn't sleep : " + e.toString());
            }
            System.out.println("Conv signaling end");
            out.write("end"+"\n");
            out.flush();
            try {
                sleep(2000);
            }
            catch(Exception e){
                System.out.println("Convo couldn't sleep : " + e.toString());
            }
            System.out.println("Conv got : "+ in.readLine());
            System.out.println("Conv closing communication");
            sock.close();
        } catch (java.io.IOException e) {
            System.out.println("Error on conversation " + e.toString() );
        }


    }

}
