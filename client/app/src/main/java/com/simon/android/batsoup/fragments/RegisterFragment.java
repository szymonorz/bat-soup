package com.simon.android.batsoup.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.simon.android.batsoup.R;
import com.simon.android.batsoup.activity.LoginActivity;
import com.simon.android.batsoup.activity.MainActivity;
import com.simon.android.batsoup.model.JwtResponse;
import com.simon.android.batsoup.model.RegisterForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        EditText email = view.findViewById(R.id.email_register);
        EditText password = view.findViewById(R.id.password_register_1);
        EditText login = view.findViewById(R.id.login_register);
        RegisterForm registerForm = new RegisterForm(email.getText().toString(), password.getText().toString(), login.getText().toString());
        Call<JwtResponse> registerUser= LoginActivity.apiInterface.registerUser(registerForm);
        registerUser.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(!response.isSuccessful())
                {
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

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
