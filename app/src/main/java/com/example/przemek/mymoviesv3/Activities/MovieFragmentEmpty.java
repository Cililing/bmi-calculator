package com.example.przemek.mymoviesv3.Activities;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.przemek.mymoviesv3.R;

public class MovieFragmentEmpty extends Fragment {

    public MovieFragmentEmpty() {
        super();
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_empty, container, false);
        return view;
    }
}
