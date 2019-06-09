package br.iesb.iesbcarpool.ui.Model;

import com.google.android.gms.maps.model.LatLng;

public class Corrida {

    double latitude_partida;
    double longitude_partida;

    double latitude_destino;
    double longitude_destino;

    Usuario companheiro;

    public Corrida(double latitude_partida, double longitude_partida, double latitude_destino, double longitude_destino, Usuario companheiro) {
        this.latitude_partida = latitude_partida;
        this.longitude_partida = longitude_partida;
        this.latitude_destino = latitude_destino;
        this.longitude_destino = longitude_destino;
        this.companheiro = companheiro;
    }

    public LatLng getLatitudeLongitude(){
    return null;

    }
}
