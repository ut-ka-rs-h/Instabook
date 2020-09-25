package com.practice.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ProfileTab extends Fragment implements View.OnClickListener{

    private EditText edtNamePT, edtUsernamePT, edtBioPT, edtEmailPT, edtGenderPT, edtPhoneNumberPT, edtDOBPT;
    private FloatingActionButton fabUpdatePT;
    private ImageView imgProfilePicPT;
    private TextView txtCPP;

    private Bitmap receivedImageBitmap;
    public Bitmap userProfilePic;

    final ParseUser parseUser = ParseUser.getCurrentUser();

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtNamePT = view.findViewById(R.id.edtNamePT);
        edtUsernamePT = view.findViewById(R.id.edtUsernamePT);
        edtBioPT = view.findViewById(R.id.edtBioPT);
        edtEmailPT = view.findViewById(R.id.edtEmailPT);
        edtGenderPT = view.findViewById(R.id.edtGenderPT);
        edtPhoneNumberPT = view.findViewById(R.id.edtPhoneNumberPT);
        edtDOBPT = view.findViewById(R.id.edtDOBPT);

        imgProfilePicPT = view.findViewById(R.id.imgProfilePicPT);
        txtCPP = view.findViewById(R.id.txtCPP);

        fabUpdatePT = view.findViewById(R.id.fabUpdatePT);

        if (parseUser.get("name") == null) {edtNamePT.setText("");}
        else {edtNamePT.setText(parseUser.get("name") + "");}

        if (parseUser.get("username") == null) {edtUsernamePT.setText("");}
        else {edtUsernamePT.setText(parseUser.get("username") + "");}

        if (parseUser.get("bio") == null) {edtBioPT.setText("");}
        else {edtBioPT.setText(parseUser.get("bio") + "");}

        if (parseUser.get("email") == null) {edtEmailPT.setText("");}
        else {edtEmailPT.setText(parseUser.get("email") + "");}

        if (parseUser.get("dateOfBirth") == null) {edtDOBPT.setText("");}
        else {edtDOBPT.setText(parseUser.get("dateOfBirth") + "");}

        if (parseUser.get("gender") == null) {edtGenderPT.setText("");}
        else {edtGenderPT.setText(parseUser.get("gender") + "");}

        if (parseUser.get("phoneNumber") == null) {edtPhoneNumberPT.setText("");}
        else {edtPhoneNumberPT.setText(parseUser.get("phoneNumber") + "");}



        fabUpdatePT.setOnClickListener(ProfileTab.this);
        txtCPP.setOnClickListener(ProfileTab.this);


        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Profile");
        parseQuery.whereEqualTo("username", parseUser.get("username") + "");
        parseQuery.orderByDescending("createdAt");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null){
                        ParseObject profilePics = objects.get(0);
                        ParseFile myProfilePicture = (ParseFile) profilePics.get("profilePicture");
                        myProfilePicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null){
                                    userProfilePic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    imgProfilePicPT.setImageBitmap(userProfilePic);
                                }
                                else {imgProfilePicPT.setImageResource(R.drawable.login);}
                            }
                        });
                }
                else {
                    imgProfilePicPT.setImageResource(R.drawable.login);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fabUpdatePT:

                parseUser.put("name", edtNamePT.getText().toString());
                parseUser.put("username", edtUsernamePT.getText().toString());
                parseUser.put("bio", edtBioPT.getText().toString());
                parseUser.put("email", edtEmailPT.getText().toString());
                parseUser.put("dateOfBirth", edtDOBPT.getText().toString());
                parseUser.put("gender", edtGenderPT.getText().toString());
                parseUser.put("phoneNumber", edtPhoneNumberPT.getText().toString());


                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){
                            FancyToast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true)
                                    .show();
                        }
                        else {FancyToast.makeText(getContext(), "There was an error updating the info", Toast.LENGTH_SHORT,FancyToast.ERROR,true)
                                .show();}
                        progressDialog.dismiss();
                    }
                });

                break;

            case R.id.txtCPP:
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},4000);

                }
                else {
                    getChosenImage();
                }
                break;

        }
    }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 5000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 4000){

            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5000) {

            if (resultCode == Activity.RESULT_OK){

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn,
                            null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgProfilePicPT.setImageBitmap(receivedImageBitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //to convert image to an array of bytes..
                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("img.png", bytes);
                    ParseObject parseObject = new ParseObject("Profile");
                    parseObject.put("profilePicture", parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(getContext(), "Profile picture uploaded successfully!",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,true).show();
                            }
                            else {FancyToast.makeText(getContext(), "Unknown Error : " + e.getMessage(),
                                    Toast.LENGTH_SHORT, FancyToast.INFO,true).show();}
                        }

                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

}