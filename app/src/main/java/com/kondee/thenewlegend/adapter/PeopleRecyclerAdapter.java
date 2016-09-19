package com.kondee.thenewlegend.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.thenewlegend.databinding.ListItemPeopleBinding;

public class PeopleRecyclerAdapter extends RecyclerView.Adapter<PeopleRecyclerAdapter.PeopleViewHolder> {

    ListItemPeopleBinding binding;

    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        public PeopleViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ListItemPeopleBinding.inflate(inflater,parent,false);

        return new PeopleViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
