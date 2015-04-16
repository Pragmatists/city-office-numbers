package pl.pragmatists.cityofficenumbers.groups;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.GroupsLoader;
import pl.pragmatists.cityofficenumbers.app.OfficeGroup;
import pl.pragmatists.cityofficenumbers.app.SelectGroup;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class GroupsLoaderTest {

    @Test
    public void loadsGroups() {
        SelectGroup selectGroup = Robolectric.buildActivity(SelectGroup.class).get();
        GroupsLoader groupsLoader = new GroupsLoader(selectGroup, "9c3d5770-57d8-4365-994c-69c5ac4186ee");
        Collection<OfficeGroup> officeGroups = groupsLoader.loadInBackground();
        Assertions.assertThat(officeGroups).hasSize(20);
    }
}
