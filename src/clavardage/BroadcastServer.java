package clavardage;

import javax.swing.event.EventListenerList;
import java.lang.reflect.Array;
import java.net.*;
import java.util.HashMap;

public class BroadcastServer extends Thread implements LocalUsernameChangedListener, LogInListener {

    private SystemState system;
    private User localUser;
    private DatagramSocket socket;
    private byte[] out;
    private byte[] in;
    private String myip;
    private boolean dropInformationPackets ; //I only need to accept one information packet at log in


    //TODO where is the localUser meant to be between here and GlobalManager
    public BroadcastServer(LoggingWindow win) {
        system = new SystemState();
      initSocket();
        win.addLogInListener(this);
        this.in = new byte[256];
        this.out = new byte[256];
        this.dropInformationPackets = false ;
    }

    public HashMap<String,User> getOnlineUsers(){
        return system.getOnlineUsers();
    }

    private void initSocket()
    {
        try {
            this.socket = new DatagramSocket(4321);
        }catch (Exception e) {
            System.out.println("broadcast server couldn't initialise socket : " + e.toString());
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

    public User getUserFromId(int userid) {
        return system.getUser(userid);
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
            String[] str = receive.split(":"); //array stores information : str[1] is packet type (CO,IN,CH,QU)
            if (str[0].equals("CO")) { //if someone connected to the system (packet CO) then
                if (str[1].equals(localUser.getID())){ //if this is my own connection packet (str[1] = my own id) , remember my IP adress
                    myip = source.toString();
                    System.out.println("My ip is : " + myip);
                }else {//if this connection packet is from someone else, store them and send them information about the system (list of users and their addresses : remote users + myself
                    String answer = "IN:" + system.toString() + ":" + myself() ;
                    sendMessage(answer, source);
                    System.out.println("Sent UDP datagram " + answer + "to host " + source);
                    system.addOnlineUser(str[1], new User(str[1], str[2], source.toString()));
                }
            } else if (str[0].equals("CH")) { //TODO if someone changed name
            } else if (str[0].equals("QU")) {//TODO if someone disconnected
            } else if (str[0].equals("IN")) {//TODO if someone is responding with some information
treatInformationPacket(str);
            } else {
                System.out.println("~~~~~~~ Recu UDP datagram AU FORMAT INCONNU ~~~~~~~~ " + receive);
            }

        }
        } catch (Exception e) {
            System.out.println("Couldn't receive datagram packet : " + e.toString());
        }
        return receive;
    }

    private void treatInformationPacket(String[] str){
        if (!(dropInformationPackets)) {
            system.setCurrentConversations(Integer.parseInt(str[1]));
            for (int i = 2; i < str.length - 1; i+=3){
                system.addOnlineUser(str[i+1],new User(str[i+1],str[i+2],str[i])); // hasmap  : key = id, user(id,username,ip)
            }
            dropInformationPackets=true;
        }
    }

    private String myself(){
        return myip + ":" + localUser.getID() + ":" + localUser.getUsername();
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
            System.out.println("Broadcast Server running...");
            while(true) {
                String str = receiveMessage();
            }
        } catch (Exception e) {
            System.out.println("Error in BroadcastServer : "+e .toString());
        }
        }
}












