package clavardage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

class UserPanel extends JPanel implements ActionListener, UserPanelEventGenerator {


    private String userID;
    private String username;

    private JLabel userLabel = new JLabel();

    private JButton engageSessionButton = new JButton("Engage session");

    private JButton showHistoryButton = new JButton("Show history");

    /*  Interface to the userListWindow
        If something happens in this userpanel (like a click on "Engage session"),
        the UserListWindow will be informed of this event and will be given the corresponding userID.
     */

    private UserPanelEventListener userListWindow;


    public UserPanel(User user){

        this.userID = user.getID();
        this.username = user.getUsername();

        this.setLayout(new GridLayout(1,2, 10, 0));

        userLabel.setText(username);
        // userLabel.setPreferredSize(new Dimension(400,30));

        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        showHistoryButton.addActionListener(this);
        engageSessionButton.addActionListener(this);

        this.add(userLabel);
        this.add(showHistoryButton);
        this.add(engageSessionButton);

    }

    public String getUserID(){
        return this.userID;
    }

    public String getUsername(){
        return this.username;
    }

    private void refreshUsername(String newUsername){
        this.username = newUsername;
        this.userLabel.setText(username);
        this.validate();
    }

    // Set the new username in the label according to the user given
    public void setNewUsername(User user){

        if(user.getID() == this.userID)
            refreshUsername(user.getUsername());

        /*  If the given user is different from the user identified by the panel,
            the panel will only show an error message (without raising an exception)
         */

        else
            refreshUsername(this.username + " -> error : trying to change name of wrong user.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source == engageSessionButton)
            this.userListWindow.engageSessionButtonRequest(this.getUserID());
        else if(source == showHistoryButton)
            this.userListWindow.showHistoryButtonRequest(this.getUserID());

    }

    @Override
    public void addUserPanelEventListener(UserPanelEventListener listener) {
        this.userListWindow = listener;
    }
}

interface UserPanelEventListener {
    void engageSessionButtonRequest(String userID);
    void showHistoryButtonRequest(String userID);
}

interface UserPanelEventGenerator {
    void addUserPanelEventListener(UserPanelEventListener listener) ;
}