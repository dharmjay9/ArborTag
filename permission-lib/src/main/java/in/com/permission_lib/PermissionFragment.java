package in.com.permission_lib;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class PermissionFragment extends Fragment {

    private PermissionCallBack mPermissionCallBack;
    private static final int REQUEST_FOR_PERMISSION = 3;
    private String[] mDefinedPermission;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setPermission();
    }


    public void requestForPermission(PermissionCallBack permissionCallBack, String... permissions) {
        mPermissionCallBack = permissionCallBack;
        List<String> permissionList = new ArrayList<>();
        List<String> permissionNotDefine = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGRanted(permission)) {
                permissionList.add(permission);
            }
            if (permissionNotDefine(permission)) {
                permissionNotDefine.add(permission);
            }
        }
        if (permissionNotDefine.size() != 0)
            mPermissionCallBack.permissionNotInManifest(permissionNotDefine.toArray(new String[permissionList.size()]));
        if (permissionList.size() == 0) {
            mPermissionCallBack.onError(ErrorCode.ALL_PERMISSION_ARE_GRANTED);
            return;
        }
        requestPermissions((permissionList.toArray(new String[permissionList.size()])), REQUEST_FOR_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FOR_PERMISSION) {

            List<String> approvedList = new ArrayList<>();
            List<String> deniedList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    approvedList.add(permissions[i]);
                else deniedList.add(permissions[i]);
            }
            mPermissionCallBack.permissionDenied(deniedList.toArray(new String[deniedList.size()]));
            mPermissionCallBack.permissionApproved(approvedList.toArray(new String[approvedList.size()]));
        }
    }

    public boolean isPermissionGRanted(String permissionsName) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mContext, permissionsName) ? true : false;
    }

    public void checkAllPermission(Set<String> permissionSet,CheckPermission permissionCallBack){

        Iterator<String> iterator=permissionSet.iterator();
        if(permissionSet.size()==0){
            permissionCallBack.pemissionCallBack(PemissionStatus.PERMISSION_SET_EMPTY);
        }
        int grantedPermission=0;
        while (iterator.hasNext()){
            if(isPermissionGRanted(iterator.next())){
                grantedPermission++;
            }
        }
        if(grantedPermission==permissionSet.size()){
            permissionCallBack.pemissionCallBack(PemissionStatus.ALL_PERMISSION_GRANTED);
        }
        else if(grantedPermission==0){
            permissionCallBack.pemissionCallBack(PemissionStatus.ALL_PERMISSION_NOT_GRANTED);
        }
        else {
            permissionCallBack.pemissionCallBack(PemissionStatus.FEW_PERMISSION_GRANTED);
        }

    }
    public boolean isPermissionGRanted() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS);

    }

    private void setPermission() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                mDefinedPermission = info.requestedPermissions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean permissionNotDefine(String permissionName) {
        for (String permission : mDefinedPermission) {

            if (permissionName.equals(permission)) return false;

        }
        return true;
    }

}
