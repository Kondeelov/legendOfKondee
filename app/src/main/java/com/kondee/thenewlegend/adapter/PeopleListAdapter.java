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
import com.kondee.thenewlegend.model.PeopleDataItemDao;
import com.kondee.thenewlegend.model.PeopleListItemDao;
import com.kondee.thenewlegend.utils.DateFormatUtils;
import com.kondee.thenewlegend.view.PeopleItemList;

public class PeopleListAdapter extends BaseAdapter {

    private static final String TAG = "Kondee";

    @Override
    public int getCount() {
        if (PeopleListManager.getInstance().getDao() == null) {
            return 0;
        }
        if (PeopleListManager.getInstance().getDao().getItem() == null) {
            return 0;
        }
        return PeopleListManager.getInstance().getDao().getItem().size();
    }

    @Override
    public Object getItem(int position) {
        return PeopleListManager.getInstance().getDao().getItem().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PeopleItemList item;
        if (convertView != null) {
            item = (PeopleItemList) convertView;
        } else {
            item = new PeopleItemList(parent.getContext());
        }

        PeopleDataItemDao dao = (PeopleDataItemDao) getItem(position);

        item.setIdNo(String.valueOf(getItemId(position)));
        item.setFirstName(dao.getFirstName());
        item.setLastName(dao.getLastName());
        item.setDateTime(DateFormatUtils.newInstance().setDateFromServer(dao.getLastUpdated().toString()));

        return item;
    }
}
