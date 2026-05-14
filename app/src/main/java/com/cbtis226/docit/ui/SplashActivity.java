package com.cbtis226.docit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.DoCitApp;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        new Handler().postDelayed(() -> {
            Intent i;
            if(DoCitApp.x().usuarioLogeado != null){
                i = new Intent(this, PrincipalActivity.class);
            } else {
                i = new Intent(this, LoginActivity.class);
            }
            startActivity(i);
            finish();
        }, 1800);
    }
}