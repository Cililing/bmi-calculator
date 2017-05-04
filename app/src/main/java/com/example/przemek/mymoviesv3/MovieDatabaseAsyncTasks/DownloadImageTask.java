package com.example.przemek.mymoviesv3.MovieDatabaseAsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

//Set image downloaded from internet
public class DownloadImageTask extends android.os.AsyncTask<String, Void, Bitmap> {

    private final int emptyPosterID;
    private ImageView imageView;
    private Context mContext;
    private Class networkErrorActivity;

    /**
     * Create new asynch task to download image data. Start networkErrorActivity when connection fails.
     * @param imageView iV where you want place an image
     * @param mContext application context
     * @param networErrorActivity an error activity, type null to do nth with error
     */
    public DownloadImageTask(ImageView imageView, Context mContext, Class networErrorActivity) {
        this.imageView = imageView;
        this.mContext = mContext;
        emptyPosterID = mContext.getResources().getIdentifier("emptyposter.jpg", "drawable", mContext.getPackageName());
        this.networkErrorActivity = networErrorActivity;
        AsyncTaskManager.asyncTasks.add(this);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        InputStream in = null;
        String urldisplay = urls[0];
        Bitmap result = null;

        try {
            in = new java.net.URL(urldisplay).openStream();
            result = BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException fnfe) {
            result = BitmapFactory.decodeResource(mContext.getResources(), emptyPosterID);
        } catch (MalformedURLException e) {
            if (networkErrorActivity != null) {
                Intent i = new Intent(mContext, networkErrorActivity);
                i.putExtra("last_exception", e);
                mContext.startActivity(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        AsyncTaskManager.asyncTasks.remove(this);
    }
}