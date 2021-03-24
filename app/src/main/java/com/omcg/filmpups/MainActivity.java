package com.omcg.filmpups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView movieListView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListView = findViewById(R.id.moviesListView);

        getAllMovies();
    }

    private void getAllMovies() {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getMovies();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> moviesList = response.body();
                ArrayList<Movie> moviesArrayList = new ArrayList<>();


                for (int i = 0; i < moviesList.size(); i++) {
                    moviesArrayList.add(new Movie(moviesList.get(i).getFilmName(),moviesList.get(i).getFilmYear()));

                }

                movieListView.setAdapter(new MovieAdapter(getApplicationContext(), moviesArrayList));
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}