package com.merlin.asset.core.utils;

import java.text.DecimalFormat;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2022.05.11 15:27
 */
public class NumberUtils {

    public static String formatNumber1(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }


}
