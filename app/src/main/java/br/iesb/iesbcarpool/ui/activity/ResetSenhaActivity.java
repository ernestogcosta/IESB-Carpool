package br.iesb.iesbcarpool.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.model.Conexao;

public class ResetSenhaActivity extends AppCompatActivity {

    private TextView txtEmail;
    private Button btnResetSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        inicalizaComponentes();
        eventoClick();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    private void inicalizaComponentes(){
        txtEmail = findViewById(R.id.idEmailResetSenha);
        btnResetSenha = findViewById(R.id.idBtnResetSenha);
    }
    private void eventoClick(){
        btnResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                if(validaCampos(email)){
                    resetSenha(email);
                }else{
                    alert("Email é obrigatório");
                }

            }
        });
    }

    private void resetSenha(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetSenhaActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            alert("Um E-mail foi enviado para resetar sua senha");
                            finish();
                        }else{
                            alert("E-mail inválido");
                        }
                    }
                });
    }

    private boolean validaCampos(String email){
        if(email.length() == 0){
            return false;
        }
        return true;
    }
    private void alert(String msg){
        Toast.makeText(ResetSenhaActivity.this,msg,Toast.LENGTH_LONG).show();
    }

}
