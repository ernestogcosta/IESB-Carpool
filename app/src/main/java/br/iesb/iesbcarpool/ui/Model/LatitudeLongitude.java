package br.iesb.iesbcarpool.ui.Model;

class LatitudeLongitude {

    double latitude;
    double longitude;
    boolean ativo;
    LatitudeLongitude(){

    }
    LatitudeLongitude(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.ativo = false;
    }


}
