package com.ayush.bazumba.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SignupWorker extends AsyncTask<String,Void,String> {

    Context ctx;
    AlertDialog alertDialog;
    String result = "";

    public void targetContext(Context context){
        ctx = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String email = params[1];
        String pass = params[2];

        try {
            URL url = new URL("http://192.168.1.6/signup.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"Utf-8"));
            String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&&"+
                          URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&&"+
                          URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();
            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line = "";
            while((line = reader.readLine()) != null){
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Message");
        alertDialog.setMessage(s);
        alertDialog.show();
    }
}
