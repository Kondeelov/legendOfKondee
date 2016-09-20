package com.kondee.thenewlegend.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
//    View view;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final PeopleItemList item;
        if (convertView != null) {
            item = (PeopleItemList) convertView;
        } else {
            item = new PeopleItemList(parent.getContext());
        }

        final PeopleDataItemDao dao = (PeopleDataItemDao) getItem(position);
        final FloatingActionButton[] fabDelete = new FloatingActionButton[1];
        final FloatingActionButton[] fabEdit = new FloatingActionButton[1];

        item.binding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fabDelete[0] = new FloatingActionButton(parent.getContext());
                fabDelete[0].setBackgroundTintList(ColorStateList.valueOf(parent.getResources().getColor(R.color.colorRedLight)));
                fabDelete[0].setImageResource(R.drawable.garbage);
                fabDelete[0].setX(parent.getWidth() - 145);
                fabDelete[0].setY(20);
                fabDelete[0].show();

                fabEdit[0] = new FloatingActionButton(parent.getContext());
                fabEdit[0].setBackgroundTintList(ColorStateList.valueOf(parent.getResources().getColor(R.color.colorGrayLight)));
                fabEdit[0].setImageResource(R.drawable.edit);
                fabEdit[0].setX(parent.getWidth() - 275);
                fabEdit[0].setY(20);
                fabEdit[0].show();

                fabDelete[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fabDelete[0].hide();
                        fabEdit[0].hide();

                        fragment.deletePeopleData(dao.getEtag(), dao.getIdNo());
                    }
                });
                fabEdit[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fabDelete[0].hide();
                        fabEdit[0].hide();

                        fragment.showEditPeopleDialog("Update");
                    }
                });

                item.binding.cardViewContainer.addView(fabDelete[0]);
                item.binding.cardViewContainer.addView(fabEdit[0]);

                return true;
            }
        });

        item.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabDelete[0] != null && fabEdit[0] != null)
                    if (fabDelete[0].isShown() && fabEdit[0].isShown()) {
                        fabDelete[0].hide();
                        fabEdit[0].hide();
                    }
            }
        });

        item.setIdNo(String.valueOf(getItemId(position)));
        item.setFirstName(dao.getFirstName());
        item.setLastName(dao.getLastName());
        item.setDateTime(DateFormatUtils.newInstance().setDateFromServer(dao.getLastUpdated().toString()));

        return item;
    }
}
