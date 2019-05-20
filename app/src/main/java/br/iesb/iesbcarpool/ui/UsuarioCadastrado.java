package br.iesb.iesbcarpool.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.iesb.iesbcarpool.R;

public class UsuarioCadastrado extends AppCompatActivity {

    boolean procuraCarona = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastrado);

        Button btnQuero = findViewById(R.id.btnQuero);
        Button btnOfereco = findViewById(R.id.btnOfereco);

        btnQuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procuraCarona = true;
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
            }
        });

        btnOfereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procuraCarona = false;
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
