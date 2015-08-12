package pl.pragmatists.cityofficenumbers.events;

import pl.pragmatists.cityofficenumbers.events.infrastructure.GreenEvents;

public class BusInstance {

    private static EventBus bus;

    public static EventBus instance() {

        if (bus == null) {
            bus = new GreenEvents(de.greenrobot.event.EventBus.getDefault());
        }

        return bus;
    }

}
