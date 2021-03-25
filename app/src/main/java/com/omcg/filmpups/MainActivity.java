package com.omcg.filmpups;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView movieListView;
    private MovieAdapter movieAdapter;
    EditText movieSearch;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        centerTitle();

        movieListView = findViewById(R.id.movieListView);
        movieSearch = findViewById(R.id.movieSearch);

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
                    moviesArrayList.add(new Movie(moviesList.get(i).getFilmName(), moviesList.get(i).getFilmYear(),"Olls Rating: " + moviesList.get(i).getOllRating(),"Dees Rating: " + moviesList.get(i).getDeeRating()));
                }
                movieAdapter = new MovieAdapter(getApplicationContext(), moviesArrayList);
                movieSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        movieAdapter.getFilter().filter(s);

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Movie movie = moviesArrayList.get(position);
                        Toast.makeText(MainActivity.this, movie.getMName(),
                                Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.movie_dialog, viewGroup, false);
                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

                movieListView.setAdapter(movieAdapter);

            }


            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();
        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if (textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if (textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for (View v : textViews) {
                    if (v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if (appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

}