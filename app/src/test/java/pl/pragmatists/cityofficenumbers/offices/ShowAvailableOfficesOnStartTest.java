package pl.pragmatists.cityofficenumbers.offices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import pl.pragmatists.cityofficenumbers.app.SelectOffice;

@RunWith(RobolectricTestRunner.class)
public class ShowAvailableOfficesOnStartTest {

    @Test
    public void shows_available_offices_on_start() {
        SelectOffice selectOffice = Robolectric.buildActivity(SelectOffice.class).create().get();



    }


}