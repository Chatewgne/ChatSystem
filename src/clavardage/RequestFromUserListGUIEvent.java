package clavardage;

import java.util.EventListener;

interface UserListGUIEventListener extends EventListener {
    void sessionRequestFromGUI(String userID);
    void newNicknameRequestFromGUI();
}

interface UserListGUIEventGenerator {
    void addUserListGUIEventListener(UserListGUIEventListener listener) ;
}