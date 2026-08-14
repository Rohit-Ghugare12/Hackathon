package com.example.woodland;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeScannerActivity extends AppCompatActivity {

    AppCompatButton btnQRCode;
    TextView tvQRCodeResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);

        btnQRCode = findViewById(R.id.btnQRCode);
        tvQRCodeResult = findViewById(R.id.tvQRCodeResult);

        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator intentIntegrator = new IntentIntegrator(QRCodeScannerActivity.this);
                intentIntegrator.setPrompt("Scan QR Code");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setTorchEnabled(true);
                intentIntegrator.initiateScan();


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult != null && intentResult.getContents() != null)
        {
            tvQRCodeResult.setText(intentResult.getContents());
            btnQRCode.setVisibility(View.GONE);


        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}