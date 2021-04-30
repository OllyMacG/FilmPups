package com.omcg.filmpups;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpandableDecadeAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<Movie>> _listMovieChild;
    private HashMap<String, ArrayList<Movie>> _filteredMovieChild;

    public ExpandableDecadeAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, ArrayList<Movie>> listMovieData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listMovieChild = listMovieData;
        this._filteredMovieChild = listMovieData;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listMovieChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Movie getChild(int groupPosition, int childPosition) {
        return this._listMovieChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.decade_group, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.decadeTitle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Movie currentMovie = (Movie) getChild(groupPosition, childPosition);
        View listItem = convertView;

        if (listItem == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItem = infalInflater.inflate(R.layout.list_text, null);
        }

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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){
        query = query.toLowerCase();
        for(String decade: _listDataHeader){
            if(_listMovieChild.get(decade).contains(query)){

            }
        }


    }

}
