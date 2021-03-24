package com.omcg.filmpups;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Results {

    @SerializedName("name")
    private String filmName;

    @SerializedName("year")
    private String filmYear;

    public Results(String name, String year) {
        this.filmName = name;
        this.filmYear = year;
    }


}

