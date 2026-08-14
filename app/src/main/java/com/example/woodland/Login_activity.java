package com.example.woodland;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class Login_activity extends AppCompatActivity {

    ImageView ivLoginLogo;
    TextView tvLogin,tvLoginForgetPassword;
    TextInputEditText etLoginUsername, etLoginPassword;
    Button btnLogin;
    CheckBox cbCheckBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(Login_activity.this);
        editor =preferences.edit();



        ivLoginLogo = findViewById(R.id.ivLoginLogo);
        tvLogin = findViewById(R.id.tvLogin);
        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbCheckBox=findViewById(R.id.cbCheckBox);
        tvLoginForgetPassword=findViewById(R.id.tvLoginForgetPassword);

        if (preferences.getBoolean("isLogin",false)){
            Intent i =new Intent(Login_activity.this, Home_Activity.class);
            startActivity(i);
            finish();

        }

        cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etLoginUsername.setError("Please Enter Your Username");
                } else if (username.length() <= 8) {
                    etLoginUsername.setError("Username Must Be Greater Than 8 Characters");
                } else if (password.isEmpty()) {
                    etLoginPassword.setError("Please Enter Your Password");
                } else if (password.length() <= 8) {
                    etLoginPassword.setError("Password Length Must Be Greater Than 8 Characters");
                } else if (!etLoginPassword.getText().toString().matches(".*[A-Z].*")) {
                    etLoginPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                } else if (!etLoginPassword.getText().toString().matches(".*[0-9].*")) {
                    etLoginPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                } else if (!etLoginPassword.getText().toString().matches(".*[@,#,$,%,!,*,&,~,%].*")) {
                    etLoginPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                } else if (!etLoginPassword.getText().toString().matches(".*[a-z].*")) {
                    etLoginPassword.setError("Password contains 1 Number,1 Symbol,1 Upper case latter,1 Lower case letter");
                }else {
                    progressDialog =new ProgressDialog(Login_activity.this);
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                            loginUser();

                }
            }


            private void loginUser()
            {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("username",etLoginUsername.getText().toString());
                params.put("password",etLoginPassword.getText().toString());

                client.post(Urls.loginUserAPI,params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try
                        {
                            String status=response.getString("success");
                            String message=response.getString("message");

                            if (status.equals("1"))
                            {
                                Intent i=new Intent(Login_activity.this,Home_Activity.class);
                                editor.putBoolean("isLogin",true).commit();
                                editor.putString("username",etLoginUsername.getText().toString()).commit();
                                startActivity(i);

                                finish();
                                Toast.makeText(
                                        Login_activity.this,
                                        message,
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(Login_activity.this,"Server Problem",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_activity.this, Forget_password.class);
                startActivity(i);
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_activity.this,Registration_activity.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(i);
                finish();
            }
        });
    }
}