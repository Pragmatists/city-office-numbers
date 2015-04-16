package pl.pragmatists.cityofficenumbers.offices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.SelectOffice;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ShowAvailableOfficesOnStartTest {

    @Test
    public void shows_available_offices_on_start() {
        SelectOffice selectOffice = Robolectric.buildActivity(SelectOffice.class).create().get();



    }


}