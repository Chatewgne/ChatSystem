package clavardage;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {


    private String userID;
    private String username;

    private JLabel userLabel = new JLabel();


    public UserPanel(User user){

        this.userID = user.getID();
        this.username = user.getUsername();

        userLabel.setText(username);
        // userLabel.setPreferredSize(new Dimension(400,30));

        this.add(userLabel);

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

}
