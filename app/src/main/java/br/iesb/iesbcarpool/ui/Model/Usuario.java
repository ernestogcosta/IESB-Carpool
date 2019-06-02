package br.iesb.iesbcarpool.ui.Model;

import com.google.firebase.database.DatabaseReference;

public class Usuario {
    String nome;
    String email;
    long matricula;
    LatitudeLongitude latLong;
    boolean logado;
    Sexo sexo;
    IESB iesb;

    public Usuario(String nome, String email, Sexo sexo, IESB iesb, long matricula) {
        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.iesb = iesb;
        this.matricula = matricula;
        this.latLong = new LatitudeLongitude();
    }

    public void gravaNovosDados(String uid) {
        DatabaseReference reference = Conexao.getReference();
        reference.child("Aluno").child(uid).setValue(this);
    }
}
