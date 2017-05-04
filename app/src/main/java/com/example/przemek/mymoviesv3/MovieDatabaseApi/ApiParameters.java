package com.example.przemek.mymoviesv3.MovieDatabaseApi;

public class ApiParameters {


    public static final String authKeyV3 = "1be20bf49cc747dbab1cd4b6c67433ee";
    public static final String authKeyV4 =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                    "eyJhdWQiOiIxYmUyMGJmNDljYzc0N2RiYWIxY" +
                    "2Q0YjZjNjc0MzNlZSIsInN1YiI6IjU5MDVkMW" +
                    "I2OTI1MTQxNjliODAxY2JmZCIsInNjb3BlcyI" +
                    "6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w" +
                    "Qvk8bS8RJlc0EEEzVZ3Fbu8hxMpKHUXZyC4JFAId6Q";


    public static final String defaultPosterRequest = "https://image.tmdb.org/t/p/w500/";

    public static final String genreRequest = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + authKeyV3 +"&language=en-US";

    //movies with title "arrival"
    public static final String eqampleRequestURL = "https://api.themoviedb.org/3/search/movie?api_key=" + authKeyV3 + "&language=en-US&query=arrival&page=1&include_adult=false";

    public static final String status_message = "status_message";
    public static final String page_number = "page";
    public static final String results = "results";

    public static final String genres = "genres";
    public static final String genre_key = "id";
    public static final String genre_value = "name";

    public static final String id = "id";
    public static final String poster_path = "poster_path";
    public static final String overview = "overview";
    public static final String original_title = "original_title";
    public static final String title = "title";
    public static final String release_date = "release_date";
    public static final String original_language = "original_language";
    public static final String genre_ids = "genre_ids";
    public static final String vote_average = "vote_average";



}