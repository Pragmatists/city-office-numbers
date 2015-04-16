package pl.pragmatists.cityofficenumbers.offices;

import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;

public class TestApplication extends CityOfficeNumbersApplication {
    private final CityOfficesModel cityOfficesModel;

    public TestApplication(CityOfficesModel cityOfficesModel) {
        this.cityOfficesModel = cityOfficesModel;
    }

    @Override
    public CityOfficesModel getCityOfficesModel() {
        return cityOfficesModel;
    }

}
