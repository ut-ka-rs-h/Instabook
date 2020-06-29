package com.practice.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUserName, edtLoginPassword;
    private Button btnLogin;
    private TextView txtSignUp, loginTxtPW;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        edtLoginUserName = findViewById(R.id.edtLoginName);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginTxtPW = findViewById(R.id.loginTxtPW);

        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLogin:
                btnLogin.setAlpha(0.4f);
                loginProgressBar.setAlpha(1);
                loginTxtPW.setAlpha(1);

                ParseUser.logInInBackground(edtLoginUserName.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(LoginActivity.this, user.getUsername() + " is logged in",
                                    Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(LoginActivity.this, "Error in logging in : " + e.getMessage(),
                                    Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
                btnLogin.setAlpha(1);
                loginProgressBar.setAlpha(0);
                loginTxtPW.setAlpha(0);
                break;

            case R.id.txtSignUp:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;

        }
    }
}