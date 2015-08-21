package pl.pragmatists.cityofficenumbers.testing;

import static org.mockito.Mockito.*;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.di.AndroidModule;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.FavoriteService;
import pl.pragmatists.cityofficenumbers.offices.Office;

public class TestApplicationBuilder {
    private FavoriteService favoriteService = mock(FavoriteService.class);

    public TestApplication build() {
        TestApplication testApplication = new TestApplication(null);
        CityOfficeNumbersApplication.ApplicationComponent component = DaggerTestApplicationBuilder_TestApplicationComponent.builder()
                .androidModule(new AndroidModule(testApplication))
                .testCityOfficesModule(new DefaultTestCityOfficesModule())
                .build();
        testApplication.withComponent(component);
        return  testApplication;
    }

    public TestApplicationBuilder withFavoriteService(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
        return this;
    }

    @Component(modules = {AndroidModule.class, TestCityOfficesModule.class})
    interface TestApplicationComponent extends CityOfficeNumbersApplication.ApplicationComponent {

    }

    @Module
    public static class TestCityOfficesModule {

        private final Office[] offices;

        private final FavoriteService favoriteService;

        public TestCityOfficesModule(Office[] offices, FavoriteService favoriteService) {
            this.offices = offices;
            this.favoriteService = favoriteService;
        }

        @Provides
        CityOfficesModel cityOfficesModel() {
            return new CityOfficesHardcoded();
        }

        @Provides
        CityOfficesFetcher cityOfficesFetcher() {
            return mock(CityOfficesFetcher.class);
        }

        @Provides
        FavoriteService favoriteService() {return favoriteService; }
    }

    private class DefaultTestCityOfficesModule extends TestCityOfficesModule {

        public DefaultTestCityOfficesModule() {
            super(new Office[]{}, favoriteService);
        }
    }
}
