package com.ayush.bazumba.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageReturn extends AsyncTask<String,Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... string) {
        String location = "http://192.168.1.6/"+string[0];
        try {
            URL url = new URL(location);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoInput(true);
            http.connect();
            InputStream input = http.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(input);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
