package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.offices.OfficeGroupsFetched;

public class GroupsListUi extends ArrayAdapter<OfficeGroup> {

    private final LayoutInflater layoutInflater;

    public GroupsListUi(Context context) {
        super(context,  R.layout.office_group_item);
        layoutInflater = LayoutInflater.from(context);
    }


    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        addAll(officeGroupsFetched.groups());
    }

}
