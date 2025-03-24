package com.example.groupproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupproject.databinding.ActivityLoginBinding;
import com.example.groupproject.p2pFunction.Constants;
import com.example.groupproject.p2pFunction.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    //Wendi added code here to fix code smell about our app design, aiming to ensure continuous tracking of the current user's information.
    private PreferenceManager preferenceManager;

    private ActivityLoginBinding binding;

    //Code ends

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Wendi added code here:
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());

        setContentView(binding.getRoot());
        //Code ends here


        // Instantiations.
        Button loginBtn, regActBtn;
        loginBtn = findViewById(R.id.btn_login);
        regActBtn = findViewById(R.id.btn_login_reg_activity);

        EditText emailInput, passInput;
        emailInput = findViewById(R.id.eT_login_email);
        passInput = findViewById(R.id.eT_login_password);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         * @studentID :- u7632488
         */

        auth = FirebaseAuth.getInstance();

        // Attempt to login, go to next activity or return error.
        loginBtn.setOnClickListener(login -> {
            String email, password;
            email = emailInput.getText().toString().toLowerCase(); //email is case insensitive
            password = passInput.getText().toString();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //Wendi changed code here
                        signIn();
                        Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        //code ends

                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed, please try again.", Toast.LENGTH_LONG).show();
                        emailInput.setText("");
                        passInput.setText("");
                    }
                }
            });
        });

        // Go to registration activity.
        regActBtn.setOnClickListener(r -> {
            Intent startRegistration = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(startRegistration);
        });
    }


    //Wendi added code here
    private void signIn(){
        // get the current user from firebase auth
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // use email in Firestore as conditions
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_USERS)
                    .whereEqualTo(Constants.KEY_EMAIL, currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null
                                && task.getResult().getDocuments().size() > 0) {
                            // get data in Firestore and update PreferenceManager
                            //Wendi added code here to fix code smell about our app design, aiming to ensure continuous tracking of the current user's information.
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                            preferenceManager.putString(Constants.KEY_USER_NAME, documentSnapshot.getString(Constants.KEY_USER_NAME));
                            preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                            //ends
                        }else {
                            Log.e(TAG, "Didn't saved data in firebase!");
                        }
                    });
        }
    }

    //code ends

}
