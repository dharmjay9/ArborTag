package in.com.arbortag;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;



public class ArborTagApp extends MultiDexApplication {
    private static Context context;
    public GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;
    @Override
    public void onCreate() {
        super.onCreate();
       // Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        context = this;
        CalligraphyConfig builder = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AVENIRLTSTD-BOOK.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
        CalligraphyConfig.initDefault(builder);
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }*/
       // enabledStrictMode();
        //LeakCanary.install(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return context;
    }
}
