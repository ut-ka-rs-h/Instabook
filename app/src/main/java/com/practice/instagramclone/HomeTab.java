package com.practice.instagramclone;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class HomeTab extends Fragment {

    private LinearLayout linearLayout;

    public HomeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        linearLayout = view.findViewById(R.id.linearLayout);

        final TextView helloUser = new TextView(getContext());
        helloUser.setText("Hello " + ParseUser.getCurrentUser().getUsername() + "!");
        LinearLayout.LayoutParams helloUser_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        helloUser_params.setMargins(5, 40, 5, 40);
        helloUser.setLayoutParams(helloUser_params);
        helloUser.setGravity(Gravity.CENTER);
        helloUser.setTextColor(Color.BLACK);
        helloUser.setTextSize(40f);
        helloUser.setTypeface(null, Typeface.BOLD);

        linearLayout.addView(helloUser);

        final View divider = new View(getContext());
        LinearLayout.LayoutParams divider_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                2);
        divider_params.setMargins(0, 0, 0, 15);
        divider.setLayoutParams(divider_params);
        divider.setBackgroundColor(Color.GRAY);

        linearLayout.addView(divider);


        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                        final TextView postDescription = new TextView(getContext());
                        postDescription.setText(post.get("username") + " : " + post.get("image_des"));
                        final TextView postUser = new TextView(getContext());
                        postUser.setText(post.get("username") + "");
                        final TextView postTime = new TextView(getContext());
                        postTime.setText(reportDate);
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(getContext());
                                    LinearLayout.LayoutParams imageView_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(25, 5, 5, 5);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setTextColor(Color.BLACK);
                                    postDescription.setTextSize(20f);

                                    LinearLayout.LayoutParams postUser_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postUser_params.setMargins(25, 25, 5, 10);
                                    postUser.setLayoutParams(postUser_params);
                                    postUser.setTextColor(Color.BLACK);
                                    postUser.setTypeface(null, Typeface.BOLD);
                                    postUser.setTextSize(20f);

                                    LinearLayout.LayoutParams postTime_paras =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postTime_paras.setMargins(25, 0, 5, 40);
                                    postTime.setLayoutParams(postTime_paras);
                                    postTime.setTextColor(Color.LTGRAY);
                                    postTime.setTextSize(15f);

                                    linearLayout.addView(postUser);
                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                    linearLayout.addView(postTime);
                                    //linearLayout.addView(divider);


                                }
                            }
                        });
                    }

                }

                progressDialog.dismiss();
            }
        });


        return view;
    }
}