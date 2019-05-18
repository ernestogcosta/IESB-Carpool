package br.iesb.iesbcarpool.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.iesb.iesbcarpool.R;

public class Cadastro extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText txtNome = findViewById(R.id.txtNome);
        final EditText txtSobrenome = findViewById(R.id.txtSobrenome);
        final EditText txtEmail = findViewById(R.id.txtEmail);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        Button btnProximo = findViewById(R.id.btnProximo);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNome.getText().toString().matches("") || txtSobrenome.getText().toString().matches("") ||
                        txtEmail.getText().toString().matches("") || txtPassword.getText().toString().matches("")){
                    Toast.makeText(Cadastro.this,"Preencha todos os campos.!", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), CadastrarFoto.class);
                    startActivity(intent);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
