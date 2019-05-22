package br.iesb.iesbcarpool.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {
    String nome;
    String email;
    String senha;
    String uid;
    Sexo sexo;
    IESB iesb;

    public Usuario(String nome, String email, String senha, Sexo sexo, IESB iesb, String uid) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uid = uid;
        this.sexo = sexo;
        this.iesb = iesb;
    }

    public void gravaNovosDados(String id) {
        DatabaseReference reference = Conexao.getReference();
        reference.child("Aluno").child(id).setValue(this);
    }
}
