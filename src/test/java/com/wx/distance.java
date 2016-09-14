package com.wx;

/**
 * Created by lijing on 2016/9/13.
 */
public class distance {
    public static void main(String[] args){
        Double l=wgs84PointsDistance(113.352425,23.137466);
        System.out.println(l);
    }
    public static Double wgs84PointsDistance(Double y,Double x){
        Integer r = 6378137;
        Double x1 = 113.352425 * Math.PI / 180;
        Double x2 = x * Math.PI / 180;
        Double y1 = 23.137466 * Math.PI / 180;
        Double y2 = y * Math.PI / 180;
        Double dx = Math.abs(x1 - x2);
        Double dy = Math.abs(y1 - y2);
        Double p = Math.pow(Math.sin(dx / 2), 2) + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin(dy / 2), 2);
        Double d= r * 2 * Math.asin(Math.sqrt(p));
        return d;

    }
}
