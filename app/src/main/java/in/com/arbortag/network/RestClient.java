package in.com.arbortag.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import in.com.arbortag.core.User;
import in.com.arbortag.network.callbacks.ObjectParserCallback;
import in.com.arbortag.network.callbacks.UiCallBack;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient implements IBaseNetwork {
    private static RestClient _instance = null;
    private ApiCall ibusBackend;
    Retrofit retrofit;

    private RestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("")//BuildConfig.BASE_URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ibusBackend = retrofit.create(ApiCall.class);

    }

    public ApiCall getIbusBackend() {
        return ibusBackend;
    }

    public static RestClient getInstance() {
        if (_instance == null) {
            _instance = new RestClient();
        }
        return _instance;
    }

    public void signUp(User user, UiCallBack<User> userUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getRegister(user);
        responseCall.enqueue(new ObjectParserCallback<User>(userUiCallBack, User.class));

    }

   /* @Override




    @Override
    public void doRegistration(User registerRequest, final UiCallBack<User> registerResponseUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getRegister(registerRequest);
        responseCall.enqueue(new ObjectParserCallback<User>(new UiCallBack<User>() {
            @Override
            public void onSuccess(User user) {
                //StoredObjectValue.getInstance().storeProfileObject(user);
                //TODO save
                registerResponseUiCallBack.onSuccess(user);
            }

            @Override
            public void onError(ErrorCodes errorCode, String message) {
                registerResponseUiCallBack.onError(errorCode, message);
            }
        }, User.class));

    }

    @Override
    public void getUserProfile(String profile_user_id, UiCallBack<User> registerResponseUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getUserProfile(profile_user_id);
        responseCall.enqueue(new ObjectParserCallback<User>(registerResponseUiCallBack, User.class));
    }


    @Override
    public void updateUserProfile(User user, UiCallBack<User> userPofileUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.updateUserProfile(user,""+user.getUserId());
        responseCall.enqueue(new ObjectParserCallback<User>(userPofileUiCallBack,User.class));
    }

    @Override
    public void getVenueDetailInfo(VenueDetailInfo venueDetailInfo, UiCallBack<VenueDetailInfo> venueDetailInfoUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getVenues(Double.parseDouble(venueDetailInfo.getLat()), Double.parseDouble(venueDetailInfo.getLng()));
        responseCall.enqueue(new ObjectParserCallback<VenueDetailInfo>(venueDetailInfoUiCallBack, VenueDetailInfo.class));
    }

    @Override
    public void getDevicesInfo(UiCallBack<List<WifiDeviceInfo>> wifiDeviceInfoUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getAllDevicesInfo();
        responseCall.enqueue(new ObjectListParserCallback<WifiDeviceInfo>(wifiDeviceInfoUiCallBack, WifiDeviceInfo.class));
    }

    @Override
    public void getOfferByDevice(WifiDeviceInfo wifiDeviceInfo, UiCallBack<WifiDeviceInfo> deviceInfoUiCallBack) {
        Call<JsonObject> responseCall = ibusBackend.getOfferByDevice(wifiDeviceInfo);
        responseCall.enqueue(new ObjectParserCallback<WifiDeviceInfo>(deviceInfoUiCallBack, WifiDeviceInfo.class));
    }

    @Override
    public void editProfile(User userDetails, UiCallBack<User> uiCallBack) {
        Call<JsonObject> call = ibusBackend.editUser(userDetails, userDetails.getUserId());
        call.enqueue(new ObjectParserCallback<User>(uiCallBack,User.class));
    }
*/
}
