package clavardage;

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
}

interface LogInEventGenerator {
    void addLogInListener(LogInListener listener) ;
}
