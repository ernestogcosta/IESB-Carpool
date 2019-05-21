package br.iesb.iesbcarpool.ui;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import br.iesb.iesbcarpool.R;

public class Cadastro extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtMatricula;
    private Button btnVoltar;
    private Button btnProximo;
    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        eventoClicks();
    }

    private void inicializaComponentes(){
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.idEmail);
        txtSenha = findViewById(R.id.idSenha);
        txtMatricula = findViewById(R.id.idMatricula);
        btnProximo = findViewById(R.id.idBtnProximo);
        btnVoltar = findViewById(R.id.idBtnVoltar);
        btnCadastro = findViewById(R.id.idBtnCadastro);
    }


    private void eventoClicks(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String nome = txtNome.getText().toString().trim();
                String senha = txtSenha.getText().toString().trim();
                String matricula = txtMatricula.getText().toString().trim();

                if(validaCampos(email,nome,senha,matricula)){
                    verificaMatriculaExisteNoBanco(matricula,nome,email,senha);
                }
            }
        });
    }

    private void criarUser(final String email,
                           final String senha,
                           final String nome,
                           final String matricula){
        auth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            alert("Usuário cadastrado com sucesso");
                            criaEGravaUsuario(email,senha,nome,matricula,auth.getUid());
                            Intent i = new Intent(Cadastro.this, GoogleMapsActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            alert("Email ou senha inválidos");
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_LONG).show();
    }
    private void criaEGravaUsuario(String email,String senha,String nome,String matricula, String uid){

        Usuario usuario = new Usuario(nome,email,senha,uid);
        usuario.gravaNovosDados(matricula);
    }

    private void verificaMatriculaExisteNoBanco(final String matricula,
                                                   final String nome,
                                                   final String email,
                                                   final String senha){
        DatabaseReference reference = Conexao.getReference();
        reference.child("Aluno")
                .child(matricula)
                .orderByValue()
                .addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    alert("Matricula " +matricula+" já cadastrada");
                }else{
                    criarUser(email,senha,nome,matricula);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validaCampos(String email,String nome,String senha,String matricula){
        if(email.length() == 0 || nome.length() == 0 || senha.length() == 0|| matricula.length() == 0){
            alert("Todos os campos são obrigatórios");
            return false;
        }
        if(matricula.length() != 10){
            alert("Matricula inválida");
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
