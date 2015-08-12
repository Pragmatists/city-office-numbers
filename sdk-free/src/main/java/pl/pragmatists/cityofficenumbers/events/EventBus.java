package pl.pragmatists.cityofficenumbers.events;

public interface EventBus {
    void post(Object event);

    void register(Object subscriber);

    void unregister(Object subscriber);
}
