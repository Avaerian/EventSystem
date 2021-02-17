package org.avaeriandev.event;

import java.util.Set;

public abstract class Event {

    public Set<Listener> getListeners() {
        return EventService.getService().getListeners(this.getClass());
    }

}
