package com.kondee.thenewlegend.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kondee.thenewlegend.databinding.ListItemPeopleBinding;
import com.kondee.thenewlegend.manager.Contextor;

public class PeopleItemList extends FrameLayout {

    ListItemPeopleBinding binding;

    public PeopleItemList(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public PeopleItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
        initWithAttrs(attrs, 0, 0);
    }

    public PeopleItemList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(21)
    public PeopleItemList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ListItemPeopleBinding.inflate(inflater, this, true);
    }

    private void initInstance() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    public void setIdNo(String idNo) {
        binding.tvIdNo.setText(idNo);
    }

    public void setFirstName(String firstName) {
        binding.tvFirstName.setText(firstName);
    }

    public void setLastName(String lastName) {
        binding.tvLastName.setText(lastName);
    }

    public void setDateTime(String dateTime) {
        binding.tvDateTime.setText(dateTime);
    }
}
