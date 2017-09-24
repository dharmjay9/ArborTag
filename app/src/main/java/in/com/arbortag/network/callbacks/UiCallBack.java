package in.com.arbortag.network.callbacks;

import in.com.arbortag.network.ErrorCodes;

public interface UiCallBack<T> {

    void onSuccess(T t);

    void onError(ErrorCodes errorCode, String message);

}