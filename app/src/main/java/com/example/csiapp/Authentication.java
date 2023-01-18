package com.example.csiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.csiapp.Adapter.FragmentAdapter;
import com.example.csiapp.databinding.ActivityAuthenticationBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {

    private static final String TAG = Authentication.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ActivityAuthenticationBinding binding;
    FloatingActionButton googleAuthBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        setContentView(R.layout.activity_authentication);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        googleAuthBtn = findViewById(R.id.google_auth_button);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        googleAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }


}