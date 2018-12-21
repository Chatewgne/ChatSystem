package clavardage;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryWindow extends JFrame{

    private JPanel container = new JPanel();
    private JTextField jtf = new JTextField();

    private JTextArea chatBox;


    // Extern listener
    private NewMessageToSendListener listener;
    final static String LOOKANDFEEL = "Metal";


    public HistoryWindow(String windowName, ArrayList<Message> messages) {

        // Window properties definition
        this.setTitle(windowName);
        this.setSize(800, 600);
        this.setResizable(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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


    // A debugging test
    public static void main (String args[]) {
        try {
            ArrayList<Message> messages = new ArrayList<Message>();

            for(int i = 0; i < 50 ; i++){
                if(i%2 == 0)
                    messages.add(new Message("Hey" + i, "Harry", "Yann"));
                else
                    messages.add(new Message("Yo" + i, "Yann", "Harry"));
            }

            HistoryWindow f = new HistoryWindow("History Window (debugging)",messages);
        }
        catch (Exception e){
        }


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
        chatBox.append("<" + message.getSender() + "> : " + message.getContent() + "\n");
    }


}
