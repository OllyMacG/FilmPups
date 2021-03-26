package com.omcg.filmpups;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Rating {

    @SerializedName("oll_rating")
    private String ollRating;

    @SerializedName("dee_rating")
    private String deeRating;

    public Rating(String deeRating, String ollRating) {
        this.deeRating = deeRating;
        this.ollRating = ollRating;
    }
}

