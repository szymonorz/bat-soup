package com.simon.android.batsoup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.simon.android.batsoup.R;
import com.simon.android.batsoup.activity.MainActivity;
import com.simon.android.batsoup.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment {

    private User currentUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_fragment, container, false);
        Call<User> getUser = MainActivity.apiInterface.getUser(MainActivity.token);

        TextView username = view.findViewById(R.id.username);
        TextView status = view.findViewById(R.id.status);
        TextView coor = view.findViewById(R.id.coor);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    return;
                }
                currentUser = response.body();
                username.setText(currentUser.getLogin());
                status.setText(currentUser.getStatus());
                String coorString = String.format("Current location x: %7.5f y: %7.5f", currentUser.getLocation().getX(),currentUser.getLocation().getY());
                coor.setText(coorString);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("UserGet", "Fail");
            }
        });





        return view;
    }


}
