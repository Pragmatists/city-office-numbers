package pl.pragmatists.cityofficenumbers.offices;

import static java.util.Arrays.*;
import static org.assertj.android.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.robolectric.Shadows.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowImageView;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.SelectOfficeActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.offices.messages.CityOfficesFetchedEvent;
import pl.pragmatists.cityofficenumbers.testing.TestApplication;
import pl.pragmatists.cityofficenumbers.testing.TestApplicationBuilder;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectOfficeActivityTest {

    private EventBus bus = BusInstance.instance();

    private TestApplication testApplication;

    private FavoriteService favoriteService;

    private SelectOfficeActivity selectOfficeActivity;

    @Before
    public void setUp() throws Exception {
        favoriteService = mock(FavoriteService.class);
        testApplication = new TestApplicationBuilder().withFavoriteService(favoriteService).build();
    }

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
        Office office = new Office("").id("office-id").favorite(true);
        List<Office> offices = asList(office);
        ListView lvOffices = buildListWith(offices);
        ShadowImageView button = shadowOf((ImageButton) lvOffices.findViewById(R.id.star_image_button));

        button.checkedPerformClick();

        verify(favoriteService).toggleFavorite(anyString(), eq(office));
    }

    private ListView buildListWith(List<Office> offices) {
        selectOfficeActivity = buildSelectOfficeActivity();
        bus.post(new CityOfficesFetchedEvent(offices));
        return (ListView) selectOfficeActivity.findViewById(R.id.offices);
    }

    private SelectOfficeActivity buildSelectOfficeActivity() {
        return Robolectric.buildActivity(SelectOfficeActivity.class).withApplication(testApplication).create().visible().resume()
                .get();
    }

}