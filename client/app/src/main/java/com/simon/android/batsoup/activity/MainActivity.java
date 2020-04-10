package com.simon.android.batsoup.activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.android.batsoup.CustomAdapter;
import com.simon.android.batsoup.R;
import com.simon.android.batsoup.fragments.SearchFragment;
import com.simon.android.batsoup.fragments.StatusFragment;
import com.simon.android.batsoup.interfaces.ApiInterface;
import com.simon.android.batsoup.model.User;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private  ArrayList<User> globalUsers = new ArrayList<>();
    public static ApiInterface apiInterface;
    public static String token;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        token = "Bearer "+getIntent().getExtras().getString("Token");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
         BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemReselectedListener);
        changeFragment(new SearchFragment());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.radar: fragment = new SearchFragment(); break;
                        case R.id.status: fragment = new StatusFragment(); break;
                    }
                    changeFragment(fragment);
                    return true;
                }
            };

    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }

}
