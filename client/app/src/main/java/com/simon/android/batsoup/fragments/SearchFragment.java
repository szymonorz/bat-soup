package com.simon.android.batsoup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.simon.android.batsoup.CustomAdapter;
import com.simon.android.batsoup.R;
import com.simon.android.batsoup.activity.MainActivity;
import com.simon.android.batsoup.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private CustomAdapter adapter;
    private ArrayList<User> globalUsers = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        ListView listView = view.findViewById(R.id.list);

        adapter = new CustomAdapter(globalUsers,getContext());
        listView.setAdapter(adapter);

        Call<List<User>> getUsers = MainActivity.apiInterface.findUsers(MainActivity.token);
        getUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful())
                {
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

        return view;
    }
}
