package com.example.projectblue;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.title.setText(plantor.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return plantor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
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
