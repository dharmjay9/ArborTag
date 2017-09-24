package in.com.arbortag.network;

import com.google.gson.JsonObject;

import in.com.arbortag.core.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Dell_PC on 5/12/2017.
 */

public interface ApiCall {
    @POST("user/registration")
    Call<JsonObject> getRegister(@Body User registerData);

    @GET("user/{user_id}")
    Call<JsonObject> getUserProfile(@Path("user_id") String user_id);

    @GET("venue/venues_list/{lat}/{longt}")
    Call<JsonObject> getVenues(@Path("lat") Double lat, @Path("longt") Double longt);

    @GET("list_ssid")
    Call<JsonObject> getAllDevicesInfo();


    /*@POST("offer/list_new")
    Call<JsonObject> getOfferByDevice(@Body WifiDeviceInfo postData);

    @PUT("user/{user_id}")
    Call<JsonObject> updateUserProfile(@Body User userData, @Path("user_id") String user_id);

    @PUT("user/{user_id}")
    Call<JsonObject> editUser(@Body User userData, @Path("user_id") String user_id);*/
}
