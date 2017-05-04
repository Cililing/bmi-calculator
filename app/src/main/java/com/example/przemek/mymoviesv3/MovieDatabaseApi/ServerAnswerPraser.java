package com.example.przemek.mymoviesv3.MovieDatabaseApi;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ServerAnswerPraser {

    static void generateMovieListFromJSONRequest(String jsonRawData, ArrayList<Movie> outList) throws JSONException, MovieDatabaseApiException, IOException {

        //download genres!
        SparseArray<String> genresArray = new SparseArray<>();
        String genreJSONRawData = MovieDatabaseNetwork.downloadJSONRawData(ApiParameters.genreRequest);
        generateGenreListFromJSONRequest(genreJSONRawData, genresArray);

        outList.clear();
        JSONObject reader = new JSONObject(jsonRawData);

        if (reader.has(ApiParameters.status_message)) {
            throw new MovieDatabaseApiException(); //nie powiodlo sie
        }

//        int page = reader.getInt(ApiParameters.page_number);
        JSONArray moviesArray = reader.getJSONArray(ApiParameters.results);

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject jsonMovie = moviesArray.getJSONObject(i);


            int id = jsonMovie.optInt(ApiParameters.id, -1);
            String poster_path = jsonMovie.optString(ApiParameters.poster_path, "unknown");
            String overview = jsonMovie.optString(ApiParameters.overview, "unknown");
            String original_title = jsonMovie.optString(ApiParameters.original_title, "unknown");
            String title = jsonMovie.optString(ApiParameters.title, "unknown");
            String relase_date = jsonMovie.optString(ApiParameters.release_date, "unknown");
            String original_language = jsonMovie.optString(ApiParameters.original_language, "unknown");
            float vote_averange = (float) jsonMovie.optDouble(ApiParameters.vote_average, 0);

            //int[] genresIDs = jsonMovie.get
            JSONArray genresIDs = jsonMovie.getJSONArray(ApiParameters.genre_ids);
            String[] genres = new String[genresIDs.length()];

            for (int j = 0; j < genresIDs.length(); j++) {
                genres[j] = genresArray.get(genresIDs.optInt(j));
            }

            //create film and add data
            outList.add(new Movie(id,
                    poster_path,
                    overview,
                    original_title,
                    title,
                    relase_date,
                    original_language,
                    genres,
                    vote_averange));
        }
    }

    static void generateGenreListFromJSONRequest(String jsonRawData, SparseArray<String> outArray) throws JSONException, MovieDatabaseApiException {

        JSONObject reader = new JSONObject(jsonRawData);

        if (reader.has(ApiParameters.status_message)) {
            throw new MovieDatabaseApiException(); //nie powiodlo sie
        }

        JSONArray genresArray = reader.getJSONArray(ApiParameters.genres);

        for (int i = 0; i < genresArray.length(); i++) {
            JSONObject genre = genresArray.getJSONObject(i);

            int key = genre.getInt(ApiParameters.genre_key);
            String value = genre.getString(ApiParameters.genre_value);
            outArray.put(key, value);
        }

    }


}
