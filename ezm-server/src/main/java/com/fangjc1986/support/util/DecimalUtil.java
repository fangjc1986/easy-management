package com.fangjc1986.support.util;

import java.math.BigDecimal;

public class DecimalUtil {

    /**
     * 大于 0
     *
     * @param decimal
     * @return
     */
    public static boolean ltZero(BigDecimal decimal) {
        return decimal != null && decimal.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 小于0
     *
     * @param decimal
     * @return
     */
    public static boolean gtZero(BigDecimal decimal) {
        return decimal != null && decimal.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 等于 0
     *
     * @param decimal
     * @return
     */
    public static boolean eqZero(BigDecimal decimal) {
        return decimal == null || decimal.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 小于等于 0
     *
     * @param decimal
     * @return
     */
    public static boolean leZero(BigDecimal decimal) {
        return ltZero(decimal) || eqZero(decimal);
    }

    /**
     * 大于等于 0
     *
     * @param decimal
     * @return
     */
    public static boolean geZero(BigDecimal decimal) {
        return gtZero(decimal) || eqZero(decimal);
    }

}
