package com.kondee.thenewlegend.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.adapter.PeopleRecyclerAdapter;
import com.kondee.thenewlegend.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    FragmentSecondBinding binding;
    private LinearLayoutManager layoutManager;
    private PeopleRecyclerAdapter adapter;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        View rootView = binding.getRoot();

        initInstance(rootView);
        
        return rootView;
    }

    private void initInstance(View rootView) {
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new PeopleRecyclerAdapter();
        binding.recyclerView.setAdapter(adapter);
    }
}
