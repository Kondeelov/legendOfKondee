package com.kondee.thenewlegend.manager.http;

import android.content.Context;

import com.kondee.thenewlegend.manager.Contextor;
import com.kondee.thenewlegend.model.PeopleListItemDao;

public class PeopleListManager {

    private static PeopleListManager instance;

    public static PeopleListManager getInstance() {
        if (instance == null) {
            instance = new PeopleListManager();
        }
        return instance;
    }

    private Context mContext;
    private PeopleListItemDao dao;

    private PeopleListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PeopleListItemDao getDao() {
        return dao;
    }

    public void setDao(PeopleListItemDao dao) {
        this.dao = dao;
    }
}
