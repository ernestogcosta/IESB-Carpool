package br.iesb.iesbcarpool.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.iesb.iesbcarpool.R;

public class MainActivity extends AppCompatActivity {
    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText email = findViewById(R.id.txtEmail);
        final EditText senha = findViewById(R.id.txtSenha);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCadastro = findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),senha.getText().toString(),MainActivity.this);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });

    }

    private void login(String email, String senha, final Activity activity){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        Task<AuthResult> task = auth.signInWithEmailAndPassword(email,senha);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(activity,"Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity,UsuarioCadastrado.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(activity,"Email ou senha errados.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
