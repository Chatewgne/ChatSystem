package clavardage;

import javax.swing.event.EventListenerList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;

public class BroadcastServer extends Thread implements LocalUsernameChangedListener {

    private SystemState system;
    private User localUser;
    private DatagramSocket socket;
    private byte[] out;
    private byte[] in ;

    public BroadcastServer(User user) {
        this.localUser = user;
        this.localUser.addLocalUsernameChangedListener(this);
        this.in = new byte[256];
        this.out = new byte[256];
        try {
            this.socket = new DatagramSocket(4321);
        } catch (Exception e) {
            System.out.println("Error in BroadCast Server couldn't create socket : " + e.toString());
        }
    }

    public void localUsernameChanged(LocalUsernameChangedEvent e) {
        User whochanged = (User) e.getSource();
        System.out.println("Broadcast server detected local username changed to : " + whochanged.getUsername());
    }

    private String receiveMessage() {             //BLOQUANTE
    DatagramPacket packet = new DatagramPacket(in, in.length);
    String receive = "";
    try {
            socket.receive(packet);
            receive = new String(packet.getData(),0,packet.getLength());

        } catch (Exception e)
        {
            System.out.println("Couldn't receive datagram packet : " + e.toString());
       }
        return receive;
    }

    private void sendMessage(String str, InetAddress adr) {
        out = str.getBytes();
        DatagramPacket packet = new DatagramPacket(out,out.length, adr,4321);
        try {
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Couldn't send UDP packet : "+e.toString());
        }
    }
    public void run(){
        try {
            System.out.println("Sending coucou");
            sendMessage("coucou", InetAddress.getByName("255.255.255.255"));
            System.out.println("Recu " + receiveMessage());
        }catch (Exception e) {
            System.out.println("Error in BroadcastServer : "+e.toString());
        }
        }
}












