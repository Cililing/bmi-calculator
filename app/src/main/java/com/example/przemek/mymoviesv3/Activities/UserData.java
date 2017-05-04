package com.example.przemek.mymoviesv3.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class UserData {

    public static final String USER_DATA = "com.example.przemek.mymoviesv3";

    public static final String USER_DATA_NUMBER_OF_MOVIES = "number_of_movies";
    public static final String USER_DATA_MOVIE_PRE_FILENAME = "movie_";


    private static ArrayList<Movie> userMovies = new ArrayList<>();
    private static ArrayList<Movie> searchResult = new ArrayList<>();

    public static ArrayList<Movie> getUserMovies() {
        return userMovies;
    }

    public static ArrayList<Movie> getSearchResult() {
        return searchResult;
    }

    public static void saveData(Context context) {


        SharedPreferences settigins = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settigins.edit();
        editor.clear(); //remove all data in shared preferences

        editor.putInt(USER_DATA_NUMBER_OF_MOVIES, userMovies.size());

        Gson gson = new Gson();
        for (int i = 0; i < userMovies.size(); i++) {
            //preferencename:
            String preference = USER_DATA_MOVIE_PRE_FILENAME + String.valueOf(i);
            String movie = gson.toJson(userMovies.get(i));
            editor.putString(preference, movie);
            //put every single movie
        }

        editor.commit();
    }

    public static void loadData(Context context) {

        SharedPreferences settigins = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

        int numberOfMovies = settigins.getInt(USER_DATA_NUMBER_OF_MOVIES, 0);

        userMovies.clear();
        Gson gson = new Gson();
        for (int i = 0; i < numberOfMovies; i++) {
            String preference = USER_DATA_MOVIE_PRE_FILENAME + String.valueOf(i);
            String movieAsString = settigins.getString(preference, "");

            if (movieAsString.equals("")) {
                Log.i("UserData", "error while loading data");
                return;
            }

            Movie movie = gson.fromJson(movieAsString, Movie.class);
            userMovies.add(movie);
        }
    }
}
