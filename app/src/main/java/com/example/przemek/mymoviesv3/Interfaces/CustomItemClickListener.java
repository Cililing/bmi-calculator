package com.example.przemek.mymoviesv3.Interfaces;

import android.view.View;

public interface CustomItemClickListener {

    void onClick(View view, int position, String... params);
    void onLongClick(View view, int position, String... params);

}
