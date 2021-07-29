package com.ayush.bazumba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.view.Window;

import com.ayush.bazumba.R;

public class SplashScreen extends AppCompatActivity {
    Window window;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.splashblue));
        }
        myDB = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor resUser = myDB.getUser();
        if(resUser.getCount() == 0) {
            Cursor res = myDB.getData();
            if (res.getCount() == 0) {
                finish();
            } else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(0));
                }
                if (buffer.toString().equals("0")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashScreen.this, InfoPage1.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashScreen.this, LoginSignup.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }
            }
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }
}