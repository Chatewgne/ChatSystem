package clavardage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserListWindow extends JFrame {


    private static JPanel globalContainer = new JPanel();

    private JPanel userPanel = new JPanel();
    private static JPanel usersPane = new JPanel(new GridLayout(0,1, 0 , 0));
    private JScrollPane userScrollPane = new JScrollPane();


    // Personal settings panel
    private JPanel personalPanel = new JPanel();
    private JLabel nicknameLabel = new JLabel("Your nickname : ?");
    private JButton changeNicknameButton = new JButton("Change nickname");

    public UserListWindow(){

        // Defining window properties
        this.setTitle("ChatSystem : Online user list");
        this.setSize(500, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        globalContainer.setLayout(new BorderLayout());

        JLabel aUser = new JLabel("User?");
        aUser.setMinimumSize(new Dimension(0,50));

        usersPane.add(aUser);

        userScrollPane = new JScrollPane(usersPane);

        personalPanel.setLayout(new BorderLayout());
        personalPanel.add(nicknameLabel, BorderLayout.WEST);
        personalPanel.add(changeNicknameButton, BorderLayout.EAST);

        globalContainer.add(userScrollPane, BorderLayout.CENTER);
        globalContainer.add(personalPanel, BorderLayout.SOUTH);


        this.setContentPane(globalContainer);
        this.setVisible(true);

    }


    public static void main (String args[]) throws InterruptedException{
        UserListWindow userListWindow = new UserListWindow();

        while(true){
            Thread.sleep(100);

            JLabel aUser = new JLabel("User?");
            aUser.setMinimumSize(new Dimension(0,50));

            usersPane.add(aUser);

            globalContainer.validate();
        }

    }

}
