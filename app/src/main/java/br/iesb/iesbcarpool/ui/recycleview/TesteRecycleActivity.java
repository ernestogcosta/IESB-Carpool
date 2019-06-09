package br.iesb.iesbcarpool.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Conexao;
import br.iesb.iesbcarpool.ui.Model.IESB;
import br.iesb.iesbcarpool.ui.Model.Sexo;
import br.iesb.iesbcarpool.ui.Model.Usuario;

public class TesteRecycleActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private AlunosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_recycle);
        mRecyclerView = findViewById(R.id.recycler_view_layour_recycler);
        //setupRecycle();
        lerDados(mRecyclerView, mAdapter);
    }

    private void setupRecycle(){
        Usuario usuario = new Usuario("Carlitos", Sexo.Masculino, IESB.SUL, 1722130042);
        ArrayList<Usuario> listUsuario = new ArrayList<Usuario>();
        listUsuario.add(usuario);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AlunosAdapter(listUsuario);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void lerDados(final RecyclerView mRecyclerView, final AlunosAdapter mAdapter){
        DatabaseReference reference = Conexao.getReference();
        reference.child("Alunos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> dados = (HashMap<String, String>) dataSnapshot.child("IdmftgDgK3PsLMysaZpGT6n92Wb2").getValue();
                criaUsuario(dados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            public Usuario criaUsuario(HashMap<String, String> dado){
                String nome = dado.get("nome");
                Object matricula = dado.get("matricula");
                IESB iesb = IESB.valueOf(dado.get("iesb"));
                Sexo sexo = Sexo.valueOf(dado.get("sexo"));
                long matriculaLong = Long.parseLong(matricula.toString());
                Usuario usuario = new Usuario(nome, sexo, iesb, matriculaLong);
                return null;
            }
        });
    }
    public void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
