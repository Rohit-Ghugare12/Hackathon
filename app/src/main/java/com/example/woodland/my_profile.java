package com.example.woodland;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woodland.COMMON.Urls;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class my_profile extends AppCompatActivity {

    TextInputEditText etProfileName,etProfileUsername,etProfilePhoneNum,etProfileEmail,etProfileId;
    Button buttonDelAccount;
    ImageView ivQrCode;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        etProfileName = findViewById(R.id.etProfileName);
        etProfileUsername = findViewById(R.id.etProfileUsername);
        etProfilePhoneNum = findViewById(R.id.etProfilePhoneNum);
        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfileId = findViewById(R.id.etProfileId);
        buttonDelAccount = findViewById(R.id.buttonDelAccount);
        ivQrCode=findViewById(R.id.ivQrCode);

       // preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = PreferenceManager.getDefaultSharedPreferences(my_profile.this);
        editor = preferences.edit();

        etProfileUsername.setText(preferences.getString("username", ""));


    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog= new ProgressDialog(my_profile.this);
        progressDialog.setTitle("My Profile");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        getMyDetails();

    }

    private void getMyDetails()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",preferences.getString("username",""));

        client.post(Urls.getMyDetailsAPI,params,new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {


                    JSONArray jsonArray = response.getJSONArray("getMyDetails");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String strId = jsonObject.getString("id");
                        String strName = jsonObject.getString("name");
                        String strMobileno = jsonObject.getString("mobileno");
                        String strEmailid = jsonObject.getString("emailid");
                        String strUsername = jsonObject.getString("username");

                        etProfileName.setText(strName);
                        etProfilePhoneNum.setText(strMobileno);
                        etProfileEmail.setText(strEmailid);
                        etProfileId.setText(strId);
                        etProfileUsername.setText(strUsername);

                        buttonDelAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                alertDialog();
                                progressDialog= new ProgressDialog(my_profile.this);
                                progressDialog.setTitle("Delete Account");
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCanceledOnTouchOutside(true);
                                progressDialog.show();

                                deleteAccount();
                            }

                            private void alertDialog()
                            {
                                AlertDialog.Builder ad = new AlertDialog.Builder(my_profile.this);
                                ad.setTitle("Delete Account");
                                ad.setMessage("Are you want to Delete Account ?");
                                ad.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                ad.setNegativeButton("LOGOUT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putBoolean("isLogin",false).commit() ;
                                        Intent i = new Intent(my_profile.this, Login_activity.class);
                                        startActivity(i);
                                        finishAffinity();

                                    }
                                }).show().create();
                            }


                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap;

                            {
                                try {
                                    bitmap = barcodeEncoder.encodeBitmap(preferences.getString("username",""),
                                            BarcodeFormat.QR_CODE,
                                            100,100);
                                    ivQrCode.setImageBitmap(bitmap);


                                } catch (WriterException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            private void deleteAccount()
                            {
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();

                                params.put("username",preferences.getString("username",""));

                                client.post(Urls.deleteAccountAPI,
                                        params,
                                        new JsonHttpResponseHandler()
                                        {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                super.onSuccess(statusCode, headers, response);

                                                try {
                                                    String strResponse= response.getString("success");
                                                    String strMessage= response.getString("message");

                                                    if (strResponse.equals("1"))
                                                    {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(my_profile.this, strMessage, Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(my_profile.this,Login_activity.class);
                                                        editor.putBoolean("isLogin",false).commit();
                                                        startActivity(i);
                                                        finish();

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(my_profile.this, strMessage, Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                super.onFailure(statusCode, headers, throwable, errorResponse);

                                                Toast.makeText(my_profile.this, "Server Problem", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);


            }
        });

    }
}