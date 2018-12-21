package clavardage;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserListWindow extends JFrame implements UserListGUIEventGenerator, UserPanelEventListener, ActionListener {


    private static JPanel globalContainer = new JPanel();

    private static JPanel usersPane = new JPanel(new GridLayout(0,1, 0 , 20));
    private JScrollPane userScrollPane = new JScrollPane(usersPane);


    /*  Personal settings panel
        It contains the actual nickname in a label,
        and a button to ask for a nickname change.
     */
    private JPanel personalPanel = new JPanel();
    private JLabel nicknameLabel = new JLabel("Your nickname : ?");
    private JButton changeNicknameButton = new JButton("Change nickname");


    // Listener of the userListWindow
    private UserListGUIEventListener userListGUIEventListener;


    public UserListWindow(String myNickname){

        // Defining window properties
        this.setTitle("ChatSystem : Online user list");
        this.setSize(500, 800);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        globalContainer.setLayout(new BorderLayout());


        // Add the list of users to the container of the JFrame
        globalContainer.add(userScrollPane, BorderLayout.CENTER);

        // Setup then add the personal information panel to the global container
        setupPersonalPanel(myNickname);
        globalContainer.add(personalPanel, BorderLayout.SOUTH);



        this.setContentPane(globalContainer);
        this.setVisible(true);

    }

    /*  Setup the personal panel (the one at the bottom of the user list)
        In particular, add an action listener on the changeNicknameButton
     */
    private void setupPersonalPanel(String myNickname){
        personalPanel.setLayout(new BorderLayout());

        changeNicknameButton.addActionListener(this);

        this.refreshNicknameLabel(myNickname);

        personalPanel.add(nicknameLabel, BorderLayout.WEST);
        personalPanel.add(changeNicknameButton, BorderLayout.EAST);
    }





    public static void main (String args[]) throws InterruptedException{
        UserListWindow userListWindow = new UserListWindow("test");

        /*
        while(true){
            Thread.sleep(100);

            UserPanel userPanel = new UserPanel( new User("YES"));

            usersPane.add(userPanel);

            globalContainer.validate();
        }
        */

    }

    @Override
    public void addUserListGUIEventListener(UserListGUIEventListener listener) {
        this.userListGUIEventListener = listener;
    }


    /*  Refresh the user list window according to the hashmap given in parameter
     */
    public void refreshUserListInGUI(HashMap<String,User> onlineUsers){

        usersPane.removeAll();

        UserPanel newUserPanel;
        System.out.println("Refreshing display");
        Iterator entries = onlineUsers.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry entry = (Map.Entry)entries.next();
            newUserPanel = new UserPanel( (User) entry.getValue() );
            newUserPanel.addUserPanelEventListener(this);
            usersPane.add(newUserPanel);
        }

        usersPane.revalidate();
        usersPane.repaint();


    }


    /*
        Refresh the nickname label in this window
     */
    public void refreshNicknameLabel(String newNickname){
        nicknameLabel.setText("Your nickname : " + newNickname);
    }

    @Override
    public void engageSessionButtonRequest(String userID) {
        this.userListGUIEventListener.sessionRequestFromGUI(userID);
    }

    @Override
    public void showHistoryButtonRequest(String userID) {
        this.userListGUIEventListener.displayHistoryRequestFromGUI(userID);
    }

    @Override
    /*  Listens to the "Change nickname" button
        and tells the UserListGUIListener that this window asks for a new nickname
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changeNicknameButton)
            this.userListGUIEventListener.newNicknameRequestFromGUI();
    }
}
