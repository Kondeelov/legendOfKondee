package com.kondee.thenewlegend.manager.http;

import com.kondee.thenewlegend.model.PeopleListItemDao;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("people")
    Call<PeopleListItemDao> loadPeople();
}
