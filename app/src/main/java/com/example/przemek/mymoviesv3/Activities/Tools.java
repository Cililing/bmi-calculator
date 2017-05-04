package com.example.przemek.mymoviesv3.Activities;

import android.content.Context;
import android.widget.Toast;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;

public class Tools {

    public static void makeLongToast(Context mContext, String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
    }

    public static void makeShortToast(Context mContext, String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    public static boolean chceckIfMovieIsInYourCollection(Movie movie) {
        for (Movie m : UserData.getUserMovies()) {
            if (m.getId() == movie.getId()) {
                return true;
            }
        }
        return false;
    }
}
