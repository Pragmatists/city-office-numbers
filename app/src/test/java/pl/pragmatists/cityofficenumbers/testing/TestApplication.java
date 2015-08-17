package pl.pragmatists.cityofficenumbers.testing;

import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;

public class TestApplication extends CityOfficeNumbersApplication {

    private final OfficeGroupsFetcher officeGroupsFetcher;

    public TestApplication(OfficeGroupsFetcher officeGroupsFetcher) {
        this.officeGroupsFetcher = officeGroupsFetcher;
    }

    @Override
    public OfficeGroupsFetcher getOfficeGroupsFetcher() {
        return officeGroupsFetcher;
    }

    @Override
    protected void createComponent() {

    }

    public void withComponent(ApplicationComponent component) {
        this.component = component;
    }
}
