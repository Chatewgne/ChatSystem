package clavardage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoggingWindow extends JFrame implements ActionListener, NewMessageToSendEventGenerator, LogInEventGenerator{
	
	// Panel of this window/JFrame
	private JPanel mainPanel = new JPanel();
	private User localUser ;
	
	// Components of the panel
	private JLabel topLabel = new JLabel("Choose a nickname to identify yourself in the chat system.");
	private JTextField nicknameField = new JTextField();
	private JButton logButton = new JButton("Log In");
	
	// Nickname chosen
	private String nickname = "";
	
	// Extern listener
	private NewMessageToSendListener listener;
	private ArrayList<LogInListener> list;
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
		
		
		// Panel layout configuration
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(20);
		
		mainPanel.setBackground(Color.YELLOW);
	    mainPanel.setLayout(borderLayout);
	    
	    
	    // Top label configuration
	    Font labelPolice = new Font("Arial", Font.BOLD, 13);
	    topLabel.setFont(labelPolice);
	    topLabel.setHorizontalAlignment(JLabel.CENTER);
	    mainPanel.add(topLabel, BorderLayout.NORTH);
		
		
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
	public void start(){
//		Set the JFrame visible
		this.setVisible(true);

		//LoggingWindow f = new LoggingWindow("ChatSystem - Logging");
		
	}
	
	
	private void setNickname() {
		
		nickname = nicknameField.getText();
		System.out.println("NEW NICKNAME : " + nickname);
		LogInEvent e = new LogInEvent(this, nickname);
		for (int i = 0 ; i < list.size(); i++) {
			list.get(i).loggedIn(e);
		}
		this.setVisible(false);
	}
	
	public String getNickname() {
		if(nickname == "")
			return null;
		else
			return nickname;
	}
	
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
