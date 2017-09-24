package in.com.arbortag;


import in.com.arbortag.network.ErrorCodes;

public interface ArborTagView {

    void showProgress();

    void hideProgress();

    void onError(ErrorCodes errorCode, String message);

    void onError(ErrorCodes errorCode, int message);
}
