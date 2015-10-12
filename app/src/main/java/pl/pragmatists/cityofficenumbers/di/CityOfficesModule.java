package pl.pragmatists.cityofficenumbers.di;

import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.app.selectoffice.FavoriteService;
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
    CityOfficesFetcher officesFetcher(EventBus eventBus) {
        return new CityOfficesFetcher(new RestClientWithOkHttp(new Host("http://10.0.2.2:8080")), eventBus);
    }

    @Provides
    EventBus eventBusInstance() {
        return BusInstance.instance();
    }

    @Provides
    FavoriteService favoriteService() {
        return new FavoriteService(new RestClientWithOkHttp(new Host("http://10.0.2.2:8080")));
    }

}

