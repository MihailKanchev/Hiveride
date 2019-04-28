package com.example.hiveride;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.ride_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_of_rides);

        ArrayList<Ride> rides = new ArrayList<Ride>();
        rides.add(new Ride(R.drawable.account_image, "Ride 1"));
        rides.add(new Ride(R.drawable.account_image, "Ride 2"));
        rides.add(new Ride(R.drawable.account_image, "Ride 3"));
        rides.add(new Ride(R.drawable.account_image, "Ride 4"));
        rides.add(new Ride(R.drawable.account_image, "Ride 5"));
        rides.add(new Ride(R.drawable.account_image, "Ride 6"));
        rides.add(new Ride(R.drawable.account_image, "Ride 7"));
        rides.add(new Ride(R.drawable.account_image, "Ride 8"));
        rides.add(new Ride(R.drawable.account_image, "Ride 9"));
        rides.add(new Ride(R.drawable.account_image, "Ride 10"));
        rides.add(new Ride(R.drawable.account_image, "Ride 11"));
        rides.add(new Ride(R.drawable.account_image, "Ride 12"));

        RideAdapter rideAdapter = new RideAdapter(rides);
        recyclerView.setAdapter(rideAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;

    }

}
