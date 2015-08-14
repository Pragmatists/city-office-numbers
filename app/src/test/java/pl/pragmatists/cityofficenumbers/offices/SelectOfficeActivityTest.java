package pl.pragmatists.cityofficenumbers.offices;

import static org.robolectric.Shadows.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Application;
import android.content.Intent;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.SelectGroup;
import pl.pragmatists.cityofficenumbers.app.SelectOffice;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectOfficeActivityTest {

    private Application testApplication;

    @Test
    public void shows_available_offices_on_start() {
        testApplication = new TestApplication(cityOfficesWith(
                new Office("1"),
                new Office("2"),
                new Office("3")
        ), null);

        SelectOffice selectOffice = buildSelectOfficeActivity();

        ListView lvOffices = (ListView) selectOffice.findViewById(R.id.offices);
        Assertions.assertThat(lvOffices.getCount()).isEqualTo(3);
    }

    @Test
    public void goes_to_group_selection_on_item_click() {
        testApplication = new TestApplication(cityOfficesWith(new Office("").id("9c3d5770-57d8-4365-994c-69c5ac4186ee")), null);
        SelectOffice selectOffice = buildSelectOfficeActivity();
        ListView lvOffices = (ListView) selectOffice.findViewById(R.id.offices);

        shadowOf(lvOffices).performItemClick(0);

        Intent expectedIntent = new Intent(selectOffice, SelectGroup.class).putExtra("office-id", "9c3d5770-57d8-4365-994c-69c5ac4186ee");
        Intent nextStartedActivity = shadowOf(selectOffice).getNextStartedActivity();
        Assertions.assertThat(nextStartedActivity).isEqualTo(expectedIntent);
    }

    private SelectOffice buildSelectOfficeActivity() {
        return Robolectric.buildActivity(SelectOffice.class).withApplication(testApplication).create().visible()
                    .get();
    }

    private CityOfficesModel cityOfficesWith(final Office... offices) {
        return new CityOfficesHardcoded() {
            @Override
            public Office[] offices() {
                return offices;
            }
        };
    }

}