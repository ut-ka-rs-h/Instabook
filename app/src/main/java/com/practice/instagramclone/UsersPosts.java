package com.practice.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        //FancyToast.makeText(this, receivedUserName, Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

        setTitle(receivedUserName + "'s posts");

        final View divider = new View(UsersPosts.this);
        LinearLayout.LayoutParams divider_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                2);
        divider_params.setMargins(0, 0, 0, 20);
        divider.setLayoutParams(divider_params);
        divider.setBackgroundColor(Color.GRAY);

        ParseQuery<ParseObject> parseQueryA = new ParseQuery<ParseObject>("Profile");
        parseQueryA.whereEqualTo("username", receivedUserName);
        parseQueryA.orderByDescending("createdAt");

        parseQueryA.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                //imgViewUserPP.setScaleType(CircleImageView.ScaleType.FIT_START);

                if (objects.size() > 0 && e == null){
                    ParseObject profilePics = objects.get(0);
                    ParseFile myProfilePicture = (ParseFile) profilePics.get("profilePicture");

                    if (myProfilePicture != null) {
                        myProfilePicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null){
                                    Bitmap userProfilePic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    final CircleImageView imgViewUserPP = new CircleImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageView_params =  new LinearLayout.LayoutParams( 200,
                                            200);
                                    imageView_params.setMargins(20,5,5,5);
                                    imgViewUserPP.setLayoutParams(imageView_params);
                                    imgViewUserPP.setImageBitmap(userProfilePic);

                                    final TextView username = new TextView(UsersPosts.this);
                                    username.setText(receivedUserName);
                                    LinearLayout.LayoutParams username_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    username_params.setMargins(50, 5, 5, 40);
                                    username.setLayoutParams(username_params);
                                    username.setTextColor(Color.BLACK);
                                    username.setTextSize(20f);
                                    username.setTypeface(null, Typeface.BOLD);



                                    linearLayout.addView(imgViewUserPP);
                                    linearLayout.addView(username);
                                    linearLayout.addView(divider);

                                }
                            }
                        });
                    }
                }
                else {
                    final CircleImageView imgViewUserPP = new CircleImageView(UsersPosts.this);
                    LinearLayout.LayoutParams imageView_params =  new LinearLayout.LayoutParams( 200,
                            200);
                    imageView_params.setMargins(30,30,5,5);
                    imgViewUserPP.setLayoutParams(imageView_params);
                    imgViewUserPP.setImageResource(R.drawable.login);

                    final TextView username = new TextView(UsersPosts.this);
                    username.setText(receivedUserName);
                    LinearLayout.LayoutParams username_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    username_params.setMargins(50, 5, 5, 40);
                    username.setLayoutParams(username_params);
                    username.setTextColor(Color.BLACK);
                    username.setTextSize(20f);
                    username.setTypeface(null, Typeface.BOLD);

                    linearLayout.addView(imgViewUserPP);
                    linearLayout.addView(username);
                    linearLayout.addView(divider);
                }

            }
        });



        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", receivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null){

                    for (ParseObject post : objects){
                        Date date = post.getCreatedAt();
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String reportDate = dateFormat.format(date);
                        final TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(receivedUserName + " : " + post.get("image_des"));
                        final TextView postTime = new TextView(UsersPosts.this);
                        postTime.setText(reportDate);
                        final TextView post_user = new TextView(UsersPosts.this);
                        post_user.setText(receivedUserName);
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageView_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(25, 5, 5, 40);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setTextColor(Color.BLACK);
                                    postDescription.setTextSize(20f);

                                    LinearLayout.LayoutParams postUser_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postUser_params.setMargins(25, 25, 5, 10);
                                    post_user.setLayoutParams(postUser_params);
                                    post_user.setTextColor(Color.BLACK);
                                    post_user.setTypeface(null, Typeface.BOLD);
                                    post_user.setTextSize(20f);

                                    LinearLayout.LayoutParams postTime_paras =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postTime_paras.setMargins(25, 0, 5, 40);
                                    postTime.setLayoutParams(postTime_paras);
                                    postTime.setTextColor(Color.LTGRAY);
                                    postTime.setTextSize(15f);

                                    linearLayout.addView(post_user);
                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                    linearLayout.addView(postTime);
                                    //linearLayout.addView(divider);



                                }
                            }
                        });
                    }

                }
                else {
                    FancyToast.makeText(UsersPosts.this, receivedUserName + " has not posted anything yet!",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                    final TextView noPost = new TextView(UsersPosts.this);
                    noPost.setText("No posts");
                    LinearLayout.LayoutParams noPost_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    noPost_params.setMargins(5, 100, 5, 5);
                    noPost.setLayoutParams(noPost_params);
                    noPost.setTextColor(Color.GRAY);
                    noPost.setTextSize(30f);
                    noPost.setGravity(Gravity.CENTER);
                    linearLayout.addView(noPost);

                }
            progressDialog.dismiss();
            }
        });


    }
}