package in.com.arbortag;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import in.com.arbortag.Utils.Helper;
import in.com.arbortag.network.ErrorCodes;

import static in.com.arbortag.network.ErrorCodes.NO_NETWORK;


public abstract class BaseFragment extends Fragment implements ArborTagView {
    boolean isActivityFinished;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View views = getViews(inflater, container, savedInstanceState);
        return views;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (doRefreshOnLoad()) {
            refresh();
        }
    }

//    protected abstract void initViews();

    protected boolean doRefreshOnLoad() {
        return true;
    }

    protected abstract void refresh();


    ProgressDialog progressDialog;

    public void dismissProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;
    }

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(message);
        }
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public ProgressDialog showProgressDialog(String title, String message) {
        progressDialog = Helper.showProgressDialog(title, message, getContext());
        return progressDialog;
    }

    public void showNoInternetDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppTheme_Light_Dialog);
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

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onDestroyView() {
        this.isActivityFinished = true;
        dismissProgressDialog();
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroyView();
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


   /* protected boolean displayErrorEmail(EditText editText, TextInputLayout textInputLayout, int id) {
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
    }*/

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
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                switch (errorCode) {
                    case NO_NETWORK:
                        showNoInternetDialog();
                        break;
                    default:
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected abstract BasePresenter getPresenter();
    public void onError(final ErrorCodes errorCode, final int message) {
        onError(errorCode,getResources().getString(message));
    }

    public void showNoInternetDialog(Dialog.OnClickListener listener) {
        AlertDialog dialog = Helper.showNoInternetDialog(getContext(), listener);
    }

    public abstract View getViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
