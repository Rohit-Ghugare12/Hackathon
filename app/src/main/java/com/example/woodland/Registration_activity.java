package com.example.woodland;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woodland.COMMON.Urls;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registration_activity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextInputEditText etRegistrationName,etRegistrationPassword,etRegistrationConfPass,etMobileno,etEmail,etRegistrationUsername;
    Button btnRegistration;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etRegistrationName = findViewById(R.id.etRegisrationName);
        etMobileno = findViewById(R.id.etMobileNo);
        etRegistrationPassword = findViewById(R.id.etRegistrationPassword);
        etRegistrationConfPass = findViewById(R.id.etRegistrationConformPass);
        etEmail = findViewById(R.id.etEmail);
        etRegistrationUsername = findViewById(R.id.etRegistrationUsername);
        btnRegistration = findViewById(R.id.btnRegistration);

        //preferences=getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = PreferenceManager.getDefaultSharedPreferences(Registration_activity.this);
        editor=preferences.edit();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etRegistrationName.getText().toString().isEmpty()){
                    etRegistrationName.setError("Please Enter Your Full name");
                } else if (etRegistrationUsername.getText().toString().isEmpty()) {
                    etRegistrationUsername.setError("Please Enter Your Username");
                }else if (etMobileno.getText().toString().length()!=10) {
                    etMobileno.setError("Enter valid mobile no...");
                }else if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Please Enter Your E-mail");
                }else if (!etEmail.getText().toString().contains("@") && !etEmail.getText().toString().contains(".com")) {
                    etEmail.setError("Please Enter Valid E-mail id");
                } else if (etRegistrationPassword.getText().toString().isEmpty()) {
                    etRegistrationPassword.setError("Please Enter Your Password");
                }else if (!etRegistrationConfPass.getText().toString().equals(etRegistrationPassword.getText().toString())){
                    etRegistrationConfPass.setError("Password & Conform Npt Matched ");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[A-Z].*")) {
                    etRegistrationPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                } else if (!etRegistrationPassword.getText().toString().matches(".*[0-9].*")) {
                    etRegistrationPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                }else if (!etRegistrationPassword.getText().toString().matches(".*[@,#,$,%,!,*,&,~,%].*")) {
                    etRegistrationPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                }else if (!etRegistrationPassword.getText().toString().matches(".*[a-z].*")) {
                    etRegistrationPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                }else{
                    editor.putString("name", etRegistrationName.getText().toString().trim());
                    editor.putString("username", etRegistrationUsername.getText().toString().trim());
                    editor.putString("mobile", etMobileno.getText().toString().trim());
                    editor.putString("email", etEmail.getText().toString().trim());
                    editor.putString("password", etRegistrationPassword.getText().toString().trim());
                    editor.apply();
//
                    progressDialog =new ProgressDialog(Registration_activity.this);
                    progressDialog.setTitle("Registration");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();


                    registerUser();
                }
            }
        });
    }

    private void registerUser()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",etRegistrationName.getText().toString());
        params.put("username",etRegistrationUsername.getText().toString());
        params.put("password",etRegistrationPassword.getText().toString());
        params.put("emailid",etEmail.getText().toString());
        params.put("mobileno",etMobileno.getText().toString());

        client.post(Urls.registerUserAPI,params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try
                        {

                            String status = response.getString("success");

                            if (status.equals("1"))
                            {
                                Toast.makeText(
                            Registration_activity.this,
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Registration_activity.this, Home_Activity.class);
                            startActivity(i);
                           finish();
                            }
                            else
                            {
                                Toast.makeText(Registration_activity.this,
                                        ""+response.getString("message"),
                                         Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {

                            throw new RuntimeException(e);
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        progressDialog.dismiss();

                        String message = (throwable != null)
                                ? throwable.getMessage()
                                : "Unknown error";

                        Toast.makeText(Registration_activity.this,
                                "Error: " + message,
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    @SuppressLint({"MissingSuperCall", "GestureBackNavigation"})
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Registration_activity.this, MainActivity.class);
        startActivity(i);
    }
}