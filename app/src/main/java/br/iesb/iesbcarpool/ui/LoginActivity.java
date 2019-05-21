package br.iesb.iesbcarpool.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import br.iesb.iesbcarpool.R;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private TextView txtCadastro;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();
        eventoClicks();


    }
    private void inicializaComponentes(){
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtCadastro = findViewById(R.id.txtCadastro);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void eventoClicks(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();
                if(validaCampos(email,senha)){
                    login(email,senha);
                }
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });
    }

    private void login(final String email,final String senha){
        final LoginActivity loginActivity = LoginActivity.this;
        final FirebaseAuth auth = Conexao.getFirebaseAuth();
        auth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    alert("Login efetuado com sucesso");
                    Intent i = new Intent(LoginActivity.this, UsuarioCadastrado.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("E-mail ou Senha inválidos");
                }
            }
        });

    }
    private boolean validaCampos(String email, String senha){
        if(email.length() == 0 || senha.length() == 0){
            alert("Todos os campos são obrigatórios");
            return false;
        }
        return true;
    }
    private void alert(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }
}
