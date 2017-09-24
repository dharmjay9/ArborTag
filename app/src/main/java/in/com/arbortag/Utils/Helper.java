package in.com.arbortag.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.com.arbortag.R;


public class Helper {

    public static String getDate(String dateinput)
    {
        String dateOutput="";
        try
        {
            //2015-02-14 00:00:00
            // (1) get today's date
            // (2) create our date "formatter" (the date format we want)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            // (3) create a new String using the date format we want
            Date date = formatter.parse(dateinput);
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            dateOutput=formatter1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOutput;
    }

    public static String getTodayDate()
    {
        String dateOutput="";
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateOutput=formatter.format(date);
        return dateOutput;
    }

    public static ProgressDialog showProgressDialog(String message, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public static ProgressDialog showProgressDialog(String title, String message, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public static AlertDialog showNoInternetDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.AppTheme_Light_Dialog);
        builder.setCancelable(true);
        builder.setTitle(R.string.no_internet_title);
        builder.setMessage(R.string.no_internet_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showNoInternetDialog(Context context, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(R.string.no_internet_title);
        builder.setMessage(R.string.no_internet);
        builder.setPositiveButton(R.string.ok,listener);
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDialog(Context context, int title, int message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDialog(Context context, String title, String message, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, listener);
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDialog(Context context, int title, int message, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, listener);
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDialog(Context context, String title, String message, String positiveText, String negativeText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveListener);
        builder.setNegativeButton(negativeText, negativeListener);
        AlertDialog dialog=builder.create();
        dialog.show();
        return dialog;
    }

    public static void showNoConnectionSnackBar(View view)
    {
        Snackbar snackbar = Snackbar
                .make(view, view.getContext().getString(R.string.no_internet), Snackbar.LENGTH_LONG);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static void showSnackBar(View view, String message)
    {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static void showSnackBar(View view, int message)
    {
        Snackbar snackbar = Snackbar
                .make(view, view.getContext().getString(message), Snackbar.LENGTH_LONG);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

}
