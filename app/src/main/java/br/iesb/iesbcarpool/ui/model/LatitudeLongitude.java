package br.iesb.iesbcarpool.ui.model;

import com.google.android.gms.maps.model.LatLng;

class LatitudeLongitude {

    public double latitude;
    public double longitude;
    public boolean ativo;
    LatitudeLongitude(){

    }
    LatitudeLongitude(double latitude, double longitude, boolean ativo){
        this.latitude = latitude;
        this.longitude = longitude;
        this.ativo = false;
    }

    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }

    public LatLng getLatLng(){
        return new LatLng(this.latitude, this.longitude);
    }

    public void setLat(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }




}
