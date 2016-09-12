package com.kondee.thenewlegend.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleListItemDao {

    @SerializedName("_items") List<PeopleDataItemDao> item;

    public List<PeopleDataItemDao> getItem() {
        return item;
    }

    public void setItem(List<PeopleDataItemDao> item) {
        this.item = item;
    }
}
