package clavardage;

import ro.pippo.core.Pippo;
import ro.pippo.core.WebServer;

public class Main {

    public static void main(String[] args) {
	/////////////////////// TEST DE COMMUNICATION TCP EN LOCAL//////////*

  /*          System.out.println("coucou je suis le main");
    ConversationServer cs = new ConversationServer();
    cs.start();
	ConversationManager ma = (new ConversationManager());
	//int freeport = ConversationServer.getPort();
        ma.initConvo("localhost",4321);
        //ma.run();*/
//////////////////////////TEST DE MON PREMIER EVENT LISTENER////////////////////////
     /*             User user = new User("Ptiteigne");
        BroadcastServer servy = new BroadcastServer(user);
            System.out.println("Created user : id = " + user.getID() + " username = " + user.getUsername());
            user.setUsername("Chatewgne");*/
  /////////////////////////TEST UDP//////////////////////////////////////
      /*  User user = new User("Chatewgne");
  BroadcastServer newt = new BroadcastServer(user);
  newt.run();*/
        GlobalManager gb = new GlobalManager();
        Pippo pip = new Pippo(new PippoServer());
        pip.start();
        gb.start();



    }
}
