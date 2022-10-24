package com.tolyzenkov.mymovies.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {


    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top";
    private static final String SINGLE_FILM_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films";
    private static final String VIDEO_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/%s/videos";
    private static final String REVIEW_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/%s/reviews?order=DATE_DESC";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_TYPE = "type";
    private static final String PARAMS_API_KEY = "X-API-KEY";
    private static final String PARAMS_ID = "id";

//    private static final String API_KEY_VALUE = "6ae5c7f0-5f7c-4bc9-aa5b-689245b148cb";
    private static final String API_KEY_VALUE = "3422f1cf-47fd-432e-8997-df5927678558";
    private static final String TYPE_VALUE_BEST = "TOP_250_BEST_FILMS";
    private static final String TYPE_VALUE_POPULAR = "TOP_100_POPULAR_FILMS";
    private static final String TYPE_VALUE_AWAIT = "TOP_AWAIT_FILMS";

    public static final int BEST = 0;
    public static final int POPULAR = 1;
    public static final int AWAIT = 2;

    public static URL buildURL(int sortBy, int page) {
        URL result = null;
        String sort;
        if (sortBy == BEST) {
            sort = TYPE_VALUE_BEST;
        } else if (sortBy == POPULAR) {
            sort = TYPE_VALUE_POPULAR;
        } else {
            sort = TYPE_VALUE_AWAIT;
        }

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_TYPE, sort)
                .appendQueryParameter(PARAMS_PAGE, String.valueOf(page))
                .build();

        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("urlAll", result.toString());
        return result;
    }

    private static URL buildFilmDataUrl(int id) {
        URL result = null;
        Uri uri = Uri.parse(SINGLE_FILM_URL + "/" + id).buildUpon()
                .build();

        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("urlSingle", result.toString());
        return result;
    }

//    private static URL buildVideoUrl(int id) {
//        Uri uri = Uri.parse(String.format(VIDEO_URL, id)).buildUpon()
//                .build();
//        try {
//            return new URL(uri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static URL buildReviewsUrl(int id) {
//        Uri uri = Uri.parse(String.format(REVIEW_URL, id)).buildUpon()
//                .build();
//        try {
//            return new URL(uri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static JSONObject getJSONFromNetwork(int sortBy, int page) {
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static JSONObject getJSONVideo(int id) {
//        JSONObject result = null;
//        URL url = buildVideoUrl(id);
//        try {
//            result = new JSONLoadTask().execute(url).get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static JSONObject getJSONReviews(int id) {
//        JSONObject result = null;
//        URL url = buildReviewsUrl(id);
//        try {
//            result = new JSONLoadTask().execute(url).get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public static JSONObject getObjectForDescription(int id) {
        JSONObject result = null;
        URL url = buildFilmDataUrl(id);
        try {
            result = new JSONLoadTaskDescription().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class JSONLoader extends AsyncTaskLoader<JSONObject> {

        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        public interface OnStartLoadingListener {
            void onStartLoading();
        }

        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoadingListener != null) {
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null) {
                return null;
            }

            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            if (url == null) {
                return jsonObject;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty(PARAMS_API_KEY, API_KEY_VALUE);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject jsonObject = null;
            if (urls == null || urls.length == 0) {
                return jsonObject;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                connection.setRequestProperty(PARAMS_API_KEY, API_KEY_VALUE);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }

    private static class JSONLoadTaskDescription extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject jsonObject = null;
            if (urls == null || urls.length == 0) {
                return jsonObject;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                connection.setRequestProperty(PARAMS_API_KEY, API_KEY_VALUE);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                jsonObject = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return jsonObject;
        }
    }


}
