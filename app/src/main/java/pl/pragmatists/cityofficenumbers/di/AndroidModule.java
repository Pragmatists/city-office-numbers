package pl.pragmatists.cityofficenumbers.di;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;

@Module
public class AndroidModule {
    private final CityOfficeNumbersApplication application;

    public AndroidModule(CityOfficeNumbersApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

}