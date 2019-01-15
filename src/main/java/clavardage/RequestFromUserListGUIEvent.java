package clavardage;

import java.util.EventListener;

interface UserListGUIEventListener extends EventListener {
    void sessionRequestFromGUI(String userID);
    void newNicknameRequestFromGUI();
    void displayHistoryRequestFromGUI(String userID, String remoteUsername);
}

interface UserListGUIEventGenerator {
    void addUserListGUIEventListener(UserListGUIEventListener listener) ;
}