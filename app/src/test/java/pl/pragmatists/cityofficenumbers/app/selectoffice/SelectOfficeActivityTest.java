package pl.pragmatists.cityofficenumbers.app.selectoffice;

import static java.util.Arrays.*;
import static org.assertj.android.api.Assertions.*;
import static org.robolectric.Shadows.*;
import static pl.pragmatists.cityofficenumbers.app.selectoffice.ToggleFavoriteIntentService.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowImageView;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.messages.CityOfficesFetchedEvent;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;

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
    public void goes_to_group_selection_on_item_click() {
        ListView lvOffices = buildListWith(asList(new Office("").id("9c3d5770-57d8-4365-994c-69c5ac4186ee")));

        shadowOf(lvOffices).performItemClick(0);

        Intent expectedIntent = new Intent(selectOfficeActivity, SelectGroupActivity.class).putExtra("office-id",
                "9c3d5770-57d8-4365-994c-69c5ac4186ee");
        Intent nextStartedActivity = shadowOf(selectOfficeActivity).getNextStartedActivity();
        Assertions.assertThat(nextStartedActivity).isEqualTo(expectedIntent);
    }

    @Test
    public void draws_favorite_star_on_favorite_item() {
        ListView lvOffices = buildListWith(asList(new Office().favorite(true)));

        ShadowImageView button = shadowOf((ImageButton) lvOffices.findViewById(R.id.star_image_button));

        Assertions.assertThat(button.getImageResourceId()).isEqualTo(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
    }

    @Test
    public void changes_favorite_state_on_item() {
        ListView lvOffices = buildListWith(asList(new Office("").id("an-office-id").favorite(true)));
        ShadowImageView button = shadowOf((ImageButton) lvOffices.findViewById(R.id.star_image_button));

        button.checkedPerformClick();

        Intent expectedIntent = new Intent(selectOfficeActivity, ToggleFavoriteIntentService.class)
                .putExtra(ARG_OFFICE_ID, "an-office-id")
                .putExtra("user-id", (String) null)
                .putExtra(ToggleFavoriteIntentService.ARG_FAVORITE, true);
        Assertions.assertThat(secondStartedService()).isEqualTo(expectedIntent);

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