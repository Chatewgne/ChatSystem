package clavardage;

import java.util.HashMap;

interface UserListChangesListener {
    void userListHasChanged (HashMap<String,User> onlineUsers);
}

interface UserListChangesEventGenerator {
    void addUserListChangesListener (UserListChangesListener listener);
}