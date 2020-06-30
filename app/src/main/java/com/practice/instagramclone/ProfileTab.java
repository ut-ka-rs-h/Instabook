package com.practice.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

        // TODO: Rename and change types of parameters
    private EditText edtNamePT, edtUsernamePT, edtBioPT, edtEmailPT, edtGenderPT, edtPhoneNumberPT, edtDOBPT;
    private FloatingActionButton fabUpdatePT;
    private TextView txtCPP;

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

        fabUpdatePT = view.findViewById(R.id.fabUpdatePT);

        final ParseUser parseUser = ParseUser.getCurrentUser();

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

        fabUpdatePT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });

        return view;
    }
}