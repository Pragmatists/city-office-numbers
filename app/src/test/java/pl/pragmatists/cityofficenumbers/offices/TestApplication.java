package pl.pragmatists.cityofficenumbers.offices;

import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;

public class TestApplication extends CityOfficeNumbersApplication {
    private final CityOfficesModel cityOfficesModel;

    private final OfficeGroupsFetcher officeGroupsFetcher;

    public TestApplication(CityOfficesModel cityOfficesModel, OfficeGroupsFetcher officeGroupsFetcher) {
        this.cityOfficesModel = cityOfficesModel;
        this.officeGroupsFetcher = officeGroupsFetcher;
    }

    @Override
    public CityOfficesModel getCityOfficesModel() {
        return cityOfficesModel;
    }

    @Override
    public OfficeGroupsFetcher getOfficeGroupsFetcher() {
        return officeGroupsFetcher;
    }
}
