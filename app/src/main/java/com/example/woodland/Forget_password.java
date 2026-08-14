package com.example.woodland;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Forget_password extends AppCompatActivity {

    TextInputEditText etForgetUsrnameName,etForgetNewPassword,etForgetConffPassword;
    Button btnForgetPassword;
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etForgetUsrnameName=findViewById(R.id.etForgetUsrnameName);
        etForgetNewPassword=findViewById(R.id.etForgetNewPassword);
        etForgetConffPassword=findViewById(R.id.etForgetConffPassword);
        btnForgetPassword=findViewById(R.id.btnForgetPassword);

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etForgetUsrnameName.getText().toString().isEmpty())
                {
                    etForgetUsrnameName.setError("Please Enter Username");
                }
                else if (etForgetUsrnameName.getText().toString().length()<8)
                {
                    etForgetUsrnameName.setError("Username must be greater than 8 character");
                }
                else if (etForgetNewPassword.getText().toString().trim().isEmpty())
                {
                    etForgetNewPassword.setError("Please Enter Password");
                }
                else if (etForgetNewPassword.getText().toString().trim().length()<8)
                {
                    etForgetNewPassword.setError("Password must be greater than 8 character");
                }
                else if (!etForgetConffPassword.getText().toString().trim()
                        .equals(etForgetNewPassword.getText().toString().trim()))
                {
                    etForgetConffPassword.setError("Password does not match");
                }
                else
                {
                    progressDialog=new ProgressDialog(Forget_password.this);
                    progressDialog.setTitle("Forget Password");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    forgetPassword();
                }


            }

            private void forgetPassword()
            {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("username",etForgetUsrnameName.getText().toString());
                params.put("newpassword",etForgetNewPassword.getText().toString());

                client.post(Urls.ForgetPasswordAPI,params,new JsonHttpResponseHandler()

                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                            {
                                super.onSuccess(statusCode, headers, response);

                                try
                                {
                                 String status = response.getString("success");
                                 String message = response.getString("message");

                                 if (status.equals("1"))
                                 {
                                     progressDialog.dismiss();
                                     Toast.makeText(Forget_password.this,"Password Changed",Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(Forget_password.this, Home_Activity.class);
                                     startActivity(intent);
                                     finish();
                                 }
                                 else
                                 {
                                     Toast.makeText(Forget_password.this,message,Toast.LENGTH_SHORT).show();
                                 }
                                }
                                catch (JSONException e)
                                {
                                    throw new RuntimeException();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
                            {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(Forget_password.this,"Server problem",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }
}