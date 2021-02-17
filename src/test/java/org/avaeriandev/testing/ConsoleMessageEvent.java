package org.avaeriandev.testing;

import org.avaeriandev.event.Event;

public class ConsoleMessageEvent extends Event {

    private String message;

    public ConsoleMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
