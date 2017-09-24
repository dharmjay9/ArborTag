package in.com.arbortag;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.com.arbortag.Utils.Helper;
import in.com.arbortag.network.ErrorCodes;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;




public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements ArborTagView {

    boolean isActivityFinished;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initViews();
    }

    protected abstract void initViews();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (doRefreshOnLoad())
            refresh();
        hideKeyboard();
    }

    protected boolean doRefreshOnLoad() {
        return true;
    }

    protected abstract void refresh();

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    ProgressDialog progressDialog;

    public void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;
    }

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
        }
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public ProgressDialog showProgressDialog(String title, String message) {
        progressDialog = Helper.showProgressDialog(title, message, this);
        return progressDialog;
    }

    public void showNoInternetDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme_Light_Dialog);
        builder.setTitle(R.string.no_internet_title);
        builder.setMessage(R.string.no_internet_message);
        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                refresh();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dismissProgressDialog();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public boolean emailVerification(String email) {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public  boolean isValidPhone(String phone) {
        String MobilePattern = "[0-9]{10}";
        boolean isValid = true;
        if (!phone.matches(MobilePattern)) {
            isValid = false;
        }
        if (isValid && !phone.isEmpty()) {
            long sum = 0;
            long input = Long.parseLong(phone);
            while (input != 0) {
                long lastdigit = input % 10;
                sum += lastdigit;
                input /= 10;
            }
            if (sum == 0)
                isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        this.isActivityFinished = true;
        hideKeyboard();
        dismissProgressDialog();
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }


    public boolean displayError(EditText editText, TextInputLayout textInputLayout, int id) {
        return displayError(editText, textInputLayout, id, 0);
    }

    protected boolean displayError(EditText editText, TextInputLayout textInputLayout, int id, int maxCount) {
        String s = editText.getText().toString().trim();
        if (s.isEmpty() || s.length() <= maxCount) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(id));
            return true;
        }
        textInputLayout.setErrorEnabled(false);
        return false;
    }

    protected boolean displayErrorPhn(EditText editText, TextInputLayout textInputLayout, int id, int maxCount) {
        String s = editText.getText().toString().trim();
        if (!isValidPhone(s)) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(id));
            return true;
        }
        textInputLayout.setErrorEnabled(false);
        return false;
    }


    protected boolean displayErrorMsg(EditText editText, TextInputLayout textInputLayout, int id, int minCount) {
        String s = editText.getText().toString();
        if (s.isEmpty() || s.length() <= minCount) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(id));
            return true;
        }
        textInputLayout.setErrorEnabled(false);
        return false;
    }


    protected boolean displayErrorEmail(EditText editText, TextInputLayout textInputLayout, int id) {
        String s = editText.getText().toString();
        if (s.isEmpty() || !emailVerification(s)) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getResources().getString(id));
            Drawable drawable = editText.getBackground().getConstantState().newDrawable();
            editText.setBackgroundDrawable(drawable);
            return true;
        }
        textInputLayout.setErrorEnabled(false);
        return false;
    }

    @Override
    public void showProgress() {
        showProgressDialog("Please wait...");
    }

    @Override
    public void hideProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onError(final ErrorCodes errorCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                switch (errorCode) {
                    case NO_NETWORK:
                        showNoInternetDialog();
                        break;
                    default:
                        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected abstract BasePresenter getPresenter();
    public void onError(final ErrorCodes errorCode, final int message) {
        onError(errorCode,getResources().getString(message));
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void showNoInternetDialog(Dialog.OnClickListener listener) {
        AlertDialog dialog = Helper.showNoInternetDialog(this, listener);
    }

    protected void setTextWatcher(final EditText editText, final TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable edt) {
                if (editText.getText().length() > 0) {
                    textInputLayout.setError(null);
                }
            }
        });
    }


}
