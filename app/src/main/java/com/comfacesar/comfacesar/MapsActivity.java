package com.comfacesar.comfacesar;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        int cadena = getIntent().getExtras().getInt("id");
        String aux = Integer.toString(cadena);

        // Toast.makeText(MapsActivity.this,aux, Toast.LENGTH_SHORT).show();

        switch (aux)
        {
            case "1":
                LatLng SedeAdministrativa = new LatLng(10.474032, -73.246280);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(SedeAdministrativa).title("SedeAdministrativa"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SedeAdministrativa, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                break;

            case"2":
                LatLng CentroIntegralServicios = new LatLng(10.473988,  -73.253233);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(CentroIntegralServicios).title("CentroIntegralServicios"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CentroIntegralServicios, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

            case"3":
                LatLng CrispinVillazon = new LatLng(10.499886,   -73.278124);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(CrispinVillazon).title("CrispinVillazon"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CrispinVillazon, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

            case"4":
                LatLng FondoAdpatacioVivienda = new LatLng(10.474386,    -73.252974);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(FondoAdpatacioVivienda).title("FondoAdpatacion Vivienda"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FondoAdpatacioVivienda, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                break;

            case"5":
                LatLng Instecom = new LatLng(10.475436,     -73.248948);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(Instecom).title("Instecom"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Instecom, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

            case"6":
                LatLng colegio = new LatLng(10.485951,     -73.280596 );
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.logocomfacesarnew)).position(colegio).title("Colegio Comfacesar"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colegio, 18));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;




        }



    }
}