package clavardage;

import java.util.EventListener;

interface UserListGUIEventListener extends EventListener {
    void sessionRequestFromGUI(String userID);
    void newNicknameRequestFromGUI();
    void disconnectionRequestFromGUI();
}

interface UserListGUIEventGenerator {
    void addUserListGUIEventListener(UserListGUIEventListener listener) ;
}