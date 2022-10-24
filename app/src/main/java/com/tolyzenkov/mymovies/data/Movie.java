package com.tolyzenkov.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String title;
    private String originalTitle;
    private String description;
    private String poster;
    private String previewPoster;
    private double rating;
    private int year;

    public Movie(int uniqueId, int id, String title, String originalTitle, String description, String poster, String previewPoster, double rating, int year) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.description = description;
        this.poster = poster;
        this.previewPoster = previewPoster;
        this.rating = rating;
        this.year = year;
    }

    @Ignore
    public Movie(int id, String title, String originalTitle, String description, String poster, String previewPoster, double rating, int year) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.description = description;
        this.poster = poster;
        this.previewPoster = previewPoster;
        this.rating = rating;
        this.year = year;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public double getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    public void setPreviewPoster(String previewPoster) {
        this.previewPoster = previewPoster;
    }

    public String getPreviewPoster() {
        return previewPoster;
    }
}
