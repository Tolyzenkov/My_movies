package com.tolyzenkov.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tolyzenkov.mymovies.data.FavouriteMovie;
import com.tolyzenkov.mymovies.data.MainViewModel;
import com.tolyzenkov.mymovies.data.Movie;
import com.tolyzenkov.mymovies.data.MovieDao;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imageViewFavourite;
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewYear;
    private TextView textViewDescription;
    private int id;
    private Movie movie;
    private FavouriteMovie favouriteMovie;


    private MainViewModel mainViewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_favourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageViewFavourite = findViewById(R.id.imageViewFavourite);
        imageViewPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0);
        } else {
            finish();
        }
        movie = mainViewModel.getMovieById(id);

        Picasso.get().load(movie.getPoster()).into(imageViewPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(String.valueOf(movie.getRating()));
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());
        setFavourite();

    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            mainViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
        } else {
            mainViewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.remove_from_favourite, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteMovie = mainViewModel.getFavouriteMovieById(id);
        if (favouriteMovie == null) {
            imageViewFavourite.setImageResource(R.drawable.favourite_add_to);
        } else {
            imageViewFavourite.setImageResource(R.drawable.favourite_remove);
        }

    }


}