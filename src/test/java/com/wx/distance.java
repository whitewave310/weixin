package com.wx;

import com.wx.model.Location;

/**
 * Created by lijing on 2016/9/13.
 */
public class distance {
    public static void main(String[] args){
        //公司
        Location myLocation=new Location();
        myLocation.setLon(112.556534);
        myLocation.setLat(37.864864);
        //太原家
        Location userLocation=new Location();
        userLocation.setLon(112.545181);
        userLocation.setLat(37.864971);

        //祁县家
        Location userLocation1=new Location();
        userLocation1.setLon(112.32869);
        userLocation1.setLat(37.361698);
        int l=wgs84PointsDistance(myLocation,userLocation);
        int ll=wgs84Distance(myLocation,userLocation);
        System.out.println(l);
        System.out.println(ll);
    }
    public static int wgs84PointsDistance(Location myLocation,Location userLocation){
        Integer r = 6378137;
        double x1 = myLocation.getLon() * Math.PI / 180;
        double x2 = userLocation.getLon() * Math.PI / 180;
        double y1 = myLocation.getLat() * Math.PI / 180;
        double y2 = userLocation.getLat() * Math.PI / 180;
        double dx = Math.abs(x1 - x2);
        double dy = Math.abs(y1 - y2);
        double p = Math.pow(Math.sin(dx / 2), 2) + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin(dy / 2), 2);
        double d= r * 2 * Math.asin(Math.sqrt(p));
        Integer i=(int)d;
        return i;

    }

    public static int wgs84Distance(Location myLocation,Location userLocation){
        double lon1 = myLocation.getLon();
        double lat1 = myLocation.getLat();
        double lon2 = userLocation.getLon();
        double lat2 = userLocation.getLat();
        int a = 6378137;
        double b = 6356752.3142;
        double f = 1 / 298.257223563;
        double L=Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);
        double lambda = L;
        double lambdaP;
        int iterLimit = 100;
        double cosSqAlpha;
        double sinSigma;
        double cos2SigmaM;
        double cosSigma;
        double sigma;
        do {
            double sinLambda = Math.sin(lambda);
            double cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if(sinSigma == 0)
                return 0;
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)){
                cos2SigmaM = 0;
            }
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda-lambdaP) > (1e-12) && --iterLimit>0);
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        int s = (int) (b * A * (sigma - deltaSigma));
        return s;
    }
}
