package clavardage;

import java.net.Socket;
import java.util.EventListener;
import java.util.EventObject;


class LocalUsernameChangedEvent extends EventObject {

    public LocalUsernameChangedEvent(Object source) {
        super(source);
    }
}

interface LocalUsernameChangedListener extends EventListener {
    void localUsernameChanged(LocalUsernameChangedEvent evt);
}

interface LocalUsernameChangedGenerator {
    void addLocalUsernameChangedListener(LocalUsernameChangedListener listener) ;
}


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
}

interface LogInEventGenerator {
    void addLogInListener(LogInListener listener) ;
}



class RemoteConnexionEvent extends EventObject {
Socket sock ;
    public RemoteConnexionEvent(Object source, Socket sock) {
        super(source);
        this.sock = sock;
    }
}

interface RemoteConnexionListener extends EventListener {
    void remoteConnexion(RemoteConnexionEvent evt);
}

interface RemoteConnexionGenerator {
    void addRemoteConnexionListener(RemoteConnexionListener listener) ;
}

