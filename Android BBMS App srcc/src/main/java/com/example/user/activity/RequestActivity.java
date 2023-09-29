package com.example.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.R;
import com.example.user.entity.Request;
import com.example.user.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    Spinner spinnerOptions;
    EditText editUnit;
    Button btnSend;

    String[] bloodTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        spinnerOptions = findViewById(R.id.spinnerOptions);
        editUnit = findViewById(R.id.editUnit);
        btnSend = findViewById(R.id.btnSend);

        // Define the array of blood types
        bloodTypes = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

        // Create an ArrayAdapter with the string array and default layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodTypes);

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        spinnerOptions.setAdapter(adapter);

        // Set the onClick listener for the button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blood_group = spinnerOptions.getSelectedItem().toString();
                String unit = editUnit.getText().toString();

                // Validate the units input
                if (unit.isEmpty()) {
                    Toast.makeText(RequestActivity.this, "Please enter the number of units", Toast.LENGTH_SHORT).show();
                    return;
                }

                int units;
                try {
                    units = Integer.parseInt(unit);
                } catch (NumberFormatException e) {
                    Toast.makeText(RequestActivity.this, "Invalid number of units", Toast.LENGTH_SHORT).show();
                    return;
                }

                int user_id = getSharedPreferences("user", Context.MODE_PRIVATE).getInt("uid", 0);

                // Create a Request object with the user ID, blood group, and units
                Request request = new Request(user_id, blood_group, units);

                Log.e("RequestActivity", "Request Object: " + request.toString());

                // Make an API call to send the request
                RetrofitClient.getInstance().getApi().request(request).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject body = response.body();
                            if (body != null && body.getAsJsonObject().get("status").getAsString().equals("success")) {
                                Toast.makeText(RequestActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                                clearFields();
                                finish();
                            } else {
                                Toast.makeText(RequestActivity.this, "Request failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RequestActivity.this, "Request failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(RequestActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void clearFields() {
        spinnerOptions.setSelection(0);
        editUnit.setText("");
    }
}
