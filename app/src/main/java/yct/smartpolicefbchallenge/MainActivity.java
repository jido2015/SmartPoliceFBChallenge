package yct.smartpolicefbchallenge;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;

    private ImageView imageView;
    private Button goto_report;


    private DatabaseReference mDatabase;
    public String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextView =  findViewById(R.id.nameTextView);
        emailTextView =  findViewById(R.id.emailTextView);
        uidTextView =  findViewById(R.id.uidTextView);
        imageView = findViewById(R.id.main_image);
        goto_report = findViewById(R.id.goto_report);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            Glide.with(this).load(photoUrl).centerCrop().into(imageView);

            String uid = user.getUid();


            nameTextView.setText(name);
            emailTextView.setText(email);
            uidTextView.setText(uid);


            goto_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ReportDetailsActivity.class));
                    finish();
                }
            });


            // Initialize Firebase Auth and Database Reference
            mDatabase = FirebaseDatabase.getInstance().getReference();

            mUserId = user.getUid();


        } else {
            goLoginScreen();
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }



    }

