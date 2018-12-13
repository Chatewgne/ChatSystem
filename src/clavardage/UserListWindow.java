package clavardage;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserListWindow extends JFrame implements UserListGUIEventGenerator, MouseListener {


    private static JPanel globalContainer = new JPanel();

    private JPanel userPanel = new JPanel();

    private static JPanel usersPane = new JPanel(new GridLayout(0,1, 0 , 20));
    private JScrollPane userScrollPane = new JScrollPane();


    // Personal settings panel
    private JPanel personalPanel = new JPanel();
    private JLabel nicknameLabel = new JLabel("Your nickname : ?");
    private JButton changeNicknameButton = new JButton("Change nickname");


    // Listener of the userListWindow
    private UserListGUIEventListener userListGUIEventListener;


    public UserListWindow(){

        // Defining window properties
        this.setTitle("ChatSystem : Online user list");
        this.setSize(500, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        globalContainer.setLayout(new BorderLayout());

        /*JLabel aUser = new JLabel("User?");
        aUser.setMinimumSize(new Dimension(0,50));*/

        UserPanel aUser = new UserPanel( new User("Quelqu'un") );
        aUser.addMouseListener(this);

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

            UserPanel userPanel = new UserPanel( new User("YES"));

            usersPane.add(userPanel);

            globalContainer.validate();
        }

    }

    @Override
    public void addUserListGUIEventListener(UserListGUIEventListener listener) {
        this.userListGUIEventListener = listener;
    }


    /*  Modify the user list in the GUI according to the parameters
        /!\ NOT FINISHED
     */
    public void modifyUserList(User user, String userModification){

        if(userModification ==  "?"){

            UserPanel newUserPanel = new UserPanel(user);
            newUserPanel.addMouseListener(this);

            usersPane.add(newUserPanel);
            globalContainer.validate();

        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();

        if(source instanceof UserPanel){

            UserPanel userPanel = (UserPanel) source;

            this.userListGUIEventListener.requestFromGUIToEngageSession(
                    new RequestFromGUIToEngageSessionEvent(userPanel.getUserID())
            );

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
