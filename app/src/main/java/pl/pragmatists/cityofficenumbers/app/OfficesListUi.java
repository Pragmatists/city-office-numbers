package pl.pragmatists.cityofficenumbers.app;

import android.content.Context;
import android.widget.ArrayAdapter;

public class OfficesListUi extends ArrayAdapter<OfficeGroup> {
    public OfficesListUi(Context context) {
        super(context,  R.layout.office_group_item);
    }


    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        addAll(officeGroupsFetched.groups());
    }

}