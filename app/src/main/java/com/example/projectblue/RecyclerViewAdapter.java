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
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;

    RecyclerViewAdapter(Context context, ArrayList<Planta> plantor, OnClickListener onClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.plantor = plantor;
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
        holder.title.setText(planta.getName());
    }

    @Override
    public int getItemCount() {
        return plantor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView bgImage;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            bgImage = itemView.findViewById(R.id.bg_image);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(plantor.get(getAdapterPosition()));
        }

    }

    public interface OnClickListener {
        void onClick(Planta plantor);
    }

    //Skickar in listOfMountains och updaterar listan i RecyclerView så att dom visas
    public void update(ArrayList newPlantor){
        plantor.addAll(newPlantor);
    }

}
