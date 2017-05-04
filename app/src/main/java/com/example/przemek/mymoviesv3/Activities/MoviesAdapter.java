package com.example.przemek.mymoviesv3.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadImageTask;
import com.example.przemek.mymoviesv3.MovieDatabaseApi.Movie;
import com.example.przemek.mymoviesv3.R;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private ArrayList<Movie> moviesList;
    private CustomItemClickListener listener;
    Context mContext;

    public MoviesAdapter(ArrayList<Movie> moviesList, CustomItemClickListener listener, Context mContext) {
        this.moviesList = moviesList;
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieAverageRating.setText(String.valueOf(movie.getVoteAverage()));
        holder.movieReleaseDate.setText(movie.getReleaseDate());
        holder.movieGenres.setText(movie.getGenresList());
        //load img
        if (movie.getBitmapPoster() == null) {

            //there is no bitmapposter saved
            new DownloadImageTask(holder.moviePoster, mContext, null).execute(movie.getPosterPath());
        } else {
            holder.moviePoster.setImageBitmap(movie.getBitmapPoster());
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView movieTitle;
        private TextView movieReleaseDate;
        private TextView movieAverageRating;
        private TextView movieGenres;
        private ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_row_title);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movie_row_release_date);
            movieAverageRating = (TextView) itemView.findViewById(R.id.movie_row_vote_average);
            movieGenres = (TextView) itemView.findViewById(R.id.movie_row_genres);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_row_poster);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, this.getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v, this.getAdapterPosition());
            return true;
        }
    }

}
