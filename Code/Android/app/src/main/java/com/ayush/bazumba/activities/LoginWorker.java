package com.ayush.bazumba.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

public class LoginWorker extends AsyncTask<String,Void,String> {
    Context ctx;
    AlertDialog alertDialog;
    DBHelper myDB;

    public void contextTarget(Context context){
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String email = params[0];
        String pduser = params[1];


        try {
            URL url = new URL("http://192.168.1.6/login.php");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);
            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
            String data = URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&&"+URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(pduser,"UTF-8");
            writer.write(data);
            /*
            void main{
                for(int i=0;i<n;i++){
                    if(false){
                        System.out.print("I'mthe best");0
                        Hey guys. todsy id gsnesh vhsutrthi
                    }
                }
            }
             */
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
        if(s.equals("wrong data inserted")){
            alertDialog.setMessage("Invalid email or password");
            alertDialog.show();
        }
        else{
            String[] returned = s.split(" ");
            //Toast.makeText(ctx,returned[4],Toast.LENGTH_LONG).show();
            myDB = new DBHelper(ctx);
            boolean ok = myDB.inputUser(returned);
            if(ok == true){
                Intent intent = new Intent(ctx, Home.class);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
            else{
                alertDialog.setMessage("Fatal Error");
                alertDialog.show();
            }
        }
    }
}
