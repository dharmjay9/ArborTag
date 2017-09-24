package in.com.arbortag.nfc;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.util.Date;

import in.com.arbortag.BaseActivity;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.ILocationChanged;
import in.com.arbortag.R;
import in.com.arbortag.database.StoredObjectValue;
import in.com.arbortag.home.HomeActivity;

public class ArborTagNFCWrite extends BaseActivity implements LocationListener, ILocationChanged {
    PillowNfcManager nfcManager;
    WriteTagHelper writeHelper;
    EditText write_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arbor_tag_nfcwrite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });

      // callGetLocation();
    }

    private void callGetLocation() {
        AppLocationService   appLocationService = new AppLocationService(ArborTagNFCWrite.this);

        Location gpsLocation = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            Toast.makeText(
                    getApplicationContext(),
                    "Mobile Location (GPS): \nLatitude: " + latitude
                            + "\nLongitude: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            showSettingsAlert("GPS");
        }
    }

    private void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        ArborTagNFCWrite.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
      protected void initViews() {
        nfcManager = new PillowNfcManager(this);
        nfcManager.onActivityCreate();
        write_text= (EditText) findViewById(R.id.write_text_id);

        nfcManager.setOnTagReadListener(new PillowNfcManager.TagReadListener() {
            @Override
            public void onTagRead(String tagRead) {
                Toast.makeText(ArborTagNFCWrite.this, tagRead, Toast.LENGTH_LONG).show();
            }
        });

        writeHelper= new WriteTagHelper(this, nfcManager);
        nfcManager.setOnTagWriteErrorListener(writeHelper);
        nfcManager.setOnTagWriteListener(writeHelper);
//		// If don't want to use the Write helper you can use the following code
//		nfcManager.setOnTagWriteListener(new TagWriteListener() {
//			@Override
//			public void onTagWritten() {
//				Toast.makeText(SampleActivity.this, "tag writen", Toast.LENGTH_LONG).show();
//			}
//		});
//		nfcManager.setOnTagWriteErrorListener(new TagWriteErrorListener() {
//			@Override
//			public void onTagWriteError(NFCWriteException exception) {
//				Toast.makeText(SampleActivity.this, exception.getType().toString(), Toast.LENGTH_LONG).show();
//			}
//		});

        findViewById(R.id.write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeHelper.writeText(write_text.getText().toString()+","+ StoredObjectValue.getInstance().getLocation().getLatitude()
                        +","+ StoredObjectValue.getInstance().getLocation().getLongitude() );
//				// If don't want to use the Write helper you can use the following code
//				nfcManager.writeText(text);
                String t = new Date().toString();
            }
        });
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcManager.onActivityResume();
    }

    @Override
    protected void onPause() {
        nfcManager.onActivityPause();
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent){
        nfcManager.onActivityNewIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
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
        in.com.arbortag.core.Location location1 = new in.com.arbortag.core.Location();
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        StoredObjectValue.getInstance().setLocation(location1);
    }
}
