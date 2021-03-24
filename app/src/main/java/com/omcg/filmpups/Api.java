package com.omcg.filmpups;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://omcg.ml/api/";
    @GET("movies")
    Call<List<Results>> getMovies();
}
