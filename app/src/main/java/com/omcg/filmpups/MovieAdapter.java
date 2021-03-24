package com.omcg.filmpups;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<Movie> moviesList = new ArrayList<>();

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_text,parent,false);

        Movie currentMovie = moviesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.filmName);
        name.setText(currentMovie.getMName());

        TextView release = (TextView) listItem.findViewById(R.id.filmYear);
        release.setText(currentMovie.getMYear());

        return listItem;
    }
}

