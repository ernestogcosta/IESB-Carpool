package br.iesb.iesbcarpool.ui.Model;

import com.google.firebase.database.DatabaseReference;

public class Usuario {
    public String nome;
    public String email;
    public long matricula;
    public LatitudeLongitude latLong;
    public boolean logado;
    public Sexo sexo;
    public IESB iesb;

    public Usuario(String nome, String email, Sexo sexo, IESB iesb, long matricula) {
        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.iesb = iesb;
        this.matricula = matricula;
        this.latLong = new LatitudeLongitude();
    }

    public Usuario(String nome, Sexo sexo, IESB iesb, long matricula) {
        this.nome = nome;
        this.sexo = sexo;
        this.iesb = iesb;
        this.matricula = matricula;
    }

    public void gravaNovosDados(String uid) {
        DatabaseReference reference = Conexao.getReference();
        reference.child("Alunos").child(uid).setValue(this);
    }

    public String toString(){
        return nome + " " + iesb.toString() + " " + sexo.toString() + " " + matricula;
    }
}
