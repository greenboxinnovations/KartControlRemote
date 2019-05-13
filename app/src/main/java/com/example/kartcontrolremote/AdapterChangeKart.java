package com.example.kartcontrolremote;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class AdapterChangeKart extends Adapter<AdapterChangeKart.KartViewHolder> {
    private List<POJOKart> kartList;

    static class KartViewHolder extends ViewHolder {
        TextView itemName;
        RelativeLayout rl;

        KartViewHolder(View kartView) {
            super(kartView);
            this.itemName =  kartView.findViewById(R.id.tv_kart_change);
            this.rl =  kartView.findViewById(R.id.rl_kart_change);
        }
    }

    AdapterChangeKart(List<POJOKart> kartList) {
        this.kartList = kartList;
    }

    @NonNull
    public KartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.change_kart_single, parent, false));
    }

    public void onBindViewHolder(@NonNull KartViewHolder holder, int position) {
        holder.itemName.setText(String.valueOf(this.kartList.get(position).getKart_no()));
    }

    public int getItemCount() {
        return this.kartList.size();
    }
}
