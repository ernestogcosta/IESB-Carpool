package br.iesb.iesbcarpool.ui.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import br.iesb.iesbcarpool.R;

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
