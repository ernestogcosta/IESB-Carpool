package br.iesb.iesbcarpool.ui.Activity;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Conexao;
import br.iesb.iesbcarpool.ui.Model.IESB;

public class MapActivityTest extends FragmentActivity implements OnMapReadyCallback {


    LatLng latLngIesbSul = IESB.SUL.getLatLng();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);
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
    public void onMapReady(final GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        Conexao.getReference().child("Aluno")
                .child("PkOA9D3YQtTNm2XlVtCuHs5bb6d2")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLngIesbSul).title("IESB SUL"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngIesbSul,15));
                String latitude = dataSnapshot.child("latLong").child("latitude").getValue().toString();
                String longitude = dataSnapshot.child("latLong").child("longitude").getValue().toString();

                double latitudeDouble = Double.parseDouble(latitude);
                double longitudeDouble = Double.parseDouble(longitude);
                LatLng marcacaoTempoReal = new LatLng(latitudeDouble, longitudeDouble);
                mMap.addMarker(new MarkerOptions().position(marcacaoTempoReal));
                alert(latitude);
                alert(longitude);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    public void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
