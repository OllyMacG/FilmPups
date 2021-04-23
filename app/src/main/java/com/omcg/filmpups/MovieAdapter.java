package com.omcg.filmpups;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> implements Filterable {

    private Context mContext;
    private List<Movie> moviesList = new ArrayList<>();
    private List<Movie> filteredMovieList = new ArrayList<>();

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
        filteredMovieList = list;
    }

    @Override
    public int getCount() {
        return filteredMovieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return filteredMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_text, parent, false);

        Movie currentMovie = filteredMovieList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.filmName);
        name.setText(currentMovie.getMName());

        TextView year = (TextView) listItem.findViewById(R.id.filmYear);
        year.setText(currentMovie.getMYear());

        TextView ollRating = (TextView) listItem.findViewById(R.id.ollRating);
        ollRating.setText("Oll Rating: " + currentMovie.getMOllRating());

        TextView deeRating = (TextView) listItem.findViewById(R.id.deeRating);
        deeRating.setText("Dee Rating: " + currentMovie.getMDeeRating());

        return listItem;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = moviesList.size();
                    filterResults.values = moviesList;

                } else {
                    ArrayList<Movie> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Movie movie : moviesList) {
                        if (movie.getMName().toLowerCase().contains(searchStr)) {
                            resultsModel.add(movie);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMovieList = (ArrayList<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}


