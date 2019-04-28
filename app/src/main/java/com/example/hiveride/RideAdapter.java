package com.example.hiveride;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    private ArrayList<Ride> mRides;

    public RideAdapter(ArrayList<Ride> rides){
        mRides = rides;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.ride_panel, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.date.setText(mRides.get(position).getDate());
        viewHolder.rideImage.setImageResource(mRides.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mRides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        ImageView rideImage;

        ViewHolder(View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.ride_text);
            rideImage = itemView.findViewById(R.id.ride_image);
        }
    }

}
