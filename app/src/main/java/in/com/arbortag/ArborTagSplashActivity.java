package in.com.arbortag;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import in.com.arbortag.database.StoredObjectValue;
import in.com.arbortag.home.HomeActivity;
import in.com.arbortag.login.LoginActivity;
import in.com.permission_lib.PermissionFragment;

public class ArborTagSplashActivity extends BaseSplashActivity {
    private String screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arbor_tag_splash);
        screen = getIntent().getStringExtra("screenType");
        checkPermissions();

    }

    @Override
    protected int getSplashScreenViewId() {
        return R.layout.activity_arbor_tag_splash;
    }

    @Override
    protected int getDelayTime() {
        return 2000;
    }

    @Override
    protected void doLogic() {
        refresh();
    }

    @Override
    protected Class getLauncherHomeActivity() {
        if (StoredObjectValue.getInstance().isLoggedIn()) {
            return HomeActivity.class;
        }
        return LoginActivity.class;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void refresh() {
        // showHomeActivity();
    }

    private void checkPermissions() {
        if (mPermissionFragment.isPermissionGRanted(android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                mPermissionFragment.isPermissionGRanted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            gotoNext();
            return;
        }

        mPermissionFragment.requestForPermission(
                new PermissionImpl() {
                    @Override
                    public void permissionApproved(String... permissions) {
                        super.permissionApproved(permissions);
                        if (mPermissionFragment.isPermissionGRanted(android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                                mPermissionFragment.isPermissionGRanted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            gotoNext();
                        } else {

                            displayPermissionDenied();
                        }
                    }

                    @Override
                    public void onApproved(String approved) {

                    }
                }, android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    private void gotoNext() {
        showHomeActivity();

    }

    private void displayPermissionDenied() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Please enable your application permission.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        checkPermissions();
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
