package epitech.epiandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by girard_s on 12/01/2016 for Epitech.
 */
public class ApplicationExt extends Application {
    private static ApplicationExt instance;

    public static ApplicationExt getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}