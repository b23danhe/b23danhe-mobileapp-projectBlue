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

    private ArrayList<Planta> plantor;
    private ArrayList<Planta> originalPlantor;
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;

    RecyclerViewAdapter(Context context, ArrayList<Planta> plantor, OnClickListener onClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.plantor = new ArrayList<>(plantor);
        this.originalPlantor = plantor;
        this.onClickListener = onClickListener;
    }

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
                .placeholder(R.drawable.item_background)
                .into(holder.bgImage);
        holder.title.setText("  " + planta.getName() + "  ");
        holder.room.setText("  " + planta.getLocation() + "  ");
    }

    @Override
    public int getItemCount() {
        return plantor.size();
    }

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

    public interface OnClickListener {
        void onClick(Planta planta);
    }

    //Skickar in listOfPlantor och updaterar listan i RecyclerView så att dom visas
    public void update(ArrayList<Planta> newPlantList){
        plantor = new ArrayList<>(newPlantList); // Updaterar listan meds en kopia
        originalPlantor = newPlantList;
        //plantor.clear();
        //plantor.addAll(listOfPlantor);
        notifyDataSetChanged();
    }

    public void filter(String location) {
        if (location.equals("Rensa filter")) {
            plantor = new ArrayList<>(originalPlantor); // Nollställer till original listan med plantor
        }
        else {
            ArrayList<Planta> filteredList = new ArrayList<>();
            for (Planta planta : originalPlantor) {
                if (planta.getLocation().equalsIgnoreCase(location)) {
                    filteredList.add(planta);
                }
            }
            plantor = filteredList;
        }
        notifyDataSetChanged();
    }

}
