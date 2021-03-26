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
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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
        RotateLoading rotateLoading = findViewById(R.id.rotateloading);
        rotateLoading.start();
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getMovies();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> moviesList = response.body();
                ArrayList<Movie> moviesArrayList = new ArrayList<>();
                for (int i = 0; i < moviesList.size(); i++) {
                    moviesArrayList.add(new Movie(moviesList.get(i).getId(), moviesList.get(i).getFilmName(), moviesList.get(i).getFilmYear(), moviesList.get(i).getOllRating(), moviesList.get(i).getDeeRating()));
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
                        Movie movie = movieAdapter.getItem(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.movie_dialog, viewGroup, false);

                        EditText deeRatingEdit = (EditText) dialogView.findViewById
                                (R.id.editDeeRating);
                        EditText ollRatingEdit = (EditText) dialogView.findViewById
                                (R.id.editOllRating);
                        Button rateButton = (Button) dialogView.findViewById
                                (R.id.rateButton);

                        deeRatingEdit.setText(movie.getMDeeRating());
                        ollRatingEdit.setText(movie.getMOllRating());
                        deeRatingEdit.addTextChangedListener(new CheckPercentage());
                        ollRatingEdit.addTextChangedListener(new CheckPercentage());


                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();

                        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


                        rateButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(buttonClick);
                                putMovieRating(movie, deeRatingEdit.getText().toString(), ollRatingEdit.getText().toString());
                                alertDialog.dismiss();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                        alertDialog.show();
                    }
                });

                rotateLoading.stop();
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

    class CheckPercentage implements TextWatcher {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("?")) {
                try {
                    if (Integer.parseInt(s.toString()) > 100)
                        s.replace(0, s.length(), "100");
                } catch (NumberFormatException nfe) {
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used, details on text just before it changed
            // used to track in detail changes made to text, e.g. implement an undo
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used, details on text at the point change made
        }
    }


    private void putMovieRating(Movie movie, String deeRating, String ollRating) {
        Rating rating = new Rating(deeRating, ollRating);
        Call<ResponseBody> call = RetrofitClient.getInstance().getMyApi().putRating(movie.getId(), rating);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Film is rated",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });

    }
}