package com.kondee.thenewlegend.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.databinding.ListItemPeopleBinding;
import com.kondee.thenewlegend.fragment.MainFragment;
import com.kondee.thenewlegend.manager.http.PeopleListManager;
import com.kondee.thenewlegend.model.PeopleDataItemDao;
import com.kondee.thenewlegend.model.PeopleListItemDao;
import com.kondee.thenewlegend.utils.DateFormatUtils;
import com.kondee.thenewlegend.view.PeopleItemList;

public class PeopleListAdapter extends BaseAdapter {

    private static final String TAG = "Kondee";
    MainFragment fragment;

    public PeopleListAdapter(MainFragment fragment) {
        this.fragment = fragment;
    }

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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        PeopleItemList item;
        if (convertView != null) {
            item = (PeopleItemList) convertView;
        } else {
            item = new PeopleItemList(parent.getContext());
        }

        final PeopleDataItemDao dao = (PeopleDataItemDao) getItem(position);

        final float[] x = new float[1];
        final float[] vX = new float[1];

        item.binding.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vX[0] = v.getX();
                        x[0] = event.getRawX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getRawX() - x[0] > 0) {
//                            Log.d(TAG, "onTouch: " + (v.getRootView().getWidth() / 5) * 2);
                            if (v.getX() > (v.getRootView().getWidth() / 5) * 2) {
                                Log.d(TAG, "onTouch: Delete");
                                fragment.deletePeopleData(dao.getEtag(), dao.getIdNo());
                            }
                            parent.requestDisallowInterceptTouchEvent(true);
                            v.setX(event.getRawX() - x[0]);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setX(vX[0]);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        v.setX(vX[0]);
                        return true;
                }
                return false;
            }
        });

        item.setIdNo(String.valueOf(getItemId(position)));
        item.setFirstName(dao.getFirstName());
        item.setLastName(dao.getLastName());
        item.setDateTime(DateFormatUtils.newInstance().setDateFromServer(dao.getLastUpdated().toString()));

        return item;
    }
}
