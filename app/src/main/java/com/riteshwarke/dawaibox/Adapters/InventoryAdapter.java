package com.riteshwarke.dawaibox.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riteshwarke.dawaibox.Helpers.AnimationUtil;
import com.riteshwarke.dawaibox.Models.DrugSearch;
import com.riteshwarke.dawaibox.R;

import java.util.List;

/**
 * Created by Ritesh Warke on 19/05/17.
 */


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    private List<DrugSearch> inventoryList;
    int prevPos = 0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView initial1;
        public TextView initial;
        public TextView name;
        public TextView company;
        public TextView type;
        public TextView compound;
        public TextView interactions;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            initial1 = (TextView) view.findViewById(R.id.initial1);
            initial = (TextView) view.findViewById(R.id.initial);
            name = (TextView) view.findViewById(R.id.name);
            company = (TextView) view.findViewById(R.id.company);
            type = (TextView) view.findViewById(R.id.type);
            compound = (TextView) view.findViewById(R.id.compound);
            interactions = (TextView) view.findViewById(R.id.interactions);

        }
    }


    public InventoryAdapter(List<DrugSearch> drugList) {
        this.inventoryList = drugList;
    }

    public DrugSearch getItem(int position) {
        return inventoryList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventoty_row, parent, false);


        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrugSearch i = inventoryList.get(position);
        holder.title.setText(i.getDrugName());
        holder.initial1.setText(i.getDrugName().substring(0, 1));
        holder.initial.setText(i.getDrugName().substring(0, 1));
        holder.name.setText(i.getDrugName());
        holder.company.setText(i.getPharmaCompName());
        holder.type.setText(i.getDrugType());
        Log.i("sdfghj","sdfghjkkjl "+i.getCompound() +"  "+(i.getCompound().equalsIgnoreCase("null")));
        if(i.getCompound().equalsIgnoreCase("") || i.getCompound().equalsIgnoreCase("null") )
        holder.compound.setText("Data not available.");
        else
            holder.compound.setText(i.getCompound());
        if(i.getDrugInteractions().equalsIgnoreCase("") || i.getDrugInteractions().equalsIgnoreCase("null"))
            holder.interactions.setText("Data not available.");
        else
        holder.interactions.setText(i.getDrugInteractions());

        if(position > prevPos){
            AnimationUtil.animate(holder, true);
        }
        else{
            AnimationUtil.animate(holder, false);
        }
        prevPos = position;

    }


    @Override
    public int getItemCount() {
        return inventoryList.size();
    }
}

