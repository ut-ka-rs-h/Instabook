package com.practice.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.widget.ProgressBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtUserName, edtPassword;
    private Button btnSignUp;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("SignUp");

        edtEmail = findViewById(R.id.edtEmail);
        edtUserName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);

        btnSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);


        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnSignUp:

                final ParseUser user = new ParseUser();
                user.setUsername(edtUserName.getText().toString());
                user.setPassword(edtPassword.getText().toString());
                user.setEmail(edtEmail.getText().toString());


                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing up " + edtUserName.getText().toString());
                progressDialog.show();
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(SignUp.this, user.getUsername().toString() + " is signed up",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent1 = new Intent(SignUp.this, LoginActivity.class);
                            startActivity(intent1);
                            // Hooray! Let them use the app now.
                        } else {
                            FancyToast.makeText(SignUp.this, "Error in signing up : " + e.getMessage(),
                                    Toast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                        progressDialog.dismiss();
                    }
                });

                break;



            case R.id.txtLogin:

                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;


        }


    }


}