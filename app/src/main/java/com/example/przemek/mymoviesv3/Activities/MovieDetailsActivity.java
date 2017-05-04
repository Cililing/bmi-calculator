package com.example.przemek.mymoviesv3.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;
    boolean movieInCollection = false;

    @BindView(R.id.details_poster) ImageView poster;
    @BindView(R.id.details_title) TextView title;
    @BindView(R.id.details_original_title) TextView originalTitle;
    @BindView(R.id.details_release_date) TextView releaseDate;
    @BindView(R.id.details_genres) TextView genres;
    @BindView(R.id.details_overview) TextView overview;
    @BindView(R.id.details_voteAverage) TextView voteAverage;
    @BindView(R.id.details_language) TextView language;
    @BindView(R.id.details_add_button) Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        UserData.loadData(this.getApplicationContext());

        movie = (Movie) getIntent().getExtras().getSerializable("movie");
        if (movie == null) movie = new Movie();

        ButterKnife.bind(this);
        generateThisShit();

    }

    private void setButtonName() {
        if (!movieInCollection) addButton.setText(R.string.button_add_to_favourities);
        if (movieInCollection) addButton.setText(R.string.button_remove_from_favourities);
    }

    private void generateThisShit() {

        new DownloadImageTask(poster, this.getApplicationContext(), null).execute(movie.getPosterPath());

        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        genres.setText(movie.getGenresList());
        overview.setText(movie.getOverview());
        voteAverage.setText(String.valueOf(movie.getVoteAverage()));
        language.setText(movie.getOriginalLanguage());

        movieInCollection = Tools.chceckIfMovieIsInYourCollection(movie);
        setButtonName();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.saveData(this.getApplicationContext());
    }

    @OnClick(R.id.details_add_button)
    public void OnAddToFavourities() {
        try {
            //check if is film in your collection

            //user dont have this film in his collecion
            if (!movieInCollection) {
                UserData.getUserMovies().add((Movie) movie.clone());
                Tools.makeLongToast(getApplicationContext(), getResources().getString(R.string.details_film_ok));
                movieInCollection = true;
            }
            //user have it in his collection
            else {
                UserData.getUserMovies().remove(movie);
                Tools.makeLongToast(getApplicationContext(), getResources().getString(R.string.details_film_deleted));
                movieInCollection = false;
            }


        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        setButtonName();
    }

}
