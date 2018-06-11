package bala.googlemap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;

    LatLng latLng;
    int locationkey;
    int dialogue = 0;
    AppPermissions mRuntimePermission;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRuntimePermission = new AppPermissions(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (mRuntimePermission.hasPermission(AppPermissions.ALL_PERMISSIONS)) {
            // Toast.makeText(this, "All permission already given", Toast.LENGTH_SHORT).show();
            Log.d("Success", "All permission already given");
        } else {
            mRuntimePermission.requestPermission(AppPermissions.ALL_PERMISSIONS, AppPermissions.ALL_REQUEST_CODE);
        }
        getLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StartLocationAlert startLocationAlert = new StartLocationAlert(MainActivity.this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (getLocationMode(getApplicationContext()) == 3) {
            mMap.clear();
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            dialogue++;
            if (dialogue == 1) {
                StartLocationAlert startLocationAlert = new StartLocationAlert(MainActivity.this);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        getLocation();
    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public int getLocationMode(Context context) {
        try {
            locationkey = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return locationkey;

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location locations) {

                        latLng = new LatLng(locations.getLatitude(), locations.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).icon(
                                BitmapDescriptorFactory.defaultMarker()).title("current location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        mMap.animateCamera(CameraUpdateFactory.zoomIn());
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                        if (locations != null) {


                            Log.d("location", String.valueOf(locations.getLatitude()));
                            Log.d("location", String.valueOf(locations.getLongitude()));

                        }
                    }
                });

    }
}
