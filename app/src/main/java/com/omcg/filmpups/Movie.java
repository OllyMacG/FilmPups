package com.omcg.filmpups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {

    private String mName;
    private String mYear;
    private String mOllRating;
    private String mDeeRating;

    // Constructor that is used to create an instance of the Movie object
    public Movie(String mName, String mRelease, String mOllRating, String mDeeRating) {
        this.mName = mName;
        this.mYear = mRelease;
        this.mOllRating = mOllRating;
        this.mDeeRating = mDeeRating;
    }

}
