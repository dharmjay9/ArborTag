package in.com.arbortag.login;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.location.LocationListener;
import com.google.gson.Gson;

import in.com.arbortag.BaseActivity;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.ILocationChanged;
import in.com.arbortag.R;
import in.com.arbortag.core.User;
import in.com.arbortag.database.StoredObjectValue;
import in.com.arbortag.home.HomeActivity;
import in.com.arbortag.registation.RegistrationActivity;

public class LoginActivity extends BaseActivity implements LoginView, LocationListener, ILocationChanged {
    TextView tvLink_signup;
    private LoginPresenterImpl loginPresenter;
    EditText mUserName, mLoginPassword;
    private TextInputLayout mUserNameTll, mLoginPasswordTll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


    }

    @Override
    protected void initViews() {
        mUserName = (EditText) findViewById(R.id.input_email_mobile);
        loginPresenter = new LoginPresenterImpl(this);
        mUserName.requestFocus();
        mLoginPassword = (EditText) findViewById(R.id.input_password);

        mLoginPasswordTll = (TextInputLayout) findViewById(R.id.login_password_tll);
        mUserNameTll = (TextInputLayout) findViewById(R.id.user_name_tll);
        setTextWatcher(mUserName, mUserNameTll);
        setTextWatcher(mLoginPassword, mLoginPasswordTll);
        tvLink_signup = (TextView) findViewById(R.id.link_signup);
        tvLink_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start(RegistrationActivity.class);
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("admin".equalsIgnoreCase(mUserName.getText().toString())){
                    if("admin".equalsIgnoreCase(mLoginPassword.getText().toString())){
                        User user=new User();
                        user.setEmail(mUserName.getText().toString());
                        user.setPassword(mLoginPassword.getText().toString());
                        StoredObjectValue.getInstance().storeProfileObject(user);
                        start(HomeActivity.class);
                    }else{
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.ERR_EMPTY_PASSWORD), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.ERR_EMPTY_EMAIL), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void refresh() {
        login();
    }

    private void login() {
        mUserNameTll.setErrorEnabled(false);
        mLoginPasswordTll.setErrorEnabled(false);
        if (displayErrorPhn(mUserName, mUserNameTll, R.string.ERR_EMPTY_USER_NAME, 9)) {
            return;
        }
        if (displayError(mLoginPassword, mLoginPasswordTll, R.string.ERR_EMPTY_PASSWORD)) {
            return;
        }
        String mobile = mUserName.getText().toString();
        String password = mLoginPassword.getText().toString();
        User login = new User();
        login.setMobile(mobile);
        login.setPassword(password);
       // loginPresenter.doLogin(login);
    }

    @Override
    protected BasePresenter getPresenter() {
        return loginPresenter;
    }

    @Override
    public void onLoginSuccess() {

    }

    public void start(Class<?> calledActivity) {
        Intent myIntent = new Intent(this, calledActivity);
        this.startActivity(myIntent);
    }

    @Override
    public void onLocationChanged(Location location) {
        in.com.arbortag.core.Location location1 = new in.com.arbortag.core.Location();
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        StoredObjectValue.getInstance().setLocation(location1);
    }

    @Override
    public void LocationChanged(Location location) {

        Gson gson = new Gson();
        in.com.arbortag.core.Location location1 = new in.com.arbortag.core.Location();
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        String content = gson.toJson(location1);
        in.com.arbortag.core. Location userLocation = gson.fromJson(content, in.com.arbortag.core.Location.class);
        StoredObjectValue.getInstance().setLocation(userLocation);
    }
}
