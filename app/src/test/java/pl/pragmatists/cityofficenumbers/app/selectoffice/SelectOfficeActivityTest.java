package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.content.Intent;
import android.widget.ListView;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectoffice.messages.CityOfficesFetchedEvent;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.android.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectOfficeActivityTest {

    private EventBus bus = BusInstance.instance();

    private SelectOfficeActivity selectOfficeActivity;

    @Test
    public void shows_available_offices_on_start() {
        ListView lvOffices = buildListWith(asList(
                new Office("1"),
                new Office("2"),
                new Office("3")));
        assertThat(lvOffices).hasCount(3);
    }

    @Test
    @Ignore
    public void goes_to_group_selection_on_item_click() {
        ListView lvOffices = buildListWith(asList(new Office("").id("9c3d5770-57d8-4365-994c-69c5ac4186ee")));

        shadowOf(lvOffices).performItemClick(0);

        //TODO: Assert new activity is started
        //Assertions.assertThat(nextStartedActivity).isEqualTo(expectedIntent);
    }

    @Test
    @Ignore
    public void draws_favorite_star_on_favorite_item() {
    }

    @Test
    @Ignore
    public void draws_not_a_favorite_star_on_not_a_favorite_item() {
    }

    @Test
    @Ignore
    public void changes_favorite_state_on_item() {
    }

    private Intent secondStartedService() {
        shadowOf(selectOfficeActivity).getNextStartedService();
        return shadowOf(selectOfficeActivity).getNextStartedService();
    }

    private ListView buildListWith(List<Office> offices) {
        selectOfficeActivity = buildSelectOfficeActivity();
        bus.post(new CityOfficesFetchedEvent(offices));
        return (ListView) selectOfficeActivity.findViewById(R.id.offices);
    }

    private SelectOfficeActivity buildSelectOfficeActivity() {
        return Robolectric.buildActivity(SelectOfficeActivity.class).create().visible().resume().get();
    }

}