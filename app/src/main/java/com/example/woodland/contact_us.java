package com.example.woodland;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class contact_us extends AppCompatActivity {


    EditText etSMSMobileNo, etSMSMessage,etEmailRecipient,etEmilSubject,etEmailBody;

    AppCompatButton btnSendSms,btnSndEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        etSMSMobileNo=findViewById(R.id.etdContactUsMobileNo);
        etSMSMessage = findViewById(R.id.etContactUsMessage);
        etEmailRecipient =findViewById(R.id.etContactUsEmail);
        etEmilSubject = findViewById(R.id.etContactUsSubject);
        etEmailBody = findViewById(R.id.etContactUsEmailBody);
        btnSendSms = findViewById(R.id.btnSendSms);
        btnSndEmail = findViewById(R.id.btnSendEmail);


        if (ContextCompat.checkSelfPermission(contact_us.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(contact_us.this,
                    new String[]{Manifest.permission.SEND_SMS},999);


        }

        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strMobileNo = etSMSMobileNo.getText().toString();
                    String strMessage = etSMSMessage.getText().toString();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(strMobileNo,null,strMessage,null,null);
                    Toast.makeText(contact_us.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    etSMSMobileNo.setText("");
                    etSMSMessage.setText("");
                } catch (Exception e) {
                    Toast.makeText(contact_us.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }


        });

        btnSndEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRecipient = etEmailRecipient.getText().toString();
                String strSubject = etEmilSubject.getText().toString();
                String strBody = etEmailBody.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{strRecipient});
                intent.putExtra(Intent.EXTRA_SUBJECT,strSubject);
                intent.putExtra(Intent.EXTRA_TEXT,strBody);

                try {
                    startActivity(Intent.createChooser(intent,"Choose an E-mail App"));

                } catch (Exception e) {
                    Toast.makeText(contact_us.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}