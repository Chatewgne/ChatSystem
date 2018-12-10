package clavardage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame implements ActionListener{
	
	private JPanel inputContainer = new JPanel();
	private JTextField jtf = new JTextField();
	private JPanel pan = new JPanel();
	
	private JTextArea chatBox;
	
	private JButton sendButton = new JButton("Send");
	
	
	public ChatWindow(String windowName) {
		
		// D�finition des propri�t�s de la fen�tre
		this.setTitle(windowName);
		this.setSize(800, 600);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
		
		
		//D�finition de sa couleur de fond
	    //pan.setBackground(Color.ORANGE);        
		
	    
	    
	    
	    
	    
	    // ###### TESTS
	    
		
	    //On pr�vient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(inputContainer);
	    
	    // Rend la fen�tre visible
	    this.setVisible(true);
	    
	}
	
	
	public static void main (String args[]) {
		
		ChatWindow f = new ChatWindow("ChatSystem");
		
	}
	
	
	private void sendMessageInField() {
		
		String msg = jtf.getText();
		jtf.setText("");
		chatBox.append("<< Me << : " + msg + "\n");
		
		//TODO
		// Here, we should call/tell the system that
		// we want to send the message in the JTextField input (jtf)
		
	}
	
	// Updates the chat box according to the GUI events done by the user
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();
		
		if(source == sendButton || source == jtf) {
			sendMessageInField();
		}
	}
	
	
	public void displayReceivedMessage(String msg) {
		chatBox.append(">> Distant >> : " + msg + "\n");
	}

}
