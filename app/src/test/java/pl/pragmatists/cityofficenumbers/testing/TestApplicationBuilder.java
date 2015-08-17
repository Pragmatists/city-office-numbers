package pl.pragmatists.cityofficenumbers.testing;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.pragmatists.cityofficenumbers.app.AndroidModule;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesHardcoded;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;

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

        CityOfficesModel cityOfficesModel;

        public TestCityOfficesModule(CityOfficesModel cityOfficesModel) {
            this.cityOfficesModel = cityOfficesModel;
        }

        @Provides
        CityOfficesModel cityOfficesModel() {
            return cityOfficesModel;
        }
    }

    private class DefaultTestCityOfficesModule extends TestCityOfficesModule {

        public DefaultTestCityOfficesModule() {
            super(new CityOfficesHardcoded());
        }
    }
}
