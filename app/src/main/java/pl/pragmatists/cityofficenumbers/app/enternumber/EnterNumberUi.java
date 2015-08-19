package pl.pragmatists.cityofficenumbers.app.enternumber;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.offices.OfficeGroupsFetched;

public class EnterNumberUi {
    private final int groupId;

    private final EnterNumberView enterNumberView;

    public EnterNumberUi(int groupId, EnterNumberView enterNumberView) {

        this.groupId = groupId;
        this.enterNumberView = enterNumberView;
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        OfficeGroup officeGroup = officeGroupsFetched.getOfficeGroups().find(groupId);
        int currentNumber = officeGroup.currentNumber();
        enterNumberView.setCurrentNumber(String.valueOf(currentNumber));
    }

}
