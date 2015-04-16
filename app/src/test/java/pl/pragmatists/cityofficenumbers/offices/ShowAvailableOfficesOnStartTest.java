package pl.pragmatists.cityofficenumbers.offices;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.SelectOffice;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ShowAvailableOfficesOnStartTest {

    @Test
    public void shows_available_offices_on_start() {
        SelectOffice selectOffice = Robolectric.buildActivity(SelectOffice.class).create().visible().get();
        selectOffice.showNoOfficesAvailableMessage();
        TextView noOfficesText = (TextView) selectOffice.findViewById(R.id.no_offices);
        Assertions.assertThat(noOfficesText.getText().toString()).isEqualTo("No offices");
    }


}