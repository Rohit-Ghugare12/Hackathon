package com.example.woodland;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView ivSolashLogo;
    TextView tvSplashTitle,tvSubTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivSolashLogo=findViewById(R.id.ivSplashLogo);
        tvSplashTitle=findViewById(R.id.tvSplashTitle);
        tvSubTitle=findViewById(R.id.tvSubTitle);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.translate);
        ivSolashLogo.startAnimation(animation);
        Handler h=new Handler(getMainLooper ());
        h.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i = new Intent(MainActivity.this,Login_activity.class);
                startActivity(i);
                finish();

            }
        }, 2000);

    }
}