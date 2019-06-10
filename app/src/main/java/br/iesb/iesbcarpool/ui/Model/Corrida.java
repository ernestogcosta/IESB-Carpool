package br.iesb.iesbcarpool.ui.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Corrida {

    private double latitudePartida, longitudePartida, latitudeDestino, longitudeDestino;
    private Usuario passageiro, motorista;
    private long horaPartida, horaDestino;
    private IESB iesb;

    public Corrida(double latitudePartida, double longitudePartida, double latitudeDestino, double
            longitudeDestino, Date horaPartida, String motoristaUID, String passageiroUID) {
        this.latitudePartida = latitudePartida;
        this.longitudePartida = longitudePartida;
        this.latitudeDestino = latitudeDestino;
        this.longitudeDestino = longitudeDestino;
        this.horaPartida = System.currentTimeMillis();
        this.motorista = getUserByUID(motoristaUID);
        this.passageiro = getUserByUID(passageiroUID);

        if(latitudeDestino == IESB.SUL.getLatitude() && longitudeDestino == IESB.SUL.getLongitude()){
            iesb = IESB.SUL;
        }else if(latitudeDestino == IESB.NORTE.getLatitude() && longitudeDestino == IESB.NORTE.getLongitude()){
            iesb = IESB.NORTE;
        }else if(latitudeDestino == IESB.OESTE.getLatitude() && longitudeDestino == IESB.OESTE.getLongitude()){
            iesb = IESB.OESTE;
        }else{
            iesb = null;
        }

    }

    private Usuario getUserByUID(String motoristaUID) {
        // TODO
        return null;
    }

    public void gravaNovosDados(String uid) {
        DatabaseReference reference = Conexao.getReference();
        reference.child("Corrida").child(uid).setValue(this);
    }

    public LatLng getLatLngPartida(){
        LatLng corridaLatLngPartida = new LatLng(this.latitudePartida, this.longitudePartida);
        return corridaLatLngPartida;
    }

    public LatLng getLatLngDestino(){
        LatLng corridaLatLngDestino = new LatLng(this.latitudeDestino, this.longitudeDestino);
        return corridaLatLngDestino;
    }

    public long getHoraPartida() {
        return horaPartida;
    }

    public long getHoraDestino() {
        return horaDestino;
    }

    public String getNomeMotorista(){
        return motorista.nome;
    }

    public String getNomePassageiro(){
        return passageiro.nome;
    }

    public long getMatriculaMotorista(){
        return motorista.matricula;
    }

    public long getMatriculaPassageiro(){
        return passageiro.matricula;
    }

    public Sexo getSexoMotorista(){
        return motorista.sexo;
    }

    public Sexo getSexoPassageiro(){
        return passageiro.sexo;
    }

    public String getIesb() {
        if(iesb == IESB.SUL){
            return "IESB Sul";
        }else if(iesb == IESB.NORTE){
            return "IESB Norte";
        }else if(iesb == IESB.OESTE){
            return "IESB Oeste";
        }else{
            return null;
        }
    }

    public void setHoraDestino(long horaDestino) {
        this.horaDestino = System.currentTimeMillis();
    }
}
