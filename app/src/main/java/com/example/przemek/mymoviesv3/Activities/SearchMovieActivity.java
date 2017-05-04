package com.example.przemek.mymoviesv3.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.AsyncTaskManager;
import com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks.DownloadMoviesListByTitle;
import com.example.przemek.mymoviesv3.R;

public class SearchMovieActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MoviesAdapter mMoviesAdapter;
    Fragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        UserData.loadData(this.getApplicationContext());

        //load start fragmemt
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loadStartFragment();
        }

        //load recycler view
        recyclerView = (RecyclerView) findViewById(R.id.search_movie_recycler_view);
        recyclerView.setItemViewCacheSize(cacheSize);


        //load search view
        searchView = (SearchView) findViewById(R.id.search_movie_search_view);
        searchView.setOnQueryTextListener(new CustomSearchQueryViewListener());

        //set recycler view
        mMoviesAdapter = new MoviesAdapter(UserData.getSearchResult(), new OnMovieItemClick(), getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);
        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.saveData(this.getApplicationContext());

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE & searchResultFragment != null) {
//            getFragmentManager().beginTransaction().remove(searchResultFragment).commit();
//        }
    }

    private void loadStartFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        searchResultFragment = new MovieFragmentEmpty();
        fragmentTransaction.add(R.id.fragment_details, searchResultFragment);
        fragmentTransaction.commit();
    }

    /**
     * Serch handler
     */
    private class CustomSearchQueryViewListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {

            String userRequest = searchView.getQuery().toString();

            if(userRequest.equals("")) {
                Log.i("SearchView", "Empty request");
                return false;
            }

            new DownloadMoviesListByTitle(
                    UserData.getSearchResult(),
                    mMoviesAdapter,
                    getApplicationContext(),
                    ErrorActivity.class)
                    .execute(userRequest);

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    /**
     * Click handler
     */
    private class OnMovieItemClick implements CustomItemClickListener {

        @Override
        public void onClick(View view, int position, String... params) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                onClickPortrait(view, position, params);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                onClickLandspace(view, position, params);
        }

        @Override
        public void onLongClick(View view, int position, String... params) {
            //nothing to do
        }

        private void onClickLandspace(View view, int position, String... params) {

            searchResultFragment = new MovieFragment();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putSerializable("movie", UserData.getSearchResult().get(position));

            searchResultFragment.setArguments(fragmentBundle);
            fragmentTransaction.replace(R.id.fragment_details, searchResultFragment);
            fragmentTransaction.commit();

        }

        private void onClickPortrait(View view, int position, String... params) {
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.putExtra("movie", UserData.getSearchResult().get(position));
            view.getContext().startActivity(i);
        }
    }
}
