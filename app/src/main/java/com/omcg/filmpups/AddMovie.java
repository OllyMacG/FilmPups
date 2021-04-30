package com.omcg.filmpups;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class AddMovie {

    @SerializedName("name")
    private String name;

    @SerializedName("year")
    private String year;


    public AddMovie(String name, String year) {
        this.name = name;
        this.year = year;
    }
}

