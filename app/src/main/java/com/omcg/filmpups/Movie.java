package com.omcg.filmpups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {

    // Store the name of the movie
    private String mName;
    // Store the release year of the movie
    private String mYear;

    // Constructor that is used to create an instance of the Movie object
    public Movie(String mName, String mRelease) {
        this.mName = mName;
        this.mYear = mRelease;
    }

}
