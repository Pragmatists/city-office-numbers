package pl.pragmatists.cityofficenumbers.testing;

import static org.mockito.Mockito.*;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.di.AndroidModule;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesFetcher;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.Office;

public class TestApplicationBuilder {
    private TestCityOfficesModule testCityOfficesModule = new DefaultTestCityOfficesModule();

    public TestApplicationBuilder withCityOfficesModule(TestCityOfficesModule testCityOfficesModule) {
        this.testCityOfficesModule = testCityOfficesModule;
        return this;
    }

    public TestApplication build() {
        TestApplication testApplication = new TestApplication(null);
        CityOfficeNumbersApplication.ApplicationComponent component = DaggerTestApplicationBuilder_TestApplicationComponent.builder()
                .androidModule(new AndroidModule(testApplication))
                .testCityOfficesModule(testCityOfficesModule)
                .build();
        testApplication.withComponent(component);
        return  testApplication;
    }

    @Component(modules = {AndroidModule.class, TestCityOfficesModule.class})
    interface TestApplicationComponent extends CityOfficeNumbersApplication.ApplicationComponent {

    }

    @Module
    public static class TestCityOfficesModule {

        private final Office[] offices;

        public TestCityOfficesModule(Office[] offices) {
            this.offices = offices;
        }

        @Provides
        CityOfficesModel cityOfficesModel() {
            return null;
        }

        @Provides
        CityOfficesFetcher cityOfficesFetcher() {
            return mock(CityOfficesFetcher.class);
        }
    }

    private class DefaultTestCityOfficesModule extends TestCityOfficesModule {

        public DefaultTestCityOfficesModule() {
            super(new Office[]{});
        }
    }
}
