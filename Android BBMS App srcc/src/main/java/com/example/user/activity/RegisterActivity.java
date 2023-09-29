package com.example.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.R;
import com.example.user.entity.User;
import com.example.user.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText editFname, editEmail, editMobile, editPassword, editConfirmPassword, editAge, editGender, editBloodGroup, editPlace, editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize EditText fields
        editFname = findViewById(R.id.editFname);
        editEmail = findViewById(R.id.editEmail);
        editMobile = findViewById(R.id.editMobile);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        editAge = findViewById(R.id.editAge);
        editGender = findViewById(R.id.editGender);
        editBloodGroup = findViewById(R.id.editBloodGroup);
        editUserName = findViewById(R.id.editUserName);
        editPlace = findViewById(R.id.editPlace);
    }

    public void register(View view) {
        // Validate user input
        User user = validateUser();
        if (user != null) {
            // Call the registration API
            RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject body = response.body();
                        if (body != null) {
                            // Check registration status from the response
                            String status = body.getAsJsonPrimitive("status").getAsString();
                            if (status.equals("success")) {
                                Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                clearFields(); // Clear the input fields
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Invalid Response from Server", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private User validateUser() {
        // Get the input values from EditText fields
        String fname = editFname.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String mobile = editMobile.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String gender = editGender.getText().toString().trim();
        String bloodGroup = editBloodGroup.getText().toString().trim();
        String place = editPlace.getText().toString().trim();
        String userName = editUserName.getText().toString().trim();

        // Perform empty field validation
        if (fname.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || age.isEmpty() || gender.isEmpty() || bloodGroup.isEmpty() ||
                place.isEmpty() || userName.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Perform password match validation
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Perform additional validations or formatting as needed

        // Create a new User object and populate its fields
        User user = new User();
        user.setUserFName(fname);
        user.setUserPassword(password);
        user.setUserMail(email);
        user.setUserPhone(Long.parseLong(mobile));
        user.setUserAge(Integer.parseInt(age));
        user.setUserGender(gender);
        user.setUserBloodGroup(bloodGroup);
        user.setUserPlace(place);
        user.setUserUserName(userName);

        return user;
    }

    public void cancel(View view) {
        clearFields(); // Clear the input fields
        finish();
    }

    private void clearFields() {
        // Clear the input fields
        editFname.setText("");
        editEmail.setText("");
        editMobile.setText("");
        editPassword.setText("");
        editConfirmPassword.setText("");
        editAge.setText("");
        editGender.setText("");
        editBloodGroup.setText("");
        editUserName.setText("");
        editPlace.setText("");
    }
}
