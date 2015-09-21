package pl.pragmatists.cityofficenumbers.enternumber;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class EnterNumberPresenter {
    private final int groupId;

    private final EnterNumberView enterNumberView;

    private final EventBus bus;

    private OfficeGroup officeGroup;

    public EnterNumberPresenter(int groupId, EnterNumberView enterNumberView, EventBus bus) {
        this.groupId = groupId;
        this.enterNumberView = enterNumberView;
        this.bus = bus;
    }

    EnterNumberPresenter(OfficeGroup officeGroup, EnterNumberView enterNumberView, EventBus bus) {
        this.bus = bus;
        this.groupId = 0;
        this.enterNumberView = enterNumberView;
        this.officeGroup = officeGroup;
    }

    public void onEventMainThread(OfficeGroupsFetched officeGroupsFetched) {
        officeGroup = officeGroupsFetched.getOfficeGroups().find(groupId);
        enterNumberView.setCurrentNumber(officeGroup.groupLetter() + officeGroup.currentNumber());
        enterNumberView.setQueueSize(String.valueOf(officeGroup.queueSize()));
        numberEntered(enterNumberView.getUserNumber());
        bus.post(new RequestStatsUpdate(groupId));
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
