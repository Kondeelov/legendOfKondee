package com.kondee.thenewlegend.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.databinding.ListItemPeopleBinding;
import com.kondee.thenewlegend.manager.http.PeopleListManager;
import com.kondee.thenewlegend.model.PeopleListItemDao;
import com.kondee.thenewlegend.view.PeopleItemList;

public class PeopleListAdapter extends BaseAdapter {

    private static final String TAG = "Kondee";
    private PeopleListItemDao dao = PeopleListManager.getInstance().getDao();

    @Override
    public int getCount() {
        if (dao == null) {
            return 50;
        }
        if (dao.getItem() == null) {
            return 50;
        }
        return dao.getItem().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getItem().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PeopleItemList item;
        if (convertView != null) {
            item = (PeopleItemList)convertView;
        } else {
            item = new PeopleItemList(parent.getContext());
        }

        item.setIdNo(String.valueOf(getItemId(position)));
        item.setFirstName(dao.getItem().get(position).getFirstName());
        item.setLastName(dao.getItem().get(position).getLastName());
        item.setDateTime(dao.getItem().get(position).getLastUpdated().toString());

        return item;
    }
}
