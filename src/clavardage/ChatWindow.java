package clavardage;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ChatWindow extends JFrame implements ActionListener, NewMessageToSendEventGenerator{
	
	private JPanel inputContainer = new JPanel();
	private JTextField jtf = new JTextField();
	
	private JTextArea chatBox;
	private String myusername;
	private String remoteuser ;
	private JButton sendButton = new JButton("Send");
	
	
	// Extern listener
	private NewMessageToSendListener listener;
	final static String LOOKANDFEEL = "Metal";


	public ChatWindow(String windowName, String remote, String local) {

		this.myusername = local;
		this.remoteuser = remote;
		// D�finition des propri�t�s de la fen�tre
		this.setTitle(windowName);
		this.setSize(800, 600);
		this.setResizable(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		inputContainer.setBackground(Color.white);
	    inputContainer.setLayout(new BorderLayout());
		
		
		JPanel bottom = new JPanel();
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(500, 30));
	    jtf.setForeground(Color.BLUE);
	    
	    // Tells the text field that the window is listening its actions
	    jtf.addActionListener(this);
	    
	    bottom.add(jtf);
	    
	    
	    sendButton.addActionListener(this);
	    bottom.add(sendButton);
	    inputContainer.add(bottom, BorderLayout.SOUTH);
	    
	    
	    chatBox = new JTextArea();
        chatBox.setEditable(false);
        
        /* Allow the chatbox to get to the end of the display
         * when the chatbox is updated (message sent or received)
         */
        DefaultCaret caret = (DefaultCaret)chatBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        inputContainer.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        chatBox.setLineWrap(true);
        
        // DEBUG
        /*
       		for(int i = 0; i < 500; i++)
        	chatBox.append("HEY " + i + "\n");
        */
	    
		
        // We tell our JFrame that the mainPanel is going to be its content pane
	    this.setContentPane(inputContainer);
	    
	    // Set the JFrame visible
	  //  this.setVisible(true);
	    
	}
	
	
	public static void main (String args[]) {
		try {
		String lookandfeel = "javax.swing.plaf.metal.MetalLookAndFeel";
		UIManager.setLookAndFeel(lookandfeel);
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());
			UIManager.setLookAndFeel(new MetalLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			ChatWindow f = new ChatWindow("ChatSystem","DEBUG","DEBUG");
		}
		catch (Exception e){
		}

	}

	public void displayInfo(String mess){
		chatBox.append( "--- " + mess + " ---\n");
	}


	public void refreshRemoteUsername(String remoteUsername){
		this.setTitle("-- You are speaking to "+ remoteUsername +"--");
	}

	private void sendMessageInField() {
		
		String msg = jtf.getText();
		jtf.setText("");
		chatBox.append("<< "+myusername+" << : " + msg + "\n");
		listener.NewMessageToSend(new NewMessageToSendEvent(this, msg));
	}
	
	// Updates the chat box according to the GUI events done by the user
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();
		
		if(source == sendButton || source == jtf) {
			sendMessageInField();
		}
	}
	
	
	public void displayReceivedMessage(String msg) {
		chatBox.append(">> "+remoteuser +" >> : " + msg + "\n");
	}


	@Override
	public void addNewMessageToSendListener(NewMessageToSendListener listener) {
		this.listener = listener;	
	}

}
