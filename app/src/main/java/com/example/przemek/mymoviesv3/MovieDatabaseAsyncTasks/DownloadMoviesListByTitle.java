package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.przemek.mymoviesv3.Activities.MoviesAdapter;
import com.example.przemek.mymoviesv3.Activities.Tools;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabase;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseApiException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class DownloadMoviesListByTitle extends AsyncTask<String, Void, ArrayList<Movie>> {

    private ArrayList<Movie> movieList;
    private MoviesAdapter moviesAdapter;
    private Context mContext;
    private Class networkErrorActivity;

    /**
     *
     * @param movieList
     * @param moviesAdapter
     * @param mContext application context
     * @param networkErrorActivity an error activity, type null to do nth with error
     */
    public DownloadMoviesListByTitle(ArrayList<Movie> movieList, MoviesAdapter moviesAdapter, Context mContext, Class networkErrorActivity) {
        this.movieList = movieList;
        this.moviesAdapter = moviesAdapter;
        this.mContext = mContext;
        this.networkErrorActivity = networkErrorActivity;
        AsyncTaskManager.asyncTasks.add(this);
    }

    /**
     *
     * @param params string
     * @return
     */
    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        try {
            MovieDatabase.getMoviesListByTitle(params[0], movieList);
        } catch (IOException | MovieDatabaseApiException | JSONException e) {
            Intent i = new Intent(mContext, networkErrorActivity);
            i.putExtra("last_exception", e);
            mContext.startActivity(i);
        }

        return movieList;

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> ignored) {
        moviesAdapter.notifyDataSetChanged();
        AsyncTaskManager.asyncTasks.remove(this);
    }


}
