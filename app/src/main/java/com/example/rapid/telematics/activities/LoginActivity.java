package com.example.rapid.telematics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapid.telematics.R;
import com.example.rapid.telematics.common.SharedPreferenceHandle;
import com.example.rapid.telematics.server.ServerConstants;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ServerConstants {
    private final String TAG = LoginActivity.class.getSimpleName();

    private EditText etUsername, etPassword;
    private Button btnSubmit;
    private SharedPreferenceHandle preferenceHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(this);
        // Initializing Views,
        initViews();
    }

    @Override
    void initViews() {
        etUsername = (EditText) findViewById(R.id.et_main_username);
        etPassword = (EditText) findViewById(R.id.et_main_password);
        btnSubmit = (Button) findViewById(R.id.btn_main_submit);

        etUsername.requestFocus();
        btnSubmit.setOnClickListener(this);
    }

    /**
     * Method to submit login details to the server
     * and then retrieve appropriate response.
     */
    private void processLoginDetails() {
        String username = "", password = "";

        if (etUsername.getText().toString().length() > 0)
            username = etUsername.getText().toString();
        else
            etUsername.setError("Enter username");

        if (etPassword.getText().toString().length() > 0)
            password = etPassword.getText().toString();
        else
            etPassword.setError("Enter password");

        if (username.length() > 0 && password.length() > 0) {
            // Sending login details to the server for retrieving appropriate details.
            utils.showProgressDialog(this);
            Call<JsonObject> call = utils.getBaseClassService(true, this).login(username, password, "password", "portal");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    utils.dismissDialog();
                    if (response.code() == 200) {
                        JsonObject loginResponse = response.body();

                        if (loginResponse.has(ACCESS_TOKEN_KEY)) {

                            Log.d(TAG, "onResponse: Token: " + loginResponse.get(ACCESS_TOKEN_KEY).getAsString());
                            preferenceHandle.putString(ACCESS_TOKEN_KEY, loginResponse.get(ACCESS_TOKEN_KEY).getAsString());
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            // Navigating to MainActivity.
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    } else {
                        if (response.message().length() > 0)
                            Log.d(TAG, "onResponse: Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    utils.dismissDialog();
                    if (t.getMessage().length() > 0)
                        Log.d(TAG, "onFailure: Message: " + t.getMessage());
                }
            });
        } else {
            utils.singleButtonAlertDialog(this, "Telematics", "Please fill the login details");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // Submit login details.
            case R.id.btn_main_submit:
                processLoginDetails();
                break;
        }
    }
}
