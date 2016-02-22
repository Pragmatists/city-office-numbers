package pl.pragmatists.cityofficenumbers.di;

import javax.inject.Singleton;

import android.content.ContentResolver;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.app.selectgroup.ErrorUi;
import pl.pragmatists.cityofficenumbers.app.selectoffice.UserId;

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
    ContentResolver contentResolver(Context context) {
        return context.getContentResolver();}

    @Provides
    ErrorUi errorUi(Context context) {
        return new ErrorUi(context);
    }

    @Provides
    UserId userId(ContentResolver contentResolver) { return new UserId(contentResolver);}
}