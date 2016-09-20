package com.kondee.thenewlegend.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.thenewlegend.databinding.ListItemPeopleBinding;
import com.kondee.thenewlegend.manager.http.PeopleListManager;
import com.kondee.thenewlegend.model.PeopleDataItemDao;
import com.kondee.thenewlegend.model.PeopleListItemDao;
import com.kondee.thenewlegend.utils.DateFormatUtils;

public class PeopleRecyclerAdapter extends RecyclerView.Adapter<PeopleRecyclerAdapter.PeopleViewHolder> {
    private PeopleListItemDao dao = PeopleListManager.getInstance().getDao();

    private static String TAG = "Kondee";

    public class PeopleViewHolder extends RecyclerView.ViewHolder {

        ListItemPeopleBinding listBinding;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            listBinding = DataBindingUtil.bind(itemView);
        }

        public ListItemPeopleBinding getBinding(){return listBinding;}
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemPeopleBinding binding;
        binding = ListItemPeopleBinding.inflate(inflater,parent,false);

        return new PeopleViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {

        PeopleDataItemDao dao = (PeopleDataItemDao)getItem(position);

        holder.getBinding().tvIdNo.setText(String.valueOf(position+1));
        holder.getBinding().tvFirstName.setText(dao.getFirstName());
        holder.getBinding().tvLastName.setText(dao.getLastName());
        holder.getBinding().tvDateTime.setText(DateFormatUtils.newInstance().setDateFromServer(dao.getLastUpdated().toString()));
    }

    private Object getItem(int position){
        return PeopleListManager.getInstance().getDao().getItem().get(position);
    }

    @Override
    public int getItemCount() {
        if(PeopleListManager.getInstance().getDao()==null)
            return 0;
        if(PeopleListManager.getInstance().getDao().getItem()==null)
            return 0;
        return PeopleListManager.getInstance().getDao().getItem().size();
    }
}
