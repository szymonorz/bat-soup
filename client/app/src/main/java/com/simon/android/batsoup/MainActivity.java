package com.simon.android.batsoup;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.simon.android.batsoup.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private  ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getUsers();
        users.add(new User(new ObjectId(), 1,"marcin", "frajer", "email", new GeoJsonPoint(3,2)));
        adapter = new CustomAdapter(users,getApplicationContext());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.i("gson", gson.toJson(users));
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }


    private void getUsers()
    {
        new RetrieveJson().execute("http://10.0.2.2:8888/test");
    }


    class RetrieveJson extends AsyncTask<String, Void, String>
    {
        private Exception e;

        @Override
        protected String doInBackground(String... strings) {
                String url = strings[0];
                RestTemplate restTemplate = new RestTemplate();

                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String s = restTemplate.getForObject(url, String.class);
                Log.d("RESULT", "Executing");
                return s;
            }


        @Override
        protected void onPostExecute(String result)
        {
            Log.d("RESULT", "Post Execute");
            users.clear();
            ArrayList<User> arrayList = new Gson().fromJson(result, new TypeToken<ArrayList<User>>(){}.getType());
            for(User u: arrayList)
            {
                users.add(u);
            }
            Log.d("USER", result);
            adapter.notifyDataSetChanged();
        }
    }
}
