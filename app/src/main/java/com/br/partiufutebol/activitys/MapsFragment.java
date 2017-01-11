package com.br.partiufutebol.activitys;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.br.partiufutebol.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    private static final String TAG = "MapsFragment" ;
    private GoogleMap mMap;
    private LocationManager locationManager;

    FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);


            firebaseDatabase = FirebaseDatabase.getInstance();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        try{
            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria =  new Criteria();
            String provider  = locationManager.getBestProvider(criteria, true);

            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
        }catch (SecurityException ex){
            Log.e(TAG, "Erro", ex);
        }



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 50, this);


    }



    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(), "Provider Habilitado"+ location.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getActivity(), "Provider Habilitado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getActivity(), "Provider Desabilitado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }


}
