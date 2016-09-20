package com.kondee.thenewlegend.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.Toast;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.adapter.PeopleListAdapter;
import com.kondee.thenewlegend.databinding.AlertDialogEditPeopleBinding;
import com.kondee.thenewlegend.databinding.FragmentMainBinding;
import com.kondee.thenewlegend.manager.Contextor;
import com.kondee.thenewlegend.manager.http.HttpManager;
import com.kondee.thenewlegend.manager.http.PeopleListManager;
import com.kondee.thenewlegend.model.PeopleListItemDao;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kondee.thenewlegend.R.id.fabAdd;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Kondee";
    FragmentMainBinding binding;
    ProgressDialog progressDialog;
    PeopleListAdapter adapter;
    GestureDetectorCompat gestureDetectorCompat;
    private AlertDialog.Builder builder;
    private AlertDialog alertdialog;
    float dX, dY;
    private float x;
    private float y;
    private long downTime;
    private AlertDialogEditPeopleBinding alertBinding;
    private AlertDialog deletedialog;
    private AlertDialog.Builder deleteBuilder;
    GestureDetector gestureDetector;
    View fabView;
    private int lastVisibleItem = 0;
    private float cY;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View rootView = binding.getRoot();

        initInstance();
        showProgressDialog();
        loadPeopleData();

        return rootView;
    }

    private void initInstance() {

        adapter = new PeopleListAdapter(this);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (lastVisibleItem < firstVisibleItem)
                    binding.fabAdd.hide();
                else if (lastVisibleItem > firstVisibleItem)
                    binding.fabAdd.show();
                lastVisibleItem = firstVisibleItem;
            }
        });


        binding.fabAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {

                fabView = v;
                gestureDetector.onTouchEvent(event);

                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//
//                        x = v.getX();
//                        y = v.getY();
//                        dX = x - event.getRawX();
//                        dY = y - event.getRawY();
//                        downTime = event.getEventTime();
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:

//                        v.getTranslationX();
//                        v.setX(event.getRawX() + dX);
//                        v.setY(event.getRawY() + dY);
//                        Log.d(TAG, "onTouch: ActionMove " + dX + " " + dY);
//                        return true;
//
                    case MotionEvent.ACTION_UP:
                        v.animate()
                                .x(x)
                                .y(cY)
                                .setDuration(950)
                                .setInterpolator(new DecelerateInterpolator());
                        return true;
                }
                return true;
            }
        });


        gestureDetector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                x = fabView.getX();
                y = fabView.getY();
                dX = x - e.getRawX();
                dY = y - e.getRawY();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                showEditPeopleDialog("Add");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                Log.d(TAG, "Distance: "+distanceX+":"+distanceY);
//                fabView.setX(fabView.getX()-distanceX);
//                fabView.setY(fabView.getY()-distanceY);
                fabView.setX(e2.getRawX() + dX);
                fabView.setY(e2.getRawY() + dY);
                cY = fabView.getY();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
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

    public void deletePeopleData(final String eTag, final String idNo) {
        deleteBuilder = new AlertDialog.Builder(getActivity());
        deleteBuilder.setTitle("Delete!")
                .setMessage("Are you sure you want to delete?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showProgressDialog();
                        Call<Void> call = HttpManager.getInstance().getService().deletePeople(eTag, idNo);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    loadPeopleData();
                                } else {
                                    try {
                                        progressDialog.dismiss();
                                        Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        if (deletedialog != null) {
            if (!deletedialog.isShowing())
                deletedialog = deleteBuilder.show();
        } else {
            deletedialog = deleteBuilder.show();
        }

    }

    public void showProgressDialog() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case fabAdd:
                showEditPeopleDialog("Add");
                break;
        }
    }

    public void showEditPeopleDialog(final String title) {
        alertBinding = AlertDialogEditPeopleBinding.inflate(LayoutInflater.from(getActivity()));
        builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_AlertDialog);
        builder.setView(alertBinding.getRoot())
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title == "Add") {
                            addPeopleData(alertBinding.etFirstName.getText().toString(), alertBinding.etLastName.getText().toString());
                            dialog.dismiss();
                            showProgressDialog();
                        }
                    }
                });


        alertdialog = builder.create();
        alertdialog.show();
    }

    private void addPeopleData(String firstName, String lastName) {
        Call<Void> call = HttpManager.getInstance().getService().addPeople(firstName, lastName);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadPeopleData();
                } else {
                    try {
                        progressDialog.dismiss();
                        Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
