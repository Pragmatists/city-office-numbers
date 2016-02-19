package pl.pragmatists.cityofficenumbers.di;

import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.app.selectoffice.FavoriteService;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

@Module
public class CityOfficesModule {

    @Provides
    CityOfficesModel cityOfficesModel() {
        return new CityOfficesHardcoded();
    }

    @Provides
    CityOfficesFetcher officesFetcher(EventBus eventBus, Host host) {
        return new CityOfficesFetcher(new RestClientWithOkHttp(host), eventBus);
    }

    @Provides
    FavoriteService favoriteService(Host host) {
        return new FavoriteService(new RestClientWithOkHttp(host));
    }

    @Provides
    EventBus eventBusInstance() {
        return BusInstance.instance();
    }

    @Provides
    Host host() {
        return new Host("https://city-office-numbers.herokuapp.com");
    }

}

