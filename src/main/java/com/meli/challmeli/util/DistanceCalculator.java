package com.meli.challmeli.util;

public class DistanceCalculator {

    private static final double latitudeBA = -34.69;
    private static final double longitudeBA = -58.56;

    public static double distance(double lat, double lon) {
        if ((latitudeBA == lat) && (longitudeBA == lon)) {
            return 0;
        } else {
            //double radioTierra = 3958.75;//en millas
            double radioTierra = 6371;//en kilómetros
            double dLat = Math.toRadians(lat - latitudeBA);
            double dLng = Math.toRadians(lon - longitudeBA);
            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);
            double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(latitudeBA)) * Math.cos(Math.toRadians(lat));
            double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
            double distancia = radioTierra * va2;
            System.out.println(distancia);
               return distancia;
        }
    }
}
