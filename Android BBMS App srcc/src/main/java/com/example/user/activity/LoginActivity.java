package com.example.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.R;
import com.example.user.entity.User;
import com.example.user.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editUserName, editPassword;
    CheckBox checkBoxRememberMe;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN_STATUS = "login_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize EditText and CheckBox fields
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        // Access the shared preferences
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
    }

    public void login(View view) {
        // Get the entered username and password
        String userName = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Validate the input fields
        if (isInputValid(userName, password)) {
            // Create a User object with the entered username and password
            User user = new User();
            user.setUserUserName(userName);
            user.setUserPassword(password);

            // Store the login status
            boolean rememberMe = checkBoxRememberMe.isChecked();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_LOGIN_STATUS, rememberMe);
            editor.apply();

            // Make an API call to login the user
            RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject body = response.body();

                        if (body != null) {
                            String status = body.get("status").getAsString();
                            if (status.equals("success")) {
                                JsonArray dataArray = body.getAsJsonArray("data");
                                if (dataArray.size() > 0) {
                                    JsonObject userData = dataArray.get(0).getAsJsonObject();
                                    int userId = userData.get("user_id").getAsInt();
                                    sharedPreferences.edit().putInt("uid", userId).apply();

                                    // Clear the EditText fields
                                    editUserName.setText("");
                                    editPassword.setText("");

                                    // Start the MainActivity
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                    return;
                                } else {
                                    // If the "data" array is empty
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If the response status is not "success"
                                Toast.makeText(LoginActivity.this, "Login failed: " + status, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If the response body is null
                            Toast.makeText(LoginActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If the response is not successful (e.g., HTTP error)
                        Toast.makeText(LoginActivity.this, "Invalid Credentials ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Handle API call failure
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void register(View view) {
        // Start the RegisterActivity
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private boolean isInputValid(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            editUserName.setError("Please enter a username");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Please enter a password");
            return false;
        }

        return true;
    }
}
