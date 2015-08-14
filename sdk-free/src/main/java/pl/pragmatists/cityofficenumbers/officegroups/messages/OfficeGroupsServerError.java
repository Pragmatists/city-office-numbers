package pl.pragmatists.cityofficenumbers.officegroups.messages;

public class OfficeGroupsServerError {
    public final String details;

    public OfficeGroupsServerError(String details) {
        this.details = details;
    }
}
