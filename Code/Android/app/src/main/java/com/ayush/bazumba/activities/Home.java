package com.ayush.bazumba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ayush.bazumba.R;
import com.ayush.bazumba.api.API;
import com.ayush.bazumba.api.APIResponse;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    private String TAG = Home.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> postList;
    String id = null;
    String user_pass = null;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        postList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.ListView1);
        myDB = new DBHelper(this);
        Cursor resUser = myDB.getUser();
        if (resUser.moveToFirst()) {
            id = resUser.getString(resUser.getColumnIndex("id"));
            user_pass = resUser.getString(resUser.getColumnIndex("password"));
        }
        //Toast.makeText(Home.this,"about to execute",Toast.LENGTH_LONG).show();
//        new GetPosts().execute();
        RequestParams params = new RequestParams();
        params.put("user_id", id);
        params.put("user_pass", user_pass);
        new API().get("http://192.168.1.6/home_content.php", params, new APIResponse() {
            @Override
            public void response(String response, Throwable error) {
                Home.this.updateScreen(response);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e("Clicked Index", "" + position);
            }
        });
    }

    private void updateScreen(String result) {
        try {
            JSONArray posts = new JSONArray(result);
            for (int i = 0; i < posts.length(); i++) {

                JSONObject p = posts.getJSONObject(i);

                String C_ID = p.getString("C_ID");
                String Name = p.getString("Name");
                String Teacher = p.getString("Teacher");
                HashMap<String, String> post = new HashMap<>();
                post.put("C_ID", C_ID);
                post.put("Name", Name);
                post.put("Teacher", Teacher);
                post.put("image_url", p.optString("Thumbnail"));
                postList.add(post);
            }
            updateAdapter();

        } catch (Exception e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            Toast.makeText(getApplicationContext(),
                    "Json parsing error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    private void updateAdapter(){
        PostListAdapter adapter = new PostListAdapter(Home.this, postList);
        //  ListAdapter adapter = new SimpleAdapter(Home.this, postList, R.layout.list_item, new String[]{"Name", "Teacher", "C_ID"}, new int[]{R.id.name, R.id.teacher, R.id.cid});
        lv.setAdapter(adapter);
    }


    public void MenuClick(View view) {

    }

    private class GetPosts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(Home.this,"JSON downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String result = "";
            try {
                URL url = new URL("http://192.168.1.6/home_content.php");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                final String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&&" + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result.equals("You have been logged out !")) {
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                try {
                    JSONArray posts = new JSONArray(result);
                    for (int i = 0; i < posts.length(); i++) {

                        JSONObject p = posts.getJSONObject(i);

                        String C_ID = p.getString("C_ID");
                        String Name = p.getString("Name");
                        String Teacher = p.getString("Teacher");
                        HashMap<String, String> post = new HashMap<>();
                        post.put("C_ID", C_ID);
                        post.put("Name", Name);
                        post.put("Teacher", Teacher);
                        post.put("image_url", p.optString("Thumbnail"));
                        postList.add(post);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            PostListAdapter adapter = new PostListAdapter(Home.this, postList);
            //  ListAdapter adapter = new SimpleAdapter(Home.this, postList, R.layout.list_item, new String[]{"Name", "Teacher", "C_ID"}, new int[]{R.id.name, R.id.teacher, R.id.cid});
            lv.setAdapter(adapter);
        }
    }
}