package pl.pragmatists.cityofficenumbers.officegroups.messages;

public class RestServerError {
    public final String details;

    public RestServerError(String details) {
        this.details = details;
    }
}
