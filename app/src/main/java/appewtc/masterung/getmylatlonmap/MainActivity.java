package appewtc.masterung.getmylatlonmap;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView txtLat, txtLon;
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean bolGPS, bolNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidget();

        //Create Instant LocationManager
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Create Instant Criteria
        createCriteria();

    }   // onCreate

    public void clickMove(View view) {
        Intent objIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(objIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bolGPS = objLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!bolGPS) {
            bolNetwork = objLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!bolNetwork) {
                Intent objIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(objIntent);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SetUpAll();
    }

    private void SetUpAll() {

        objLocationManager.removeUpdates(objLocationListener);
        String strLatitute = "Unknow";
        String strLongtitute = "Unknow";

        Location objNetworkLocation = requestUpdateFromProvider(LocationManager.NETWORK_PROVIDER, "Network Not Support");
        if (objNetworkLocation != null) {
            strLatitute = String.format("%.7f", objNetworkLocation.getLatitude());
            strLongtitute = String.format("%.7f", objNetworkLocation.getLongitude());
        }

        Location objGPSLocation = requestUpdateFromProvider(LocationManager.GPS_PROVIDER, "GPS not Support");
        if (objGPSLocation != null) {
            strLatitute = String.format("%.7f", objGPSLocation.getLatitude());
            strLongtitute = String.format("%.7f", objGPSLocation.getLongitude());
        }
        txtLat.setText(strLatitute);
        txtLon.setText(strLongtitute);
    }

    @Override
    protected void onStop() {
        super.onStop();
        objLocationManager.removeUpdates(objLocationListener);
    }

    public Location requestUpdateFromProvider(final String strPrivider, String strError) {

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strPrivider)) {
            objLocationManager.requestLocationUpdates(strPrivider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strPrivider);
        } else {
            Log.d("master", "Error From ==> " + strError);
        }

        return objLocation;
    }

    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            txtLat.setText(String.format("%.7f", location.getLatitude()));
            txtLon.setText(String.format("%.7f", location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void createCriteria() {
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);
    }

    private void bindWidget() {
        txtLat = (TextView) findViewById(R.id.textView);
        txtLon = (TextView) findViewById(R.id.textView2);
    }

}   // Main Class
