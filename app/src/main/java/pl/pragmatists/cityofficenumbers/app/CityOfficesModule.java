package pl.pragmatists.cityofficenumbers.app;

import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;

@Module
public class CityOfficesModule {

    @Provides
    CityOfficesModel cityOfficesModel() {
        return new CityOfficesHardcoded();
    }

}
