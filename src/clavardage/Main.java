package clavardage;

public class Main {

    public static void main(String[] args) {
	System.out.println("coucou je suis le main");
	ConversationManager ma = (new ConversationManager());
	//int freeport = ConversationServer.getPort();
        ma.initConvo("localhost",1024);
        ma.run();

    }
}
