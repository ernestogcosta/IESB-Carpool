package br.iesb.iesbcarpool.ui.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Conexao;

public class Teste extends AppCompatActivity {
    private boolean matriculaExistente = false;
    private final String matricula = "1722130042";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        DatabaseReference reference = Conexao.getReference();
        final String matricula = "1722130042";
        reference.child("Aluno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alert(verificaMatriculaExistente(dataSnapshot) + "");

            }
            public boolean verificaMatriculaExistente(DataSnapshot dataSnapshot){
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alert(String s){
        Toast.makeText(Teste.this, s, Toast.LENGTH_LONG).show();

    }
}
