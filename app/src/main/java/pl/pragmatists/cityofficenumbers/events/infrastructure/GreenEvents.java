package pl.pragmatists.cityofficenumbers.events.infrastructure;

import pl.pragmatists.cityofficenumbers.events.EventBus;

public class GreenEvents implements EventBus {

    private final de.greenrobot.event.EventBus eventBus;

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }

    public GreenEvents(de.greenrobot.event.EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
