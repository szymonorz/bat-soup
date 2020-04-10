package com.simon.android.batsoup.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.simon.android.batsoup.R;
import com.simon.android.batsoup.fragments.LoginFragment;
import com.simon.android.batsoup.interfaces.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle onSavedInstance)
    {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.login_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new LoginFragment()).commit();

    }


}
