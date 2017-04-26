package com.br.partiufutebol.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by Rafael on 25/04/2017.
 */
public class GeoLocalizacaoUtil {

    private Context context;

    public GeoLocalizacaoUtil (Context context){
        this.context  = context;
    }





    public Address buscarEndereco(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        Address endereco = null;
        List<Address> enderecos;

        geocoder = new Geocoder(context);

        enderecos = geocoder.getFromLocation(latitude, longitude, 1);
        if (enderecos.size() > 0) {
            endereco = enderecos.get(0);
        }

        return endereco;

    }
}
