package in.com.arbortag.network.callbacks;



import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;

import in.com.arbortag.IBaseModel;
import in.com.arbortag.network.ErrorCodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class BaseNetworkCallBack <V, T extends IBaseModel> implements Callback<V> {

    UiCallBack uiCallBack;
    Type clazz;
    BaseNetworkCallBack(UiCallBack uiCallBack, Class<T> clazz) {
        this.uiCallBack = uiCallBack;
        this.clazz = clazz;
    }

    @Override
    public void onResponse(Call<V> call, Response<V> response) {

    }

    @Override
    public void onFailure(Call<V> call, Throwable t) {
        if (t instanceof IOException) {
            uiCallBack.onError(ErrorCodes.NO_NETWORK, "");
        } else if (t instanceof JSONException) {
            uiCallBack.onError(ErrorCodes.JSON_EXCEPTION, "");
        }
    }
}
