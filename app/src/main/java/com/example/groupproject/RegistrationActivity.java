package com.example.groupproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupproject.databinding.ActivityRegisterBinding;
import com.example.groupproject.p2pFunction.Constants;
import com.example.groupproject.p2pFunction.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This activity is for users to register a new account and to initialise the user data associated
 * with that user's new account.
 *
 * @author ${Kyle Greenwood}
 */

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    //Wendi added code here to fix code smell about our app design, aiming to ensure continuous tracking of the current user's information:
    private FirebaseFirestore firestore;
    private PreferenceManager preferenceManager;
    private ActivityRegisterBinding binding;

    //Code ends

    private boolean imgSelected = false;

    private final String defaultImg = "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";

    private String imgString = defaultImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Wendi changed code here
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the current user
        preferenceManager = new PreferenceManager(getApplicationContext());
        //Code ends

        // Instantiations.
        Button registerBtn = findViewById(R.id.btn_reg);
        Button imgBtn = findViewById(R.id.btn_reg_img);

        EditText emailInput, passInput, pwconfInput, nameInput;
        emailInput = findViewById(R.id.eT_reg_email);
        nameInput = findViewById(R.id.eT_personName);
        passInput = findViewById(R.id.eT_reg_password);
        pwconfInput = findViewById(R.id.eT_reg_pwconfirm);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         */

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Attempt to register, return relevant errors or return to login activity.
        registerBtn.setOnClickListener(v -> {
            String email, password, pwconfirm, name;
            email = emailInput.getText().toString().toLowerCase(); //email is case insensitive
            name = nameInput.getText().toString();
            password = passInput.getText().toString();
            pwconfirm = pwconfInput.getText().toString();

            // Make sure conditions are met to register user.
            if (!Objects.equals(password,pwconfirm)) {
                Toast.makeText(getApplicationContext(),"Passwords do not match.",Toast.LENGTH_LONG).show();
                passInput.setText("");
                pwconfInput.setText("");
            } else if(!imgSelected){
                Toast.makeText(getApplicationContext(),"Default image will be used if you don't select a profile picture.",Toast.LENGTH_LONG).show();
                imgString = defaultImg;
                imgSelected = true;
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save registered user data.
                            FirebaseUser currentUser = auth.getCurrentUser();
                            saveUserNameToDatabase(currentUser.getUid(), email, name);
                            Toast.makeText(getApplicationContext(), "Registration Successful.", Toast.LENGTH_LONG).show();

                            //Wendi changed some code here:
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //Code ends here

                            finish();
                        } else {
                            // Resolve errors if task fails, give useful information to user about
                            //possible reasons for the error.
                            if (password.length() < 6) {
                                Toast.makeText(getApplicationContext(), "Password must be minimum 6 characters.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed. Email may already be used.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        // Start pop-up to choose a profile image.
        imgBtn.setOnClickListener(z -> {
            Intent imgSelect = new Intent(MediaStore.ACTION_PICK_IMAGES);
            startActivity(imgSelect);
        });

        // Get result of user choosing image.
        registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            if(!Objects.isNull(o.getData())){
                                Uri imgUri = o.getData().getData();

                                // Convert Uri to string so it can be stored in firestore. First
                                //by converting to bitmap, then encoding the bitmap to string.
                                Bitmap imgData = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imgUri);
                                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                                imgData.compress(Bitmap.CompressFormat.JPEG,100,outStream);
                                byte[] imgBytes = outStream.toByteArray();

                                imgString = Base64.getEncoder().encodeToString(imgBytes);
                            } else {
                                // Use pre-encoded image (default) if there's an error or user does not
                                //choose an image.
                                Toast.makeText(getApplicationContext(), "Image not found, default image used.", Toast.LENGTH_LONG).show();
                                imgString = defaultImg;
                            }
                            imgSelected = true;
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Image invalid, default image used.", Toast.LENGTH_LONG).show();
                            imgString = defaultImg;
                            imgSelected = true;
                        }
                    }
                }
        );
    }

    private void saveUserNameToDatabase(String userId, String email, String name) {
        //New version uses Firestore instead of Firebase.

        //Wendi added some codes here
        // create data for Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("name", name);
        user.put("image", imgString);
        user.put("following", "");

        // save data to Firestore
        DocumentReference userDoc = firestore.collection("users").document(userId);
        userDoc.set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("RegistrationActivity", "User data saved to Firestore.");
                Toast.makeText(getApplicationContext(), "User data saved to Firestore.", Toast.LENGTH_LONG).show();
            } else {
                Log.e("RegistrationActivity", "Failed to save user data to Firestore.", task.getException());
                Toast.makeText(getApplicationContext(), "Failed to save user data to Firestore.", Toast.LENGTH_LONG).show();
            }
        });

        //Wendi added code here to fix code smell about our app design, aiming to ensure continuous tracking of the current user's information:
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        preferenceManager.putString(Constants.KEY_USER_ID,userId);
        preferenceManager.putString(Constants.KEY_USER_NAME, binding.eTPersonName.getText().toString());
        preferenceManager.putString(Constants.KEY_IMAGE, imgString);
        //Code ends here

    }
}