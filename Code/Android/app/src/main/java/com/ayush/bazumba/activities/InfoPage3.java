package com.ayush.bazumba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ayush.bazumba.R;

public class InfoPage3 extends AppCompatActivity {
    Window window;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page3);
        myDB = new DBHelper(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
    }

    public void onClickNext(View view) {
        boolean ok = myDB.setOpened();
        Intent intent = new Intent(InfoPage3.this, LoginSignup.class);
        startActivity(intent);
        finish();
    }
}