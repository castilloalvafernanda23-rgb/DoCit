package com.cbtis226.docit.ui;

// ya me canse
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.R;

public class MisCitasActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_historial);
        // xdxdxdxdxdxd
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
    }
}