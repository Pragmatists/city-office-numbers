package pl.pragmatists.cityofficenumbers.app.common;

import android.app.Application;
import dagger.Component;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.OfficesIntentService;
import pl.pragmatists.cityofficenumbers.app.selectoffice.SelectOfficeActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.ToggleFavoriteIntentService;
import pl.pragmatists.cityofficenumbers.di.AndroidModule;
import pl.pragmatists.cityofficenumbers.di.CityOfficesModule;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

import javax.inject.Singleton;


public class CityOfficeNumbersApplication extends Application {

    @Singleton
    @Component(modules = {AndroidModule.class, CityOfficesModule.class})
    public interface ApplicationComponent {
        void inject(CityOfficeNumbersApplication application);

        void inject(SelectOfficeActivity selectOfficeActivity);

        void inject(OfficesIntentService officesIntentService);

        void inject(ToggleFavoriteIntentService toggleFavoriteIntentService);

        void inject(SelectGroupActivity selectGroupActivity);
    }

    public OfficeGroupsFetcher getOfficeGroupsFetcher() {
        return new OfficeGroupsFetcher(new RestClientWithOkHttp(new Host("https://api.um.warszawa.pl")), BusInstance.instance());
    }

    protected ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        createComponent();
        component().inject(this);
    }

    protected void createComponent() {
        component = DaggerCityOfficeNumbersApplication_ApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ApplicationComponent component() {
        return component;
    }
}
