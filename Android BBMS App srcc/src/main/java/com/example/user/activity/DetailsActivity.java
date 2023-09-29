package com.example.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.R;
import com.example.user.entity.User;
import com.example.user.utils.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    TextView textFname, textAge, textGender, textBloodGroup, textPhone, textMail, textAddress;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize the views
        textFname = findViewById(R.id.textFname);
        textAge = findViewById(R.id.textAge);
        textGender = findViewById(R.id.textGender);
        textBloodGroup = findViewById(R.id.textBloodGroup);
        textPhone = findViewById(R.id.textPhone);
        textMail = findViewById(R.id.textMail);
        textAddress = findViewById(R.id.textAddress);

        // Fetch user details
        getUserById();
    }

    private void getUserById() {
        // Retrieve user ID from SharedPreferences
        int id = getSharedPreferences("user", Context.MODE_PRIVATE).getInt("uid", 0);

        // Make API call to retrieve user details
        RetrofitClient.getInstance().getApi().getUserById(id).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseObject = response.body();
                    if (responseObject != null) {
                        String status = responseObject.get("status").getAsString();
                        if (status.equals("success")) {
                            JsonArray dataArray = responseObject.getAsJsonArray("data");
                            if (dataArray != null && dataArray.size() > 0) {
                                JsonObject userObject = dataArray.get(0).getAsJsonObject();
                                User user = new Gson().fromJson(userObject, User.class);
                                displayUserDetails(user);
                            } else {
                                displayError("No user data found");
                            }
                        } else {
                            String message = responseObject.get("message").getAsString();
                            displayError(message);
                        }
                    } else {
                        displayError("Response body is null");
                    }
                } else {
                    displayError("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle API call failure
                displayError("Something went wrong");
            }
        });
    }

    private void displayUserDetails(User user) {
        textFname.setText("Name: " + user.getUserFName());
        textAge.setText("Age: " + user.getUserAge());
        textGender.setText("Gender: " + user.getUserGender());
        textBloodGroup.setText("Blood Group: " + user.getUserBloodGroup());
        textPhone.setText("Phone: " + user.getUserPhone());
        textMail.setText("Mail: " + user.getUserMail());
        textAddress.setText("Address: " + user.getUserPlace());
    }

    private void displayError(String errorMessage) {
        Toast.makeText(DetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        // Finish the activity and go back
        finish();
    }
}
