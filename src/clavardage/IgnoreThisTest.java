package clavardage;

import java.util.EventListener;
import java.util.HashMap;

public class IgnoreThisTest implements NewMessageToSendListener, UserListGUIEventListener {
	
	private LoggingWindow logWindow;
	
	private ChatWindow chatWindow;
	
	public String nickname;


	private UserListWindow userListWindow;
	
	public IgnoreThisTest() {

		/*
		chatWindow = new ChatWindow("Chat test");
		chatWindow.addNewMessageToSendListener(this);
		*/

		userListWindow = new UserListWindow("Moi");
		userListWindow.addUserListGUIEventListener(this);

		HashMap<String, User> onlineUsers = new HashMap<String,User>();

		for (int i = 0; i < 50; i++)
			onlineUsers.put("test " + i, new User("COUCOU" + i));

		userListWindow.refreshUserListInGUI(onlineUsers);


		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		onlineUsers.clear();

		onlineUsers.put("LOL", new User("HEY"));

		for (int i = 0; i < 30; i++)
			onlineUsers.put("test " + i, new User("COUCOU" + i));

		userListWindow.refreshUserListInGUI(onlineUsers);

	}
	
	public static void main (String args[]) throws InterruptedException {
		IgnoreThisTest test = new IgnoreThisTest();
	}

	@Override
	public void NewMessageToSend(NewMessageToSendEvent evt) {
		System.out.println("From outside the JFrame, I know the message is : " + evt.msg);
	}


	@Override
	public void sessionRequestFromGUI(String userID) {
		System.out.println(userID);
	}

	@Override
	public void newNicknameRequestFromGUI() {
		System.out.println("I want a new nickname!");
	}

	@Override
	public void displayHistoryRequestFromGUI(String userID, String remoteUsername) {

	}


}
