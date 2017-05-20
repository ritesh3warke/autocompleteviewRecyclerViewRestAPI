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


    public class DrugsSearchAdapter extends RecyclerView.Adapter<DrugsSearchAdapter.MyViewHolder> {

        private List<DrugSearch> drugList;
        int prevPos = 0;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);

            }
        }


        public DrugsSearchAdapter(List<DrugSearch> drugList) {
            this.drugList = drugList;
        }

    public DrugSearch getItem(int position) {
        return drugList.get(position);
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DrugSearch movie = drugList.get(position);
            holder.title.setText(movie.getDrugName());

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
            return drugList.size();
        }
    }

