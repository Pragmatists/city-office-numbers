package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Context;
import android.widget.ArrayAdapter;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class GroupsListUi extends ArrayAdapter<OfficeGroup> {

    public GroupsListUi(Context context) {
        super(context,  R.layout.office_group_item);
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        clear();
        addAll(officeGroupsFetched.groups());
    }

}
