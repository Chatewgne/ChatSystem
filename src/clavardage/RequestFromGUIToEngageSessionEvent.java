package clavardage;

import java.util.EventListener;
import java.util.EventObject;

class RequestFromGUIToEngageSessionEvent extends EventObject {

    private String userID;

    public String getUserID(){
        return userID;
    }

    public RequestFromGUIToEngageSessionEvent(String userID) {
        super(userID);
        this.userID = userID;
    }
}

interface UserListGUIEventListener extends EventListener {
    void requestFromGUIToEngageSession(RequestFromGUIToEngageSessionEvent evt);
}

interface UserListGUIEventGenerator {
    void addUserListGUIEventListener(UserListGUIEventListener listener) ;
}