package in.com.arbortag;


import in.com.permission_lib.ErrorCode;
import in.com.permission_lib.PermissionCallBack;

public abstract class PermissionImpl implements PermissionCallBack {
    @Override
    public void permissionApproved(String... permissions) {
        for (String approved : permissions) {
            onApproved(approved);
        }
    }

    public abstract void onApproved(String str);

    @Override
    public void permissionDenied(String... permissionName) {

    }

    @Override
    public void permissionNotInManifest(String... permissionName) {

    }

    @Override
    public void onError(ErrorCode errorCode) {

    }
}

