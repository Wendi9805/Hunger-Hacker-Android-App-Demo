package com.example.groupproject.dataProfileFunction;
import com.example.groupproject.FollowingActivity;
import com.example.groupproject.SearchPage;
import com.example.groupproject.LoadActivity;
import com.example.groupproject.UserListActivity;
import com.example.groupproject.LoginActivity;
import com.example.groupproject.dataGraphicalViewer.DataGraphicalFunction;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.example.groupproject.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import javax.annotation.Nullable;

/**
 *This ProfileActivity displays the user's profile information
 * including their name, email, and profile image. It retrieves
 * the data from Firebase Firestore and displays it in the corresponding
 * TextViews and ImageView. It also allows the user to change their
 * profile image by launching the EditActivity with the current profile image URI
 * passed as an extra. The logout method handles user logout by signing out from
 * Firebase Authentication and redirecting to the login page.
 * authored and implemented the complete feature requirement end to end
 * @author ${Tohfa Siddika Barbhuiya}
 */

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView fullName,email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Button changeProfileImage;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName = findViewById(R.id.profileName);
        email    = findViewById(R.id.profileEmail);



        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);

        /**
         * Verified that data used in this file is persisted in firebase
         * @Verified by:- Muhammad Arslan Amjad Qureshi
         */

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }


        });



        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    fullName.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));


                        // If imageURL is null, use the default image
                        String defaultImage = documentSnapshot.getString("image");
                        if (defaultImage != null) {
                            // Load the default image from the Base64-encoded string using Picasso
                            byte[] decodedString = Base64.decode(defaultImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profileImage.setImageBitmap(decodedByte);
                        }
                    }
                else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent i = new Intent(v.getContext(),EditActivity.class);
                i.putExtra("fullName",fullName.getText().toString());
                i.putExtra("email",email.getText().toString());

                String profileImageUri = profileRef.toString();
                i.putExtra("profileImageUri", profileImageUri);

                startActivity(i);
//

            }
        });

        //buttons to jump from one page to another
        Button buttonSearchButton = findViewById(R.id.searchButton);
        Button loadButton = findViewById(R.id.homeButton);
        Button userListButton = findViewById(R.id.userListButton);
        Button profileButton = findViewById(R.id.profile);
        Button dataButton = findViewById(R.id.data);
        Button followingButton = findViewById(R.id.btn_profile_following);
        //Follow feature integration
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent following = new Intent(getApplicationContext(), FollowingActivity.class);
                startActivity(following);
            }
        });
        //Follow feature integration ends
        //UI Integration
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoadActivity.class);
                startActivity(intent);
//              finish();
            }

        });
        buttonSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchPage.class);
                startActivity(intent);
//              finish();
            }

        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserListActivity.class);
                startActivity(intent);
//              finish();
            }

        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
//              finish();
            }

        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DataGraphicalFunction.class);
                startActivity(intent);
//              finish();
            }

        });
        //UI Integration ends

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }


}