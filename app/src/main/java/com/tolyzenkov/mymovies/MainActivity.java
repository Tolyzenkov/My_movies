package com.tolyzenkov.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tolyzenkov.mymovies.data.Movie;
import com.tolyzenkov.mymovies.utils.JSONUtils;
import com.tolyzenkov.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textViewPopular;
    private TextView textViewBest;
    private RecyclerView recyclerViewPosters;
    private MovieAdapter adapter;
    private Switch switchSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPopular = findViewById(R.id.textViewPopular);
        textViewBest = findViewById(R.id.textViewBest);
        switchSort = findViewById(R.id.switchSort);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter();
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.BEST, 1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        adapter.setMovies(movies);
        recyclerViewPosters.setAdapter(adapter);

        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int sort;
                if (b) {
                    sort = NetworkUtils.POPULAR;
                } else {
                    sort = NetworkUtils.BEST;
                }
                JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(sort, 1);
                ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
                adapter.setMovies(movies);

            }
        });


    }

    public void setPopular(View view) {
        switchSort.setChecked(false);
    }

    public void setTheBest(View view) {
        switchSort.setChecked(true);
    }
}