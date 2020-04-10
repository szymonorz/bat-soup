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
        changeFragment(new SearchFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);



    }

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.radar: changeFragment(new SearchFragment()); break;
                        case R.id.status: changeFragment(new StatusFragment()); break;
                    }
                }
            };

    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, fragment).commit();
    }

}
