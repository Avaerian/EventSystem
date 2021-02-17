package org.avaeriandev.testing;

import org.avaeriandev.event.EventService;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        EventService eventService = EventService.getService();
        ConsoleListener listener = new ConsoleListener();

        eventService.register(listener);

        // Setup event thing
        Scanner scanner = new Scanner(System.in);
        String status = scanner.nextLine();
        while(!status.equals("exit")) {

            // Broadcast event
            eventService.execute(new ConsoleMessageEvent(status));
            //eventService.unregister(listener);

            status = scanner.nextLine();
        }

        eventService.unregister(listener);

    }

}
