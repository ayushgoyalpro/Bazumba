package com.ayush.bazumba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ayush.bazumba.R;

public class Signup extends AppCompatActivity {
    EditText nameuser;
    EditText emailuser;
    EditText passuser;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameuser = (EditText) findViewById(R.id.namefield);
        emailuser = (EditText) findViewById(R.id.emailfield);
        passuser = (EditText) findViewById(R.id.passfield);
        signup = (Button) findViewById(R.id.signupbutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailuser.getText().toString();
                String name = nameuser.getText().toString();
                String pass = passuser.getText().toString();

                SignupWorker work = new SignupWorker();
                work.targetContext(Signup.this);
                work.execute(name,email,pass);
            }
        });
    }

    public void crossClick(View view) {
        Intent intent = new Intent(Signup.this, LoginSignup.class);
        startActivity(intent);
        finish();
    }
}