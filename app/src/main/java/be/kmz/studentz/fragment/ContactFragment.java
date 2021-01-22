package be.kmz.studentz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import be.kmz.studentz.R;
import be.kmz.studentz.model.ehb.Ehb;
import be.kmz.studentz.model.ehb.EhbModel;


//mijn map werkt alleen op fysieke toestellen, op mijn virtuele device wilt het niet werken..
public class ContactFragment extends Fragment implements OnMapReadyCallback {

    //fields dat ik nodig heb
    private FragmentActivity mContext;
    private MapView mMapView;
    private GoogleMap mMap;

    //wordt opgeroepen wanneer de fragment toegevoegd is aan de fragmentmanager en
    //gekoppeld aan zijn hostactivity, fragment is actief
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    //nieuwe instantie van mijn fragment
    public static ContactFragment newInstance() {
        return new ContactFragment();
    }


    //wordt gebruikt om een instantie te maken van mijn user interface view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return rootView;
    }

    //volgende methodes zijn nodig om de lifecycle van maps te linken aan lifecycle van de fragment
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    //wanneer maps wordt ingeladen, wat moet er gebeuren?
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        centerMapOnEhb();
        createMarker();
    }

    //methode om te centreren op brussel waarbij de 3 grote campussen worden getoond
    private void centerMapOnEhb() {
        LatLng coordEhb = new LatLng(50.842266078753354, 4.322805316393921);
        CameraUpdate moveToEhb = CameraUpdateFactory.newLatLngZoom(coordEhb, 11.30f);
        mMap.animateCamera(moveToEhb);
    }

    //metohde om markers te maken
    private void createMarker() {
        EhbModel model = new ViewModelProvider(mContext).get(EhbModel.class);
        model.getEhbCampusList().observe(getViewLifecycleOwner(), ehbSchools -> {
            for (Ehb currentCampus : ehbSchools) {
                mMap.addMarker(new MarkerOptions().position(currentCampus.getCoordinate())
                        .title(currentCampus.getName())
                        .snippet(currentCampus.getInfo())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        });
    }
}
