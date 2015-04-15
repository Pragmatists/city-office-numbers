package pl.pragmatists.cityofficenumbers.offices;

import java.util.List;

public interface CityOfficesView {
    void showNoOfficesAvailableMessage();

    void showOffices(List<Office> offices);
}
