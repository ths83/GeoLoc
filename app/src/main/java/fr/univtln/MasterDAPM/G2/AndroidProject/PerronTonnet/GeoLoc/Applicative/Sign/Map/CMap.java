package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Applicative.Sign.Map;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;


public class CMap {


    /**
     * methode permettant de calculer la distance entre deux points donnes
     * avec une latitude et une longitude
     * et en utilisant des proprietes geometriques
     * @param startLatLng
     * @param finalLatLng
     * @return
     */
    public static double CalculationByDistance(LatLng startLatLng, LatLng finalLatLng) {
        int Radius=6371;//radius of earth in Km
        double startLat = startLatLng.latitude;
        double startLng = startLatLng.longitude;
        double finalLat = finalLatLng.latitude;
        double finalLng = finalLatLng.longitude;
        double dLat = Math.toRadians(finalLat-startLat);
        double dLon = Math.toRadians(finalLng-startLng);
        double pyth = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(finalLat)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(pyth));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));

        return Radius * c;
    }


}
