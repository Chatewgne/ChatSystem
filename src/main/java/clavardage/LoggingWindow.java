package clavardage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoggingWindow extends JFrame implements ActionListener, NewMessageToSendEventGenerator, LogInEventGenerator{

	// Panel of this window/JFrame
	private JPanel mainPanel = new JPanel();
	
	// Components of the main panel
	private JTextField nicknameField = new JTextField();
	private JButton logButton = new JButton("Log In");


	// Top panel (with one or two information labels)
	private JPanel topPanel = new JPanel(new GridLayout(0,1));

	// The top panel components
	private JLabel topLabel = new JLabel("Choose a nickname to identify yourself in the chat system.");
		// Label warning the user that he cannot use the char ':'
	private JLabel warningLabel = null;
	
	// Nickname chosen
	private String nickname = "";
	
	// Extern listener
	private NewMessageToSendListener listener;
	private ArrayList<LogInListener> list;

	//to differentiate between a SET username and a CHANGE username
	private boolean firstconnexion;
	/** Class constructor, instanciate the logging window.
	 * 
	 * @param windowName name of the window
	 */
	public LoggingWindow(String windowName) {
		
		// D�finition des propri�t�s de la fen�tre
		this.setTitle(windowName);
		this.setSize(400, 150);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.list = new ArrayList<LogInListener>();
		this.firstconnexion=true;
		
		// Panel layout configuration
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(20);
		
		mainPanel.setBackground(Color.YELLOW);
	    mainPanel.setLayout(borderLayout);
	    
	    
	    // Top label configuration
	    Font labelPolice = new Font("Arial", Font.BOLD, 13);
	    topLabel.setFont(labelPolice);
	    topLabel.setHorizontalAlignment(JLabel.CENTER);

	    topPanel.add(topLabel);

	    topPanel.setBackground(Color.WHITE);

	    mainPanel.add(topPanel, BorderLayout.NORTH);
		
		
	    // Nickname textfield configuration
	    Font police = new Font("Arial", Font.BOLD, 14);
	    nicknameField.setFont(police);
	    nicknameField.setPreferredSize(new Dimension(200, 30));
	    nicknameField.setForeground(Color.RED);
	    nicknameField.setHorizontalAlignment(JTextField.CENTER);

	    // Tells the text field that the window is listening its actions
	    nicknameField.addActionListener(this);

	    mainPanel.add(nicknameField, BorderLayout.CENTER);

	    // Log button configuration
	    logButton.addActionListener(this);
	    mainPanel.add(logButton, BorderLayout.SOUTH);


	    // ###### TESTS


	    // We tell our JFrame that the mainPanel is going to be its content pane
	    this.setContentPane(mainPanel);

		// Set the JFrame visible
		//this.setVisible(true);

	}
	
	
	//public static void main (String args[]) {
	public void start(Boolean firstconnexion){
//		Set the JFrame visible
		this.firstconnexion = firstconnexion;
		this.setVisible(true);

		//LoggingWindow f = new LoggingWindow("ChatSystem - Logging");
		
	}
	
	private void setNickname() {


		nickname = nicknameField.getText();

		/*  With our setup, we cannot accept the char ':' in the nickname
			Hence, we check the input string before transmitting it to the rest of our application.
		 */

		// If the input string is valid, we transmit it to the rest of the application which will inform the network
		if(!nickname.contains(":")) {

			// DEBUG
			//System.out.println("NEW NICKNAME : " + nickname);

			if (firstconnexion) {
				LogInEvent e = new LogInEvent(this, nickname);
				for (int i = 0; i < list.size(); i++) {
					list.get(i).loggedIn(e);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).changedLocalUsername(nickname);
				}
			}

			this.setVisible(false);
		}

		// If the input string contains any ':', we inform the user that we cannot allow it.
		else {

			if(warningLabel == null){

				warningLabel = new JLabel("The character ':' is not allowed.");
				warningLabel.setForeground(Color.RED);
				warningLabel.setHorizontalAlignment(JLabel.CENTER);

				topPanel.add(warningLabel);
				mainPanel.repaint();
				mainPanel.revalidate();

			}

		}

	}
	
	/*public String getNickname() {
		/*if(nickname == "")
			return null;
		else
			return nickname;
		return localUser.getUsername();
	}*/
	
	// Updates the chat box according to the GUI events done by the user
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();
		
		if(source == logButton || source == nicknameField) {
			setNickname();
		}
	}


	@Override
	public void addNewMessageToSendListener(NewMessageToSendListener listener) {
		this.listener = listener;
	}
	@Override
	public void addLogInListener(LogInListener listener) {
		this.list.add(listener);
	}

}
