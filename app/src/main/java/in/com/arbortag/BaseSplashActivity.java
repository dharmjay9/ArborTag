package in.com.arbortag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.com.permission_lib.PermissionFragment;


public abstract class BaseSplashActivity extends BaseActivity {
    protected PermissionFragment mPermissionFragment;
    private boolean isFromNotification;
    private static final String HEADLESS_FRAGMENT = "head_less_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromNotification = getIntent().getBooleanExtra("isFromNotification", false);
        mPermissionFragment = (PermissionFragment) getSupportFragmentManager().findFragmentByTag(HEADLESS_FRAGMENT);
        if (mPermissionFragment == null) ;
        {
            mPermissionFragment = new PermissionFragment();
            getSupportFragmentManager().beginTransaction().add(mPermissionFragment, HEADLESS_FRAGMENT).commitNow();
        }
    }


    protected abstract int getSplashScreenViewId();

    protected abstract int getDelayTime();

    public void showHomeActivity() {
        int delay = getDelayTime();

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class launcherHomeActivity = getLauncherHomeActivity();
                if (launcherHomeActivity == null) {
                    return;
                }

                Intent mIntent = new Intent(BaseSplashActivity.this, launcherHomeActivity);

                if (isFromNotification()) {
                    if (getExtrasIntent() != null) {
                        Bundle extras = getExtrasIntent().getExtras();
                        mIntent.putExtras(extras);
                    }
                }
                startActivity(mIntent);
                finish();
            }
        }, delay);
    }
    protected boolean isFromNotification() {
        return isFromNotification;
    }

    @Override
    protected void refresh() {

    }

    protected Intent getExtrasIntent() {
        return null;
    }

    protected abstract void doLogic();

    protected abstract Class getLauncherHomeActivity();


}
