package com.tolyzenkov.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movie")
public class FavouriteMovie extends Movie{
    public FavouriteMovie(int uniqueId, int id, String title, String originalTitle, String description, String poster, String previewPoster, double rating, int year) {
        super(uniqueId, id, title, originalTitle, description, poster, previewPoster, rating, year);
    }

    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqueId(), movie.getId(), movie.getTitle(), movie.getOriginalTitle(), movie.getDescription(), movie.getPoster(), movie.getPreviewPoster(), movie.getRating(), movie.getYear());
    }
}
