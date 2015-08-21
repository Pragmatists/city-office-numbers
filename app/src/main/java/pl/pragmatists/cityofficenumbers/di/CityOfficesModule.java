package pl.pragmatists.cityofficenumbers.di;

import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.FavoriteService;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

@Module
public class CityOfficesModule {

    @Provides
    CityOfficesModel cityOfficesModel() {
        return new CityOfficesHardcoded();
    }

    @Provides
    CityOfficesFetcher officesFetcher() {
        return new CityOfficesFetcher(new RestClientWithOkHttp(new Host("http://10.0.2.2:8080")), BusInstance.instance());
    }

    @Provides
    FavoriteService favoriteService() {
        return new FavoriteService(new RestClientWithOkHttp(new Host("http://10.0.2.2:8080")));
    }

}

