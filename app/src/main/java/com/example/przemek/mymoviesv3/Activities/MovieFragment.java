package com.example.przemek.mymoviesv3.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieFragment extends Fragment {

    private Movie movie;
    private boolean movieInCollection;

    @BindView(R.id.fragment_movie_poster) ImageView poster;
    @BindView(R.id.fragment_movie_details_title) TextView title;
    @BindView(R.id.fragment_movie_overview) TextView overview;
    @BindView(R.id.fragment_movie_add_button) Button addButton;


    public MovieFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);

        //load data
        movie = (Movie) getArguments().getSerializable("movie");
        generateThisShit();

        return view;
    }


    @OnClick(R.id.fragment_movie_add_button)
    public void OnAddToFavourities() {

        if (movie == null) return;
        try {
            //check if is film in your collection
            //user dont have this film in his collecion
            if (!movieInCollection) {
                UserData.getUserMovies().add((Movie) movie.clone());
                Tools.makeLongToast(getActivity().getApplicationContext(), getResources().getString(R.string.details_film_ok));
                movieInCollection = true;
            }
            //user have it in his collection
            else {
                UserData.getUserMovies().remove(movie);
                Tools.makeLongToast(getActivity().getApplicationContext(), getResources().getString(R.string.details_film_deleted));
                movieInCollection = false;
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        setButtonName();
    }

    private void generateThisShit() {
        new DownloadImageTask(poster, getActivity().getApplicationContext(), null).execute(movie.getPosterPath());
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());
        movieInCollection = Tools.chceckIfMovieIsInYourCollection(movie);
        setButtonName();
    }

    private void setButtonName() {
        if (!movieInCollection) addButton.setText(R.string.button_add_to_favourities);
        if (movieInCollection) addButton.setText(R.string.button_remove_from_favourities);
    }

}
