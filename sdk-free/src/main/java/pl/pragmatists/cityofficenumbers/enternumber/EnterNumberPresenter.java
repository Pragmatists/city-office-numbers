package pl.pragmatists.cityofficenumbers.enternumber;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.offices.OfficeGroupsFetched;

public class EnterNumberPresenter {
    private final int groupId;

    private final EnterNumberView enterNumberView;

    private OfficeGroup officeGroup;

    public EnterNumberPresenter(int groupId, EnterNumberView enterNumberView) {
        this.groupId = groupId;
        this.enterNumberView = enterNumberView;
    }

    EnterNumberPresenter(OfficeGroup officeGroup, EnterNumberView enterNumberView) {
        this.groupId = 0;
        this.enterNumberView = enterNumberView;
        this.officeGroup = officeGroup;
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        officeGroup = officeGroupsFetched.getOfficeGroups().find(groupId);
        enterNumberView.setCurrentNumber(officeGroup.groupLetter() + officeGroup.currentNumber());
        enterNumberView.setQueueSize(String.valueOf(officeGroup.queueSize()));
        numberEntered(enterNumberView.getUserNumber());
    }

    public void numberEntered(String newNumber) {
        if (newNumber.isEmpty()) {
            return;
        }
        enterNumberView.setUsersNumber(officeGroup.groupLetter() + newNumber);
        int usersNumber = Integer.parseInt(newNumber);
        enterNumberView.setQueueBeforeSize(String.valueOf(officeGroup.distanceTo(usersNumber)));
        enterNumberView.setExpectedTime(String.valueOf(officeGroup.expectedWaitingTime(usersNumber)));
    }

}
