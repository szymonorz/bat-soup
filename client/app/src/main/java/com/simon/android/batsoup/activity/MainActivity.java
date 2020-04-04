package com.simon.android.batsoup.activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.android.batsoup.CustomAdapter;
import com.simon.android.batsoup.R;
import com.simon.android.batsoup.interfaces.ApiInterface;
import com.simon.android.batsoup.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private  ArrayList<User> globalUsers = new ArrayList<>();
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //getUsers();
        globalUsers.add(new User(new ObjectId(), 1,"marcin", "frajer", "email", new GeoJsonPoint(3,2)));
        adapter = new CustomAdapter(globalUsers,getApplicationContext());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.i("gson", gson.toJson(globalUsers));
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<List<User>> usersQuery = apiInterface.findUsers();

        usersQuery.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful())
                {
                    Log.d("EnqueueUser", "failure");
                    return;
                }
                globalUsers.clear();

                List<User> users = response.body();
                globalUsers.addAll(users);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("EnqueueUser", "failure");
            }
        });


    }

}
