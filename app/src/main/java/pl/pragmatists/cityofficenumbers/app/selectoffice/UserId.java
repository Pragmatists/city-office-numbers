package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.content.ContentResolver;
import android.provider.Settings;

public class UserId {
    private final ContentResolver contentResolver;

    public UserId(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public String get() {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
    }
}
