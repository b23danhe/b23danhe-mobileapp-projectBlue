package com.example.projectblue;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // List of plants to display and a copy for filtering
    private ArrayList<Planta> plantor;
    private ArrayList<Planta> originalPlantor;

    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;

    // Constructor to initialize the adapter with context, list of plants, and click listener
    RecyclerViewAdapter(Context context, ArrayList<Planta> plantor, OnClickListener onClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.plantor = new ArrayList<>(plantor);
        this.originalPlantor = plantor;
        this.onClickListener = onClickListener;
    }

    // Inflate the data to each item in the RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Planta planta = plantor.get(position);
        Picasso.get()
                .load(planta.getImageUrl())
                // placeholder showing while image loads
                .placeholder(R.drawable.item_background)
                // loads plant images using Picasso
                .into(holder.bgImage);
        // Sets plant name
        holder.title.setText("  " + planta.getName() + "  ");
        // Sets plant location
        holder.room.setText("  " + planta.getLocation() + "  ");
    }

    @Override
    public int getItemCount() {
        return plantor.size();
    }

    // ViewHolder class to hold the view elements for each item
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, room;
        ImageView bgImage;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title_name);
            room = itemView.findViewById(R.id.title_room);
            bgImage = itemView.findViewById(R.id.bg_image);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(plantor.get(getAdapterPosition()));
        }

    }

    // Interface for handling item clicks
    public interface OnClickListener {
        void onClick(Planta planta);
    }

    // Updates the list of plants and notifies the adapter to refresh
    public void update(ArrayList<Planta> newPlantList){
        // Updates the list with a copy
        plantor = new ArrayList<>(newPlantList);
        originalPlantor = newPlantList;
    }

    // Filters the list of plants based on the specified location
    public void filter(String location) {
        if (location.equals("Rensa filter")) {
            // Resets to the original list
            plantor = new ArrayList<>(originalPlantor);
        }
        else {
            ArrayList<Planta> filteredList = new ArrayList<>();
            for (Planta planta : originalPlantor) {
                if (planta.getLocation().equalsIgnoreCase(location)) {
                    // Adds plants that match the location
                    filteredList.add(planta);
                }
            }
            plantor = filteredList;
        }
        // Notifies the adapter to refresh the RecyclerView
        notifyDataSetChanged();
    }

}
