package com.kondee.thenewlegend.manager.http;

import com.kondee.thenewlegend.model.PeopleListItemDao;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("people")
    Call<PeopleListItemDao> loadPeople();

    @FormUrlEncoded
    @POST("people")
    Call<Void> addPeople(@Field("firstName") String firstName, @Field("lastName") String lastName);

    @DELETE("people/{id}")
    Call<Void> deletePeople(@Header("If-Match") String eTag, @Path("id") String idNo);
}
