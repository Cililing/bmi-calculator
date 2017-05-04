package com.example.przemek.mymoviesv3.MovieDatabaseApi;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Movie implements Cloneable, Serializable {

    private int id = 0;
    private String posterPath = "posterPath";
    private String overview = "overview";
    private String originalTitle = "originalTitle";
    private String title = "title";
    private String releaseDate = "releaseDate";
    private String originalLanguage = "originalLanguage";
    private String[] genres = new String[1]; //TODO genres
    private float voteAverage = 0;

    private Bitmap bitmapPoster = null;

    private float userRating = 0;

    public Movie() {
        //nothing to do
    }

    public Movie(String title, String genre, String year) {

        this.title = title;
        genres[0] = genre;
        this.releaseDate = year;
        this.overview = "No description";
    }

    public Movie(int id, String poster_path, String overview, String original_title, String title, String release_date, String original_language, String[] genres, float vote_averange) {
        this.id = id;
        this.posterPath = poster_path;
        this.overview = overview;
        this.originalTitle = original_title;
        this.title = title;
        this.releaseDate = release_date;
        this.originalLanguage = original_language;
        this.genres = genres;
        this.voteAverage = vote_averange;
    }

    public JSONObject generateJSONObject() throws JSONException {
        JSONObject thisMovie = new JSONObject();
        thisMovie.put(ApiParameters.id, this.id);
        thisMovie.put(ApiParameters.poster_path, this.posterPath);
        thisMovie.put(ApiParameters.overview, this.overview);
        thisMovie.put(ApiParameters.original_title, this.originalTitle);
        thisMovie.put(ApiParameters.title, this.title);
        thisMovie.put(ApiParameters.release_date, this.releaseDate);
        thisMovie.put(ApiParameters.original_language, this.originalLanguage);

        //put genres
        JSONArray movieGenres = new JSONArray();
        for (String genre : this.genres) {
            movieGenres.put(genre);
        }
        //todo genres
        thisMovie.put(ApiParameters.genre_ids, this.genres);

        return thisMovie;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return ApiParameters.defaultPosterRequest + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getGenresList() {
        String genresText = "";
        for (String s : genres) {
            if (s != null) {
                genresText += s + ", ";
            }
        }
        if (!genresText.equals("")) genresText = genresText.substring(0, genresText.length() - 2); //removie ", " from last genre
        return genresText;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Movie(id, posterPath, overview, originalTitle, title, releaseDate, originalLanguage, genres, voteAverage);
    }

    public Bitmap getBitmapPoster() {
        return bitmapPoster;
    }

    public void setBitmapPoster(Bitmap bitmapPoster) {
        this.bitmapPoster = bitmapPoster;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            if (((Movie) obj).id == this.id) {
                return true;
            }
        }

        return false;
    }
}
