package in.com.arbortag.map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import in.com.arbortag.ArborTagApp;
import in.com.arbortag.BaseFragment;
import in.com.arbortag.BasePresenter;
import in.com.arbortag.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMapFragment extends BaseFragment implements OnMapReadyCallback {

    public static final String TAG = HomeMapFragment.class.getSimpleName();
    private MapView mapView = null;
    // private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    private double latitude, longitude;


    public static HomeMapFragment newInstance() {
        return new HomeMapFragment();
    }

    public HomeMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_map, container, false);
        View view = inflater.inflate(R.layout.fragment_home_map, container, false);

        mapView = (MapView) view.findViewById(R.id.map_sub_user);

        mapView.onCreate(savedInstanceState);

        if (mapView != null) {
            mapView.getMapAsync(this);
        }

        return view;
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady");
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(ArborTagApp.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ArborTagApp.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng bandipur = new LatLng(11.7722, 76.4539);
        mMap.addMarker(new MarkerOptions().position(bandipur).title("Bandipur").snippet("Bandipur plantation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bandipur));

        LatLng sydney = new LatLng(12.2958104, 76.6393805);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Mysore").snippet("Mysore plantation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
