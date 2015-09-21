package pl.pragmatists.cityofficenumbers.app.selectoffice.messages;

import java.util.Collection;
import java.util.List;

import pl.pragmatists.cityofficenumbers.app.selectoffice.Office;

public class CityOfficesFetchedEvent {
    private final List<Office> offices;

    public CityOfficesFetchedEvent(List<Office> offices) {

        this.offices = offices;
    }

    public Collection<? extends Office> offices() {
        return offices;
    }
}
