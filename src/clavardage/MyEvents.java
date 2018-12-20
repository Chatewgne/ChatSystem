package clavardage;

import java.net.Socket;
import java.util.EventListener;
import java.util.EventObject;

class LogInEvent extends EventObject {
    public String name;
    public LogInEvent(Object source,String pseudo) {
        super(source);
        name = pseudo;
    }
}

interface LogInListener extends EventListener {
    void loggedIn(LogInEvent evt);
    void changedLocalUsername(String e);
    void changedRemoteUsername(String id, String username);
    void remoteDisconnection();
}

interface LogInEventGenerator {
    void addLogInListener(LogInListener listener) ;
}



class RemoteConnectionEvent extends EventObject {
Socket sock ;
    public RemoteConnectionEvent(Object source, Socket sock) {
        super(source);
        this.sock = sock;
    }
}

interface RemoteConnectionListener extends EventListener {
    void remoteConnection(RemoteConnectionEvent evt);
}

interface RemoteConnectionEventGenerator {
    void addRemoteConnectionListener(RemoteConnectionListener listener) ;
}

