package in.com.arbortag.network.callbacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import in.com.arbortag.IBaseModel;
import in.com.arbortag.network.ErrorCodes;
import retrofit2.Call;
import retrofit2.Response;


public class ObjectParserCallback <V extends IBaseModel> extends BaseNetworkCallBack<JsonObject, V> {

    public ObjectParserCallback(UiCallBack uiCallBack, Class<V> aClass) {
        super(uiCallBack, aClass);
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

        if (!isValidResponse(response)) {
            return;
        }

        //JsonArray content = response.body().getAsJsonArray("content");
        //if (content.size() > 0) {
        ///  JsonObject asJsonObject = content.get(0).getAsJsonObject();
        JsonObject asJsonObject = response.body().getAsJsonObject();
        String jsonString = asJsonObject.toString();
        V classObject = new Gson().fromJson(jsonString, clazz);
        uiCallBack.onSuccess(classObject);
        // }
    }

    boolean isValidResponse(Response<JsonObject> response) {
        if (!response.isSuccessful()) {
            switch (response.code()) {
                case 500:
                    uiCallBack.onError(ErrorCodes.SERVER_ERROR, "");
                    break;
            }
            return false;
        }
        JsonObject body = response.body();
        if (!isSuccess(body)) {
            uiCallBack.onError(ErrorCodes.SERVER_ERROR, "");
            return false;
        }
        return true;
    }

    private boolean isSuccess(JsonObject body) {
       /* JsonObject jsonObject = body.getAsJsonObject("response");
        int successCode = jsonObject.getAsJsonPrimitive("error").getAsInt();*//*
        return successCode == 0;*/
        return true;
    }
}
