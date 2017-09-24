package in.com.arbortag.database;

import android.content.Context;

import com.google.gson.Gson;

import in.com.arbortag.ArborTagApp;
import in.com.arbortag.core.Location;
import in.com.arbortag.core.User;
import in.com.arbortag.network.IConsts;
import in.com.arbortag.shared_preferences.SharedPreferences;



public class StoredObjectValue {
    private static SharedPreferences preferences;
    private static StoredObjectValue _instance = null;

    private StoredObjectValue() {
        preferences = SharedPreferences.getSharedPreferences(ArborTagApp.getAppContext(), IConsts.USER_INFO_PREF, Context.MODE_PRIVATE);
    }

    public static StoredObjectValue getInstance() {
        if (_instance == null) {
            _instance = new StoredObjectValue();
        }
        return _instance;
    }
    public void storeProfileObject(User jsonObject) {
        preferences.putObject(IConsts.USER_INFO_PREF, jsonObject);
    }

    public boolean isLoggedIn() {
        return preferences.contains(IConsts.USER_INFO_PREF);
    }

    public void setLocation(Location location) {
       /* Gson gson = new Gson();
        Location location1 = new Location();
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        String content = gson.toJson(location1);*/
        //Location userLocation = gson.fromJson(content, Location.class);
        preferences.putObject(IConsts.USER_LOCATION, location);
    }

    /**
     * This method will store current Location
     *
     * @return User
     */
    public Location getLocation() {
        return preferences.getObject(IConsts.USER_LOCATION, Location.class);
    }

    public boolean hasLatLong() {
        return StoredObjectValue.getInstance().preferences.contains(IConsts.USER_LOCATION);
    }
}


