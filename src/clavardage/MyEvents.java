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
