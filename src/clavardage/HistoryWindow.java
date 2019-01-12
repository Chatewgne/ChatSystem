package clavardage;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryWindow extends JFrame{

    private JPanel container = new JPanel();
    private JTextField jtf = new JTextField();

    private JTextArea chatBox;

    // Date formatter
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateToDisplay;


    // Extern listener
    private NewMessageToSendListener listener;
    final static String LOOKANDFEEL = "Metal";


    public HistoryWindow(String windowName, ArrayList<Message> messages) {

        // Window properties definition
        this.setTitle(windowName);
        this.setSize(800, 600);
        this.setResizable(true);

        this.setLocationRelativeTo(null);


        // Main container properties
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());

        // Chatbox properties (it will contain all the history)
        chatBox = new JTextArea();
        chatBox.setEditable(false);

        container.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        chatBox.setLineWrap(true);

        displayMessages(messages);



        // We tell our JFrame that the mainPanel is going to be its content pane
        this.setContentPane(container);

        // Set the JFrame visible
        this.setVisible(true);

    }


    // Display every message given by the list in the chat history
    private void displayMessages(ArrayList<Message> messagesList){

        Iterator<Message> messageIter = messagesList.iterator();
        Message currentMessageToDisplay;

        while(messageIter.hasNext()){

            currentMessageToDisplay = messageIter.next();

            displayOneMessage(currentMessageToDisplay);

        }

    }

    // Display one message in the chat history
    private void displayOneMessage(Message message){

        dateToDisplay= sdf.format(message.getDate());

        chatBox.append(dateToDisplay + ": <" + message.getIDSender() + "> : " + message.getContent() + "\n");
    }


}
