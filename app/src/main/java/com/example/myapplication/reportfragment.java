package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.SignUp.CreateAccount;
import com.example.myapplication.SignUp.register;
import com.example.myapplication.api.ApiEndpoints;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class reportfragment extends Fragment {

    private EditText report, message, chocolate, milk, pineapple, sweetcorn, watermelon;
    private AppCompatButton send;
    private String userId, username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reportfragment, container, false);

        report = view.findViewById(R.id.Report);
        message = view.findViewById(R.id.Message);
        chocolate = view.findViewById(R.id.ChocolateStick);
        milk = view.findViewById(R.id.MilkStick);
        pineapple = view.findViewById(R.id.PineappleStick);
        sweetcorn = view.findViewById(R.id.SweetcornStick);
        watermelon = view.findViewById(R.id.WatermelonStick);
        send = view.findViewById(R.id.Send);

        EditText editTextDate = view.findViewById(R.id.Datetoday);

        Date currentDate = Calendar.getInstance().getTime();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        editTextDate.setText(formattedDate);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getReport = report.getText().toString().trim();
                String getMessage = message.getText().toString().trim();
                String getChocolate = chocolate.getText().toString().trim();
                String getMilk = milk.getText().toString().trim();
                String getPineapple = pineapple.getText().toString().trim();
                String getSweetcorn = sweetcorn.getText().toString().trim();
                String getWatermelon = watermelon.getText().toString().trim();
                String getDate = editTextDate.getText().toString().trim();
                checkCredentials(getReport, getMessage, getChocolate, getMilk, getPineapple, getSweetcorn, getWatermelon, getDate);
            }
        });
        getUserDataFromSharedPreferences();
                return view;

        }
    private void checkCredentials(String Report, String Message, String Chocolate, String Milk, String Pineapple, String Sweetcorn, String Watermelon, String Date) {
        if (Report.isEmpty()) {
            Toast.makeText(getContext(), "Enter your Report", Toast.LENGTH_SHORT).show();
        } else if (Message.isEmpty()) {
            Toast.makeText(getContext(), "Enter your Message", Toast.LENGTH_SHORT).show();
        } else if (Chocolate.isEmpty()) {
            Toast.makeText(getContext(), "Enter a number for Chocolate", Toast.LENGTH_SHORT).show();

        } else if (Milk.isEmpty()) {
            Toast.makeText(getContext(), "Enter a number for Milk", Toast.LENGTH_SHORT).show();

        } else if (Pineapple.isEmpty()) {
            Toast.makeText(getContext(), "Enter a number for Pineapple", Toast.LENGTH_SHORT).show();

        } else if (Sweetcorn.isEmpty()) {
            Toast.makeText(getContext(), "Enter a number for Sweetcorn", Toast.LENGTH_SHORT).show();

        } else if (Watermelon.isEmpty()) {
            Toast.makeText(getContext(), "Enter a number for Watermelon", Toast.LENGTH_SHORT).show();
        } else {
            String getUserId = userId;
            String getUsername = username;
            SubmitReport(getUserId, getUsername, Date, Report, Message, Chocolate, Milk, Pineapple, Sweetcorn, Watermelon);

        }
    }

    private void SubmitReport(String userId, String username, String Date, String Report, String Message, String Chocolate, String Milk, String Pineapple, String Sweetcorn, String Watermelon){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-east-1.aws.data.mongodb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints apiEndpoints = retrofit.create(ApiEndpoints.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", userId);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("date", Date);
        jsonObject.addProperty("report", Report);
        jsonObject.addProperty("message", Message);
        jsonObject.addProperty("chocolatestick", Chocolate);
        jsonObject.addProperty("milkstick", Milk);
        jsonObject.addProperty("pineapplestick", Pineapple);
        jsonObject.addProperty("sweetcornstick", Sweetcorn);
        jsonObject.addProperty("watermelonstick", Watermelon);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<CreateReport> call = apiEndpoints.CreateReport(requestBody);
        call.enqueue(new Callback<CreateReport>() {
            @Override
            public void onResponse(Call<CreateReport> call, Response<CreateReport> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Report Submitted Successfully!.", Toast.LENGTH_SHORT).show();
                    ClearText();
        } else {
                    Toast.makeText(getContext(), "Report Submission Failed.", Toast.LENGTH_SHORT).show();


                }
            }
            public void onFailure(Call<CreateReport> call, Throwable t) {
                Toast.makeText(getContext(), "Report Submission Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String getUserId = sharedPreferences.getString("userId", "");
        String getUsername = sharedPreferences.getString("username", "");
        if (!getUsername.isEmpty() && !getUserId.isEmpty()) {
            userId = getUserId;
            username = getUsername;
        } else {

        }
    }
    private void ClearText() {
        report.setText("");
        message.setText("");
        chocolate.setText("");
        milk.setText("");
        pineapple.setText("");
        sweetcorn.setText("");
        watermelon.setText("");
    }
}
