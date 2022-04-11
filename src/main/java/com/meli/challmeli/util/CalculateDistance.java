package com.meli.challmeli.util;

import static com.meli.challmeli.util.DecimalsUtil.formatDecimals;

public class CalculateDistance {

    private static final double latitudeBuenosAires = -34.687400817871094;
    private static final double longitudeBuenosAires = -58.56330108642578;

    public static double distance(double lat, double lon) {
        if ((latitudeBuenosAires == lat) && (longitudeBuenosAires == lon)) {
            return 0;
        } else {
            double radioTierra = 6371;//en kilómetros
            double dLat = Math.toRadians(lat - latitudeBuenosAires);
            double dLng = Math.toRadians(lon - longitudeBuenosAires);
            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);
            double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(latitudeBuenosAires)) * Math.cos(Math.toRadians(lat));
            double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
           return formatDecimals(radioTierra * va2,3);
        }
    }
}
