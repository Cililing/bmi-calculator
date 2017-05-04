package com.example.przemek.mymoviesv3.MovieDatabaseApi;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.przemek.mymoviesv3.MovieDatabaseApi.MovieDatabaseNetwork.generateSearchMovieURLbyTitle;

public class MovieDatabase {

    public static void getMoviesListByTitle(String title, ArrayList<Movie> outList) throws IOException, JSONException, MovieDatabaseApiException {

        //generate url
        String url = generateSearchMovieURLbyTitle(title);

        //generate json request
        String JSONRequest = MovieDatabaseNetwork.downloadJSONRawData(url);

        //get movie list and save it in outList
        ServerAnswerPraser.generateMovieListFromJSONRequest(JSONRequest, outList);
    }

    /**
     * @param url filename of poster in database
     * @return full poster url
     */
    public static String getPosterURL(String url) {
        return ApiParameters.defaultPosterRequest + url;
    }

}
