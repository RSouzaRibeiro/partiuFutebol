package com.br.partiufutebol.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by suporte on 24/10/2016.
 */
public class Util {

    public String formartDataAtual() {
        String data_completa;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        data_completa = dateFormat.format(data_atual);
        return data_completa;
    }

    public Date getHoraAtual() {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date horaAtual = cal.getTime();

        String hora_atual = dateFormat_hora.format(horaAtual);

        Log.i("hora_atual", horaAtual.toString());

        return data;
    }

    public Date formatHora(String hora) {

        try {

            SimpleDateFormat formatoData = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


            return formatoData.parse(hora);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Date stringToDate(String aDate) {

        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
        Date stringDate = simpledateformat.parse(aDate, pos);

        return stringDate;

    }

    public void verificaPermissoes(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        } else
        {
            //a permissão já está ativada }
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        } else
        {
            //a permissão já está ativada }
        }





    }
}
