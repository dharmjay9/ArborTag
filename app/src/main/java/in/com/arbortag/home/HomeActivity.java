package in.com.arbortag.home;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import in.com.arbortag.BaseActivity;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.ILocationChanged;
import in.com.arbortag.R;
import in.com.arbortag.database.StoredObjectValue;
import in.com.arbortag.map.HomeMapFragment;
import in.com.arbortag.nfc.AppLocationService;
import in.com.arbortag.nfc.ArborTagNFCWrite;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener, ILocationChanged {
    private HomeMapFragment mHomeFragment;
    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoogleApiClient  mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        LocationRequest  mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }
    @Override
    protected void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorTextWhite));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // callHomeFragment();
        mHomeFragment = HomeMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.content_home, mHomeFragment).addToBackStack(null).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           /* if (mHomeFragment.isVisible()) {
                finish();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                List<Fragment> list = fragmentManager.getFragments();
                fragmentManager.beginTransaction().replace(R.id.content_home, list.get(0)).commit();
            }*/
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                this.finish();
            }
        }
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

     /*   //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.location);
        //System.out.print("onNavigationItemSelected---" + id);
        switch (id) {
            case R.id.nav_location:
                callHomeFragment();
                break;

            case R.id.nav_logout:

                break;
            case R.id.nav_slideshow:
                startActivity(ArborTagNFCWrite.class);
                break;
            case R.id.nav_histroy:
                break;

            case R.id.nav_aboutus:
                break;

            case R.id.nav_rate:

                break;
            case R.id.nav_share:
                shareApplication();
                break;
        }
        if (fragment != null) {

           //   String backStateName = this.getClass().getName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_home, fragment).addToBackStack(null);//.addToBackStack(null)
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_home,
                        HomeMapFragment.newInstance(),
                        HomeMapFragment.TAG).addToBackStack(null)
                .commit();
    }

    void shareApplication() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "SampleScan");
            String sAux = "\nCheck this application out\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivity(Class<?> calledActivity) {
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
        in.com.arbortag.core.Location location1 = new in.com.arbortag.core.Location();
        location1.setLatitude(location.getLatitude());
        location1.setLongitude(location.getLongitude());
        StoredObjectValue.getInstance().setLocation(location1);
    }


    @Override
    protected void refresh() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
