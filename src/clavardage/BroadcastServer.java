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

    //TODO where is the localUser meant to be between here and GlobalManager
    public BroadcastServer(LoggingWindow win) {
        win.addLogInListener(this);
        this.in = new byte[256];
        this.out = new byte[256];
        try {
            this.socket = new DatagramSocket(4321);
        } catch (Exception e) {
            System.out.println("Error in BroadCast Server couldn't create socket : " + e.toString());
        }
    }

    public void loggedIn(LogInEvent logged){
        localUser = new User(logged.name);
        broadcastLocalConnection(localUser);
        localUser.addLocalUsernameChangedListener(this);
    }

    public void broadcastLocalConnection(User u) {
        broadcastMessage("CO:" + u.getID() + ":" + u.getUsername());
    }

    public void broadcastLocalChanged(User u) {
        broadcastMessage("CH:" + u.getID() + ":" + u.getUsername());
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


        String[] str = receive.split(":");
        if (str[0].equals("CO")) { //TODO IF someone new connected, send them the info about the system
            sendMessage("IN:" + system.toString(), source);
        } else if (str[0].equals("CH")) { //TODO if someone changed name
        } else if (str[0].equals("QU")) {//TODO if someone disconnected
        } else if (str[0].equals("IN")) {//TODO if someone is responding with some information

        } else {
            System.out.println("Recu UDP datagram " + receive);
        }
        } catch (Exception e) {
            System.out.println("Couldn't receive datagram packet : " + e.toString());
        }
        System.out.println("Recu UDP datagram " + receive);
        return receive;
    }

    private void sendMessage(String mess, InetAddress adr){
        out = mess.getBytes();
        try{
            DatagramPacket packet = new DatagramPacket(out,out.length, adr,4321);
            socket.send(packet);
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












