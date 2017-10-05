package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Intent;
import android.widget.ListView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.enternumber.EnterNumberActivity;
import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder.anOfficeGroup;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder.withOneGroup;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SelectGroupActivityTest {

    private EventBus eventBus = BusInstance.instance();

    @Test
    public void shouldFetchGroupsOnCreate() {
        Intent intent = new Intent().putExtra(SelectGroupActivity.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        SelectGroupActivity activity = createSelectGroupActivity(intent);

        Intent nextStartedService = Shadows.shadowOf(activity).getNextStartedService();
        assertThat(nextStartedService).hasExtra(SelectGroupActivity.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
    }

    @Test
    public void showsLoadedGroups() {
        SelectGroupActivity activity = createDefaultSelectGroupActivity();

        eventBus.post(new OfficeGroupsFetched(OfficeGroupsBuilder.withOneGroup().build()));

        ListView listView = (ListView) activity.findViewById(R.id.office_groups);
        assertThat(listView.getCount()).isEqualTo(1);
    }

    // TODO: Check reaction to UI error: eventBus.post(new RestServerError("details"));
    @Test
    @Ignore
    public void showsAToastOnServerError() {
    }

    // TODO: Check also text
    @Test
    @Ignore
    public void showsAToastOnNetworkError() {
    }

    @Test
    public void goesToGroupActivityOnSelect() {
        SelectGroupActivity activity = createSelectGroupActivity("9c3d5770-57d8-4365-994c-69c5ac4186ee");
        ListView lvOffices = (ListView) activity.findViewById(R.id.office_groups);

        eventBus.post(new OfficeGroupsFetched(withOneGroup(anOfficeGroup().withId(1).build()).build()));

        shadowOf(lvOffices).performItemClick(0);

        Intent nextStartedActivity = shadowOf(activity).getNextStartedActivity();
        assertThat(nextStartedActivity).hasComponent("pl.pragmatists.cityofficenumbers.app", EnterNumberActivity.class)
                .hasExtra("office-id", "9c3d5770-57d8-4365-994c-69c5ac4186ee")
        .hasExtra("group-id", 1);
    }

    private SelectGroupActivity createDefaultSelectGroupActivity() {
        return createSelectGroupActivity("5d2e698a-9c31-456b-8452-7ce33e7deb94");
    }

    private SelectGroupActivity createSelectGroupActivity(String officeId) {
        Intent intent = new Intent().putExtra(SelectGroupActivity.ARG_OFFICE_ID, officeId);
        return createSelectGroupActivity(intent);
    }

    private SelectGroupActivity createSelectGroupActivity(Intent intent) {
        return Robolectric.buildActivity(SelectGroupActivity.class)
                .withIntent(intent)
                .create().visible().resume().get();
    }

}