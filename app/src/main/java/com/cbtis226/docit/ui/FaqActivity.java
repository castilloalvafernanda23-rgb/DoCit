package com.cbtis226.docit.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cbtis226.docit.R;

public class FaqActivity extends AppCompatActivity {

    String[][] faqs = {
            {"¿Cómo agendo una cita?", "Entra a Buscar doctor, escoge un médico, dale a Agendar cita, selecciona fecha y hora."},
            {"¿Puedo cancelar mi cita?", "Sí, desde el historial de citas puedes cancelar antes de la fecha programada."},
            {"¿Cuánto cuesta usar DoCit?", "Crear cuenta y agendar es gratis. Solo pagas la consulta directo al doctor."},
            {"¿Cómo cambio mi foto de perfil?", "Ve a tu perfil, dale a Editar perfil y toca tu foto pa cambiarla."},
            {"¿Es seguro?", "Tus datos están protegidos y solo el doctor que escojas verá tu información clínica."}
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_faq);
        findViewById(R.id.btnX).setOnClickListener(v -> finish());
        LinearLayout cont = findViewById(R.id.contFaq);

        for(String[] f : faqs){
            View card = pintarCard(f[0], f[1]);
            cont.addView(card);
        }
    }

    View pintarCard(String preg, String resp){
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.bg_redondeado);
        int pad = (int)(14 * getResources().getDisplayMetrics().density);
        ll.setPadding(pad,pad,pad,pad);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, pad);
        ll.setLayoutParams(lp);

        TextView t1 = new TextView(this);
        t1.setText(preg);
        t1.setTextSize(15);
        t1.setTypeface(null, Typeface.BOLD);
        t1.setTextColor(getResources().getColor(R.color.rojoDocit));
        ll.addView(t1);

        TextView t2 = new TextView(this);
        t2.setText(resp);
        t2.setTextColor(getResources().getColor(R.color.negroSuave));
        t2.setPadding(0, pad/2, 0, 0);
        ll.addView(t2);

        return ll;
    }
}