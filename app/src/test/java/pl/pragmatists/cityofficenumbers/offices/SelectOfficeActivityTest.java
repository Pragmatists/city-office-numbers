package pl.pragmatists.cityofficenumbers.offices;

import static org.robolectric.Shadows.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Intent;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.SelectOfficeActivity;
import pl.pragmatists.cityofficenumbers.testing.TestApplication;
import pl.pragmatists.cityofficenumbers.testing.TestApplicationBuilder;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectOfficeActivityTest {

    private TestApplication testApplication;

    @Test
    public void shows_available_offices_on_start() {
        initTestApplicationWith(cityOfficesWith(
                new Office("1"),
                new Office("2"),
                new Office("3")
        ));

        SelectOfficeActivity selectOfficeActivity = buildSelectOfficeActivity();

        ListView lvOffices = (ListView) selectOfficeActivity.findViewById(R.id.offices);
        Assertions.assertThat(lvOffices.getCount()).isEqualTo(3);
    }

    private void initTestApplicationWith(CityOfficesModel cityOfficesModel) {
        testApplication = new TestApplicationBuilder()
                .withCityOfficesModule(new TestApplicationBuilder.TestCityOfficesModule(cityOfficesModel)).build();
    }

    @Test
    public void goes_to_group_selection_on_item_click() {
        initTestApplicationWith(cityOfficesWith(new Office("").id("9c3d5770-57d8-4365-994c-69c5ac4186ee")));
        SelectOfficeActivity selectOfficeActivity = buildSelectOfficeActivity();
        ListView lvOffices = (ListView) selectOfficeActivity.findViewById(R.id.offices);

        shadowOf(lvOffices).performItemClick(0);

        Intent expectedIntent = new Intent(selectOfficeActivity, SelectGroupActivity.class).putExtra("office-id", "9c3d5770-57d8-4365-994c-69c5ac4186ee");
        Intent nextStartedActivity = shadowOf(selectOfficeActivity).getNextStartedActivity();
        Assertions.assertThat(nextStartedActivity).isEqualTo(expectedIntent);
    }

    private SelectOfficeActivity buildSelectOfficeActivity() {
        return Robolectric.buildActivity(SelectOfficeActivity.class).withApplication(testApplication).create().visible()
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