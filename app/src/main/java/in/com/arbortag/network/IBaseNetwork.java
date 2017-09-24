package in.com.arbortag.network;



import in.com.arbortag.core.User;
import in.com.arbortag.network.callbacks.UiCallBack;

/**
 * Created by Dell_PC on 5/12/2017.
 */

public interface IBaseNetwork {
    void signUp(User user, UiCallBack<User> userUiCallBack);
   /* void doRegistration(User registerRequest, UiCallBack<User> userUiCallBack);

    void getUserProfile(String s, UiCallBack<User> userPofileUiCallBack);

    void updateUserProfile(User user, UiCallBack<User> userPofileUiCallBack);

    void getVenueDetailInfo(VenueDetailInfo venueDetailInfo, UiCallBack<VenueDetailInfo> venueDetailInfoUiCallBack);

    void  getDevicesInfo(UiCallBack<List<WifiDeviceInfo>> wifiDeviceInfoUiCallBack);

    void  getOfferByDevice(WifiDeviceInfo deviceInfo, UiCallBack<WifiDeviceInfo> deviceInfoUiCallBack);



    void editProfile(User user, UiCallBack<User> userUiCallBack);*/
}
