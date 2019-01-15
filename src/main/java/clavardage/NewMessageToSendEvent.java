package clavardage;

import java.util.EventListener;
import java.util.EventObject;

public class NewMessageToSendEvent extends EventObject {
	
	public String msg;

	public NewMessageToSendEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

}


interface NewMessageToSendListener extends EventListener {
    void NewMessageToSend(NewMessageToSendEvent evt);
}

interface NewMessageToSendEventGenerator {
    void addNewMessageToSendListener(NewMessageToSendListener listener) ;
}
