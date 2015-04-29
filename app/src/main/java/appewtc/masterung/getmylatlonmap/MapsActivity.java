package appewtc.masterung.getmylatlonmap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng centerMapLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpCenterMap();

        setUpMapIfNeeded();
    }   // onCreate

    private void setUpCenterMap() {

        double douCenterLng = getIntent().getExtras().getDouble("myLat");
        double douCenterLong = getIntent().getExtras().getDouble("myLong");
        centerMapLatLng = new LatLng(douCenterLng, douCenterLong);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }   // if2
        }   // if1
    }   // setUpMapIfNeeded

    private void setUpMap() {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMapLatLng, 15));
        mMap.addMarker(new MarkerOptions().position(centerMapLatLng).title("Me are Here"));

    }   // setUpMap
}
