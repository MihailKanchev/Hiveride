package com.example.hiveride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Map extends Fragment implements OnMapReadyCallback {
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(((MainActivity)getActivity()).isSignedIn())
            map.setMyLocationEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(map != null){
            if(((MainActivity)getActivity()).isSignedIn())
                map.setMyLocationEnabled(false);
            else
                map.setMyLocationEnabled(true);

        }
        else;
    }
}
