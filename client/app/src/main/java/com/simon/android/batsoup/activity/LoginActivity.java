package com.simon.android.batsoup.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.simon.android.batsoup.R;
import com.simon.android.batsoup.interfaces.ApiInterface;
import com.simon.android.batsoup.model.JwtResponse;
import com.simon.android.batsoup.model.LoginForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
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

        Button button = findViewById(R.id.signin);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Log.d("signin", "signin");
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                signIn(emailString, passwordString);
            }
        });

    }

    private void signIn(String email, String password)
    {
        LoginForm loginForm = new LoginForm(email, password);
        Call<JwtResponse> token = apiInterface.loginUser(loginForm);
        token.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(!response.isSuccessful())
                {
                    Log.d("LoginUser", "failure");
                    return;
                }
                Log.d("LoginUser", response.body().getToken());
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Log.d("LoginUser", "Failure");
                t.printStackTrace();

            }
        });
    }
}
