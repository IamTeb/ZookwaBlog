package com.example.codetribe.zookwablog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabase;

    private ImageView imageView;
    private ImageButton imageButton;
    private FirebaseStorage mStorage;
    private StorageReference mStorageImage;
    private TextView textUser;
    private TextView textEmail,textContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_images");
        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        imageView = (ImageView) findViewById(R.id.displayImage);
        textEmail = (TextView) findViewById(R.id.textViewEmail);
        textUser = (TextView) findViewById(R.id.textViewUser);
        textContact = (TextView) findViewById(R.id.profileContact);
        imageButton = (ImageButton) findViewById(R.id.imageCall);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        final String email = mUser.getEmail();

        //String logged_in_user = mAuth.getCurrentUser().toString();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String retrieveImage = (String) dataSnapshot.child("image").getValue();
                String retrieveName = (String) dataSnapshot.child("name").getValue();
                String retrieveContact = (String) dataSnapshot.child("contact").getValue();

                textUser.setText(retrieveName);
                textEmail.setText(email);
                textContact.setText(retrieveContact);

                Picasso.with(ProfileActivity.this).load(retrieveImage).centerCrop().into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        //imageView.setImageURI(photoUrl);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String cell = "0735842092";
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //callIntent.setData(Uri.parse("tel:"+Uri.encode(textContact.toString().trim())));
                callIntent.setData(Uri.parse("tel:"+Uri.encode(cell.trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });



        //Picasso.with(context).load(url).into(imageButton);
    }
}
