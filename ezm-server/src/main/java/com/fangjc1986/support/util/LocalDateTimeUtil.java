package com.fangjc1986.support.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeUtil {

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 获取当天结束
     *
     * @return
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 获取本周开始时间
     *
     * @return
     */
    public static LocalDateTime weekStart() {
        return LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.MIN);
    }

}
