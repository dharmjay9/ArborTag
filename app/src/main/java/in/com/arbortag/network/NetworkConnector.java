package in.com.arbortag.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import in.com.arbortag.R;


/**
 * Created by Dell_PC on 5/12/2017.
 */

public class NetworkConnector {
    public static boolean isNetworkAvailable(Context context) {
        try {
            if (context == null) {
                return false;
            }
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo == null) {
                    return false;
                } else if (netInfo.isConnected()) {
//                    Toast.makeText(context, "Network Connected.... ", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (!TextUtils.isEmpty(netInfo.getExtraInfo()) && netInfo.getExtraInfo().equalsIgnoreCase(context.getString(R.string.app_name))) {
                    boolean connectedOrConnecting = netInfo.isConnectedOrConnecting();
                    Toast.makeText(context, "Default network[ " + connectedOrConnecting + " ]", Toast.LENGTH_SHORT).show();
                    return connectedOrConnecting;
                }
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Problem[ " + e + " ]", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
