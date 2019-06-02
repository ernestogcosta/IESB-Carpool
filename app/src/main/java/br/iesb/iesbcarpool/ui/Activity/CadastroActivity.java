package br.iesb.iesbcarpool.ui.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Conexao;
import br.iesb.iesbcarpool.ui.Model.IESB;
import br.iesb.iesbcarpool.ui.Model.Sexo;
import br.iesb.iesbcarpool.ui.Model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtMatricula;
    private Button btnVoltar;
    private Button btnProximo;
    private Button btnCadastro;
    private Spinner spinerIESB;
    private Spinner spinerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        eventoClicks();
    }
    private void alert(String msg){
        Toast.makeText(CadastroActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    private void inicializaComponentes(){
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.idEmail);
        txtSenha = findViewById(R.id.idSenha);
        txtMatricula = findViewById(R.id.idMatricula);
        btnProximo = findViewById(R.id.idBtnProximo);
        btnVoltar = findViewById(R.id.idBtnVoltar);
        btnCadastro = findViewById(R.id.idBtnCadastro);
        spinerIESB = findViewById(R.id.idSpinnerIESB);
        spinerSexo = findViewById(R.id.idSpinnerSexo);
        ArrayAdapter arrayAdapterIESB = new ArrayAdapter(this,android.R.layout.simple_spinner_item, IESB.values());
        arrayAdapterIESB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerIESB.setAdapter(arrayAdapterIESB);

        ArrayAdapter arrayAdapterSexo = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Sexo.values());
        arrayAdapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerSexo.setAdapter(arrayAdapterSexo);
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
                IESB iesb = (IESB) spinerIESB.getSelectedItem();
                Sexo sexo = (Sexo) spinerSexo.getSelectedItem();

                if(validaCampos(email,nome,senha,matricula)){
                    gravaUser(email,senha,nome,matricula,iesb,sexo);
                }
            }
        });
    }

    private void gravaUser(final String email,
                           final String senha,
                           final String nome,
                           final String matricula,
                           final IESB iesb,
                           final Sexo sexo){
        DatabaseReference reference = Conexao.getReference();
        reference.child("Aluno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!verificaMatricula(dataSnapshot)){
                    Usuario usuario = criaUser();
                    registraUsuarioEmailSenha(usuario);
                }else{
                    alert("Matricula já cadastrada");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public boolean verificaMatricula(DataSnapshot dataSnapshot){
                HashMap value = (HashMap)dataSnapshot.getValue();
                List<Object> arrayKeySet = Arrays.asList(value.keySet().toArray());
                boolean matriculaExistente = false;
                for(Object key: arrayKeySet){
                    HashMap hash = (HashMap) value.get(key);
                    String matriculaHash = hash.get("matricula").toString();
                    if(matricula.equals(matriculaHash)){
                        matriculaExistente = true;
                        break;
                    }

                }
                return matriculaExistente;
            }


            public Usuario criaUser(){
                Usuario newUser = new Usuario(nome,email,sexo,iesb,Long.parseLong(matricula));
                return newUser;
            }

            public void registraUsuarioEmailSenha(final Usuario user){
                final FirebaseAuth auth = Conexao.getFirebaseAuth();
                Task<AuthResult> task = auth.createUserWithEmailAndPassword(email, senha);
                task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String uid = auth.getUid();
                            user.gravaNovosDados(uid);
                            alert("Cadastrado com sucesso");
                        }else{
                            alert("Email já cadastrado");
                        }
                    }
                });
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
