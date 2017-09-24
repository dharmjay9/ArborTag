package in.com.arbortag.registation;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.com.arbortag.BaseActivity;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.R;
import in.com.arbortag.core.User;

public class RegistrationActivity extends BaseActivity<RegistrationPresenter> implements RegistrationView {
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    Button _signupButton;
    RelativeLayout _uploadPhotos;
    ImageButton passShowHideBtn;
    boolean passHideShow = false;
    private EditText mFirstnameText, mLastnameText, mEmail, mPassword, mConfirmPassword, mMobileNo;
    private TextInputLayout mFullNameTll, mEmailTll, mMobileTll, mPasswordTll,
            mConfirmPasswordTll, mDeviceNoTll, mDeviceNicknameTll;
    RegistrationPresenterImpl registrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registrationPresenter = new RegistrationPresenterImpl(this);
    }

    @Override
    protected void initViews() {
        mFirstnameText = (EditText) findViewById(R.id.sign_first_name);
        mLastnameText = (EditText) findViewById(R.id.sign_last_name);
        mEmail = (EditText) findViewById(R.id.sign_email_address);
        mMobileNo = (EditText) findViewById(R.id.sign_mobile_number);
        mPassword = (EditText) findViewById(R.id.sign_password);
        mConfirmPassword = (EditText) findViewById(R.id.sign_confrom_password);
        _signupButton = (Button) findViewById(R.id.btn_signup_account);
        _uploadPhotos = (RelativeLayout) findViewById(R.id.topLayout);
        passShowHideBtn = (ImageButton) findViewById(R.id.pastePin);
        findViewById(R.id.btn_signup_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        findViewById(R.id.link_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void refresh() {

    }

    private void register() {
      /*  if (displayError(mFirstnameText, mFullNameTll, R.string.ERR_EMPTY_FIRST_NAME)) {
            return;
        }
        if (displayErrorEmail(mEmail, mEmailTll, R.string.ERR_CRT_EMAIL)) {
            return;
        }
        if (displayError(mPassword, mPasswordTll, R.string.ERR_EMPTY_PASSWORD_1)) {
            return;
        }
        if (displayError(mPassword, mPasswordTll, R.string.ERR_EMPTY_PASSWORD, 5)) {
            return;
        }
        if (displayError(mConfirmPassword, mConfirmPasswordTll, R.string.ERR_EMPTY_PASSWORD_1)) {
            return;
        }
        if (displayError(mConfirmPassword, mConfirmPasswordTll, R.string.ERR_EMPTY_CONFIRM_PASSWORD, 5)) {
            return;
        }
        if (!(mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))) {
            Toast.makeText(getApplicationContext(), "Password and Confirm Password not matching", Toast.LENGTH_SHORT).show();
            return;
        }
        if (displayErrorPhn(mMobileNo, mMobileTll, R.string.ERR_EMPTY_PHONE_NO, 9)) {
            return;
        }*/
        User registration = new User();
        registration.setFirstName(mFirstnameText.getText().toString());
        registration.setLastName(mLastnameText.getText().toString());
        registration.setEmail(mEmail.getText().toString());
        registration.setMobile(mMobileNo.getText().toString());
        registration.setPassword(mPassword.getText().toString());
        registration.setConfirmPassword(mConfirmPassword.getText().toString());
        registrationPresenter.doRegistration(registration);
    }

    @Override
    protected BasePresenter getPresenter() {
        return registrationPresenter;
    }

    @Override
    public void onRegistrationSuccess(String registaration) {

    }
}
