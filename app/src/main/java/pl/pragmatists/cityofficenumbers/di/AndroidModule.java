package pl.pragmatists.cityofficenumbers.di;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.producers.ProducerModule;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.app.selectgroup.ErrorUi;

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

    @Provides
    ErrorUi errorUi(Context context) {
        return new ErrorUi(context);
    }

}