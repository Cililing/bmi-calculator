package com.example.przemek.mymoviesv3.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.przemek.mymoviesv3.Interfaces.CustomItemClickListener;
import com.example.przemek.mymoviesv3.R;

public class MainActivity extends AppCompatActivity {

    public static final int cacheSize = 20;

    private RecyclerView recyclerView;
    private MoviesAdapter mMoviesAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        UserData.loadData(this.getApplicationContext());

        floatingActionButton = (FloatingActionButton) findViewById(R.id.main_acticity_floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingButtomClick();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        recyclerView.setItemViewCacheSize(cacheSize);


        mMoviesAdapter = new MoviesAdapter(UserData.getUserMovies(), new OnMovieItemClickOpenDetails(), getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_search_activity:
                Intent intent = new Intent(this, SearchMovieActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void onFloatingButtomClick() {
        Intent intent = new Intent(this, SearchMovieActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserData.saveData(this.getApplicationContext());
    }

    private class OnMovieItemClickOpenDetails implements CustomItemClickListener {

        @Override
        public void onClick(View view, int position, String... params) {
            Intent i = new Intent(view.getContext(), MovieDetailsActivity.class);
            i.putExtra("movie", UserData.getUserMovies().get(position));
            view.getContext().startActivity(i);
        }

        @Override
        public void onLongClick(View view, int position, String... params) {
            //TODO
        }
    }


//    private void prepareMovieData() {
//
//
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        try {
//            String url = MovieDatabaseNetwork.generateSearchMovieURLbyTitle("arrival");
//            String answer = MovieDatabaseNetwork.downloadMoviesJSON(url);
//            ServerAnswerPraser.parseJSONRequest(answer, UserData.userMovies);
//        } catch (JSONException | MovieDatabaseApiException | IOException e) {
//            e.printStackTrace();
//        }
//
//
//        mMoviesAdapter.notifyDataSetChanged();
//    }

}
