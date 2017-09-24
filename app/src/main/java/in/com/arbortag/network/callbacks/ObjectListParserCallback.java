package in.com.arbortag.network.callbacks;

import com.google.gson.JsonObject;

import java.util.List;

import in.com.arbortag.IBaseModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dell_PC on 5/12/2017.
 */

public class ObjectListParserCallback <V extends IBaseModel> extends ObjectParserCallback<V> {

    public ObjectListParserCallback(UiCallBack<List<V>> uiCallBack, Class<V> aClass) {
        super(uiCallBack, aClass);
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        if (!isValidResponse(response)) {
            return;
        }

    }
}
