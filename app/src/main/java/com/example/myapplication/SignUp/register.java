package com.example.myapplication.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SignIn.login;
import com.example.myapplication.api.ApiEndpoints;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {

    private AppCompatButton btnRegister;
    private TextView btnSignIn;
    private EditText txtUsername, txtPassword, txtLastname, txtFirstname, txtAge, txtAddress, txtCnumber, txtEmail;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String formattedText = getString(R.string.text_with_colors1);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(formattedText);

        ForegroundColorSpan grayColorSpan = new ForegroundColorSpan(Color.parseColor("#818183"));
        spannableStringBuilder.setSpan(grayColorSpan, 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan greenColorSpan = new ForegroundColorSpan(Color.parseColor("#1C360D"));
        spannableStringBuilder.setSpan(greenColorSpan, 25, formattedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView myTextView = findViewById(R.id.textView6);
        myTextView.setText(spannableStringBuilder);

        txtFirstname = findViewById(R.id.Firstname);
        txtLastname = findViewById(R.id.Lastname);
        txtAge = findViewById(R.id.Age);
        txtAddress = findViewById(R.id.Address);
        txtCnumber = findViewById(R.id.Cellphonenumber);
        txtEmail = findViewById(R.id.Emailaddress);
        txtUsername = findViewById(R.id.Username);
        txtPassword = findViewById(R.id.Password);
        txtLastname = findViewById(R.id.Lastname);
        btnRegister = findViewById(R.id.Register);
        btnSignIn = findViewById(R.id.textView6);
        progress = findViewById(R.id.progressbar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                loading(true);
                //Check valid email and password
                String getFirstname = txtFirstname.getText().toString().trim();
                String getLastname = txtLastname.getText().toString().trim();
                String getAge = txtAge.getText().toString().trim();
                String getAddress = txtAddress.getText().toString().trim();
                String getCnumber = txtCnumber.getText().toString().trim();
                String getEmail = txtEmail.getText().toString().trim();
                String getUsername = txtUsername.getText().toString().trim();
                String getPassword = txtPassword.getText().toString().trim();

                checkCredentials(getFirstname, getLastname, getAge, getAddress, getCnumber, getEmail, getUsername, getPassword);
        }
    });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }
    private void checkCredentials(String Firstname, String Lastname, String Age, String Address, String Cnumber, String Email, String Username, String Password){
        if(Firstname.isEmpty()){
            Toast.makeText(register.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
        } else if(Lastname.isEmpty()){
            Toast.makeText(register.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
        } else if(Age.isEmpty()){
            Toast.makeText(register.this, "Please enter your age", Toast.LENGTH_SHORT).show();
        } else if(Address.isEmpty()) {
            Toast.makeText(register.this, "Please enter your address", Toast.LENGTH_SHORT).show();
        } else if(Cnumber.isEmpty()) {
            Toast.makeText(register.this, "Please enter your cellphone number", Toast.LENGTH_SHORT).show();
        } else if(Email.isEmpty()) {
            Toast.makeText(register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (Username.isEmpty()) {
            Toast.makeText(register.this, "Please enter your username", Toast.LENGTH_SHORT).show();
        } else if (Password.isEmpty()) {
            Toast.makeText(register.this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else {
            createAccount(Firstname, Lastname, Age, Address, Cnumber, Email, Username, Password);
        }
    }
    private void createAccount(String Firstname, String Lastname, String Age, String Address, String Cnumber, String Email, String Username, String Password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-east-1.aws.data.mongodb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints apiEndpoints = retrofit.create(ApiEndpoints.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fname", Firstname);
        jsonObject.addProperty("lname", Lastname);
        jsonObject.addProperty("age", Age);
        jsonObject.addProperty("address", Address);
        jsonObject.addProperty("cnumber", Cnumber);
        jsonObject.addProperty("email", Email);
        jsonObject.addProperty("username", Username);
        jsonObject.addProperty("password", Password);
        jsonObject.addProperty("chocolatestick", "none");
        jsonObject.addProperty("chocolateprice", "none");
        jsonObject.addProperty("milkstick", "none");
        jsonObject.addProperty("milkprice", "none");
        jsonObject.addProperty("pineappleprice", "none");
        jsonObject.addProperty("pineapplestick", "none");
        jsonObject.addProperty("sweetcornprice", "none");
        jsonObject.addProperty("sweetcornstick", "none");
        jsonObject.addProperty("watermelonprice", "none");
        jsonObject.addProperty("watermelonstick", "none");
        jsonObject.addProperty("chocolatecart", "none");
        jsonObject.addProperty("milkcart", "none");
        jsonObject.addProperty("pineapplecart", "none");
        jsonObject.addProperty("sweetcorncart", "none");
        jsonObject.addProperty("watermeloncart", "none");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<CreateAccount> call = apiEndpoints.CreateAccount(requestBody);
        call.enqueue(new Callback<CreateAccount>() {
            @Override
            public void onResponse(Call<CreateAccount> call, Response<CreateAccount> response) {
                if (response.isSuccessful()) {
                    loading(false);
                    Toast.makeText(register.this, "Account Created Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(register.this, login.class));
                    overridePendingTransition( 0, R.anim.splash_fade_out);

                } else {
                    loading(false);
                    Toast.makeText(register.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }

                public void onFailure(Call<CreateAccount> call, Throwable t) {
                    Toast.makeText(register.this, "Account Creation failed.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    private void loading(Boolean isLoading) {
        if (isLoading) {
            btnRegister.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }
    }
}