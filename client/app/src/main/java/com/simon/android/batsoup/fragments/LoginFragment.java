package com.simon.android.batsoup.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.simon.android.batsoup.R;
import com.simon.android.batsoup.activity.LoginActivity;
import com.simon.android.batsoup.activity.MainActivity;
import com.simon.android.batsoup.model.JwtResponse;
import com.simon.android.batsoup.model.LoginForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        Button button = view.findViewById(R.id.sign_email);
        EditText email = view.findViewById(R.id.email_login);
        EditText password = view.findViewById(R.id.password_login);
        TextView register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new RegisterFragment()).commit();
            }
        });
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
        return view;
    }

    private void signIn(String email, String password)
    {
        LoginForm loginForm = new LoginForm(email, password);
        Call<JwtResponse> token = LoginActivity.apiInterface.loginUser(loginForm);
        token.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(!response.isSuccessful())
                {
                    Log.d("LoginUser", "failure");
                    return;
                }
                Log.d("LoginUser", response.body().getToken());
                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("Token",response.body().getToken());
                startActivity(i);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Log.d("LoginUser", "Failure");
                t.printStackTrace();

            }
        });
    }
}
