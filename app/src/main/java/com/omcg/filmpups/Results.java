package com.omcg.filmpups;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Results {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String filmName;

    @SerializedName("year")
    private String filmYear;

    @SerializedName("oll_rating")
    private String ollRating;

    @SerializedName("dee_rating")
    private String deeRating;

    public Results(int id, String name, String year, String ollRating, String deeRating) {
        this.id = id;
        this.filmName = name;
        this.filmYear = year;
        this.ollRating = ollRating;
        this.deeRating = deeRating;
    }
}

