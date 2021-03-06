package com.kondee.thenewlegend.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.adapter.PeopleRecyclerAdapter;
import com.kondee.thenewlegend.databinding.FragmentSecondBinding;
import com.kondee.thenewlegend.manager.Contextor;
import com.kondee.thenewlegend.manager.http.HttpManager;
import com.kondee.thenewlegend.manager.http.PeopleListManager;
import com.kondee.thenewlegend.model.PeopleListItemDao;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    FragmentSecondBinding binding;
    private LinearLayoutManager layoutManager;
    private PeopleRecyclerAdapter adapter;
    private ProgressDialog progressDialog;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        View rootView = binding.getRoot();

        initInstance(rootView);
        showProgressDialog();
        loadPeopleData();
        return rootView;
    }

    private void initInstance(View rootView) {
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new PeopleRecyclerAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    public void loadPeopleData() {
        Call<PeopleListItemDao> call = HttpManager.getInstance().getService().loadPeople();
        call.enqueue(new Callback<PeopleListItemDao>() {
            @Override
            public void onResponse(Call<PeopleListItemDao> call, Response<PeopleListItemDao> response) {
                if (response.isSuccessful()) {
                    PeopleListItemDao dao = response.body();
                    PeopleListManager.getInstance().setDao(dao);

                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleListItemDao> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showProgressDialog() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
    }
}
