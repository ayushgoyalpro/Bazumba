package com.ayush.bazumba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ayush.bazumba.R;

public class Login extends AppCompatActivity {
    Window window;
    String email, pduser;

    EditText emailtb;
    EditText passtb;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        emailtb = (EditText) findViewById(R.id.emailfield);
        passtb = (EditText) findViewById(R.id.passfield);
        login = (Button) findViewById(R.id.loginbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailtb.getText().toString();
                pduser = passtb.getText().toString();
                LoginWorker work = new LoginWorker();
                work.contextTarget(Login.this);
                work.execute(email,pduser);
            }
        });
    }

    public void crossClick(View view) {
        Intent intent = new Intent(Login.this, LoginSignup.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this, LoginSignup.class);
        startActivity(intent);
        finish();
    }
}