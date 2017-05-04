package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;


import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncTaskManager {

    static ArrayList<AsyncTask> asyncTasks = new ArrayList<>();

    public static void cancelAll() {
        for (AsyncTask asyncTask : asyncTasks) {
            try {
                asyncTask.get(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                asyncTask.cancel(true);
            }
        }
    }

}
