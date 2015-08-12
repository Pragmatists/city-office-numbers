package pl.pragmatists.cityofficenumbers.app;

import android.app.Application;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.OfficeGroupsFetcher;
import pl.pragmatists.http.RestClientWithRestTemplate;

public class CityOfficeNumbersApplication extends Application {
    public CityOfficesModel getCityOfficesModel() {
        return new CityOfficesHardcoded();
    }

    public OfficeGroupsFetcher getOfficeGroupsFetcher() {
        return new OfficeGroupsFetcher(new RestClientWithRestTemplate(), BusInstance.instance());
    }
}
