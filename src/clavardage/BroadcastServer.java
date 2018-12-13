package clavardage;

import javax.swing.event.EventListenerList;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;

public class BroadcastServer extends Thread implements LocalUsernameChangedListener, LogInListener {

    private SystemState system;
    private User localUser;
    private DatagramSocket socket;
    private byte[] out;
    private byte[] in;
    private String myip;

    //TODO where is the localUser meant to be between here and GlobalManager
    public BroadcastServer(LoggingWindow win) {
        system = new SystemState();
        win.addLogInListener(this);
        this.in = new byte[256];
        this.out = new byte[256];
        this.myip =  getMyip();
        try {
            this.socket = new DatagramSocket(4321);
        } catch (Exception e) {
            System.out.println("Error in BroadCast Server couldn't create socket : " + e.toString());
        }
    }

    public void loggedIn(LogInEvent logged){
        try {
            localUser = new User(logged.name);
            broadcastLocalConnection(localUser);
            localUser.addLocalUsernameChangedListener(this);
          //  system.addOnlineUser(myip, localUser);
        } catch (Exception e) {
            System.out.println("Error on broadcast : "+e.toString());
        }
    }

    private void broadcastLocalConnection (User u) { //broadcasts my arrival and returns my ip
        String str = "CO:" + u.getID() + ":" + u.getUsername() ;
        out = str.getBytes();
        try{
            DatagramPacket packet = new DatagramPacket(out,out.length, InetAddress.getByName("255.255.255.255"),4321);
            socket.send(packet);
            System.out.println("Send connection UDP datagram : " +str);
        } catch (Exception e) {
            System.out.println("Couldn't send UDP packet : "+e.toString());
        }
    }

    public void broadcastLocalChanged(User u) {
        broadcastMessage("CH:" + myip + ":" + u.getID() + ":" + u.getUsername());
    }
    //TODO interdire de mettre ":" dans son username

    public void localUsernameChanged(LocalUsernameChangedEvent e) {
        User whochanged = (User) e.getSource();
        System.out.println("Broadcast server detected local username changed to : " + whochanged.getUsername());
    }

    private String receiveMessage() {             //BLOQUANTE
        DatagramPacket packet = new DatagramPacket(in, in.length);
        InetAddress source ;
        String receive = "";
        try {
            socket.receive(packet);
            receive = new String(packet.getData(), 0, packet.getLength());
            source = packet.getAddress();
            System.out.println("Recu UDP datagram " + receive + "from host : " + source.toString() );
        if (!(source.toString()==myip)) {
            String[] str = receive.split(":");
            if (str[0].equals("CO")) { //if someone connected to the system
                if (str[1].equals(localUser.getID())){ //if this is my own connection packet, remember my IP adress
                    myip = source.toString();
                    System.out.println("My ip is : " + myip);
                }else {//if this is not my connection packet, send then information about the system
                    String answer = "IN:" + system.toString();
                    system.addOnlineUser(source.toString(), new User(str[1], str[2]));
                    sendMessage(answer, source);
                    System.out.println("Sent UDP datagram " + answer);
                }
            } else if (str[0].equals("CH")) { //TODO if someone changed name
            } else if (str[0].equals("QU")) {//TODO if someone disconnected
            } else if (str[0].equals("IN")) {//TODO if someone is responding with some information

            } else {
                System.out.println("~~~~~~~ Recu UDP datagram AU FORMAT INCONNU ~~~~~~~~ " + receive);
            }

        }
        } catch (Exception e) {
            System.out.println("Couldn't receive datagram packet : " + e.toString());
        }
        return receive;
    }

    private String getMyip(){
        String str = "empty_ip";
        InetAddress adr ;
        out = str.getBytes();
       try {
        DatagramPacket packet = new DatagramPacket(out,out.length,InetAddress.getByName("localhost"),4321);
        adr = packet.getAddress();
        str = adr.toString() ;
       } catch (Exception e) {
        System.out.println("Couldn't get my ip adress : "+e.toString());
    }
       return str;
    }


    private void sendMessage(String mess, InetAddress adr){
        out = mess.getBytes();
        try{
            DatagramPacket packet = new DatagramPacket(out,out.length, adr,4321);
            socket.send(packet);
            //System.out.println("Sent UDP datagram : "+mess);
        } catch (Exception e) {
            System.out.println("Couldn't send UDP packet : "+e.toString());
        }
}

    private void broadcastMessage(String str) {
        out = str.getBytes();
        try{
            DatagramPacket packet = new DatagramPacket(out,out.length, InetAddress.getByName("255.255.255.255"),4321);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Couldn't send UDP packet : "+e.toString());
        }
    }
    public void run(){
        try {
            while(true) {
                String str = receiveMessage();
            }
        } catch (Exception e) {
            System.out.println("Error in BroadcastServer : "+e .toString());
        }
        }
}












