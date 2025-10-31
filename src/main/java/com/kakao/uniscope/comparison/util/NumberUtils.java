package com.kakao.uniscope.comparison.util;

public class NumberUtils {

    public static double roundToOneDecimalPlace(double value) {
        if (Double.isNaN(value) || value <= 0) {
            return 0.0;
        }
        return Math.round(value * 10.0) / 10.0;
    }
}
