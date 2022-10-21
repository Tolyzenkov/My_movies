package com.tolyzenkov.mymovies.data;

public class Movie {
    private int id;
    private String nameRu;
    private String nameEn;
    private String description;
    private String posterUrl;
    private String previewPoster;
    private double rating;
    private int year;

    public Movie(int id, String title, String originalTitle, String description, String poster, String previewPoster, double rating, int releaseDate) {
        this.id = id;
        this.nameRu = title;
        this.nameEn = originalTitle;
        this.description = description;
        this.posterUrl = poster;
        this.previewPoster = previewPoster;
        this.rating = rating;
        this.year = releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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

    public String getNameRu() {
        return nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterUrl() {
        return posterUrl;
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
