package org.avaeriandev.testing;

import org.avaeriandev.event.EventHandler;
import org.avaeriandev.event.Listener;

public class ConsoleListener implements Listener {

    @EventHandler
    public void onConsoleMessage(ConsoleMessageEvent e) {
        System.out.println("Console message event listened: " + e.getMessage());
    }

}
