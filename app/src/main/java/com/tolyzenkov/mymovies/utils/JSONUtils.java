package com.tolyzenkov.mymovies.utils;

import android.util.Log;

import androidx.annotation.StringDef;

import com.tolyzenkov.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_RESULT = "films";
    private static final String KEY_ID = "filmId";
    private static final String KEY_NAME_RU = "nameRu";
    private static final String KEY_NAME_EN = "nameEn";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_POSTER_URL = "posterUrl";
    private static final String KEY_PREVIEW_POSTER_URL = "posterUrlPreview";
    private static final String KEY_RATING = "rating";
    private static final String KEY_YEAR = "year";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                String title = objectMovie.getString(KEY_NAME_RU);
                String originalTitle = objectMovie.getString(KEY_NAME_EN);
                String poster = objectMovie.getString(KEY_POSTER_URL);
                String previewPoster = objectMovie.getString(KEY_PREVIEW_POSTER_URL);
                double rating = objectMovie.getInt(KEY_RATING);
                int year = objectMovie.getInt(KEY_YEAR);
                String description = getDescription(id);
//                String description = "425535353563";
                Movie movie = new Movie(id, title, originalTitle, description, poster, previewPoster, rating, year);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getDescription(int id) {
        JSONObject jsonObject = NetworkUtils.getObjectForDescription(id);
        Log.i("JSONONJECT", jsonObject.toString());
        String description = null;
        try {
            description = jsonObject.getString(KEY_DESCRIPTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return description;
    }
}
