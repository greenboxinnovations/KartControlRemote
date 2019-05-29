package com.example.kartcontrolremote;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterGrid extends Adapter<AdapterGrid.KartViewHolder> {
    private ArrayList<POJOKart> kartList;
    private gridListener mListener;

    static class KartViewHolder extends ViewHolder {
        TextView kartNum;
        LinearLayout tile;

        KartViewHolder(View itemView) {
            super(itemView);
            this.kartNum = itemView.findViewById(R.id.tv_grid);
            this.tile = itemView.findViewById(R.id.tile);
        }
    }

    AdapterGrid(ArrayList<POJOKart> kartList, gridListener mListener) {
        this.kartList = kartList;
        this.mListener = mListener;
    }

    @NonNull
    public KartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single, parent, false));
    }

    public void androidIsChutya(List<POJOKart> kartList) {
        this.kartList.clear();
        this.kartList.addAll(kartList);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(@NonNull KartViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.kartNum.setText(String.valueOf(this.kartList.get(position).getKart_no()));
//        Log.e("val", String.valueOf(this.kartList.get(position).getKart_no()));
        if (this.kartList.get(position).isBattActive()) {
            if (this.kartList.get(position).isGreen()) {
                holder.tile.setBackgroundColor(-16711936);
            }
            if (!this.kartList.get(position).isActive()) {
                holder.tile.setBackgroundColor(Color.parseColor("#212121"));
            } else if (this.kartList.get(position).isGreen()) {
                holder.tile.setBackgroundColor(-16711936);
            } else {
                holder.tile.setBackgroundColor(-1);
            }
        } else {
            holder.tile.setBackgroundColor(Color.parseColor("#aa041d"));
        }


//        holder.tile.setOnLongClickListener(new OnLongClickListener() {
//            public boolean onLongClick(View v) {
//                if (AdapterGrid.this.mListener.gridCheckActive(v, position)) {
//                    AdapterGrid.this.mListener.dialog(position);
//                }
//                return true;
//            }
//        });
        holder.tile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!AdapterGrid.this.mListener.gridCheckActive(v, position)) {
                    AdapterGrid.this.mListener.makeKartActive(v, position);
                }else{


                    AdapterGrid.this.mListener.dialog(position);

                }
            }
        });
    }

    public int getItemCount() {
        return this.kartList.size();
    }
}
