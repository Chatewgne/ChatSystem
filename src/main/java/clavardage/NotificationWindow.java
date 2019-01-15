package clavardage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class NotificationWindow extends JFrame{

    // Panel of this window/JFrame
    private JPanel container = new JPanel();

    private JLabel notificationLabel;

    //to differentiate between a SET username and a CHANGE username
    private boolean firstconnexion;
    /** Class constructor, instanciate the notification window.
     *
     * @param notification notification to display
     */
    public NotificationWindow(String notification) {

        // D�finition des propri�t�s de la fen�tre
        this.setTitle("Notification");
        this.setSize(500, 100);
        this.setResizable(false);

        this.setLocationRelativeTo(null);


        notificationLabel = new JLabel(notification);

        notificationLabel.setFont(notificationLabel.getFont().deriveFont(16.0f));

        notificationLabel.setHorizontalAlignment(JLabel.CENTER);

        notificationLabel.setForeground(Color.DARK_GRAY);

        // Main container properties
        //container.setBackground(Color.white);
        container.setLayout(new BorderLayout());

        container.add(notificationLabel, BorderLayout.CENTER);


        // We tell our JFrame that the mainPanel is going to be its content pane
        this.setContentPane(container);

        // Set the JFrame visible
        this.setVisible(true);

    }

    public static void main (String args[]){
        new NotificationWindow("There is no history of conversation with this user yet.");
    }

}
