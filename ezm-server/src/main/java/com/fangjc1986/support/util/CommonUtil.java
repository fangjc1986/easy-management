package com.fangjc1986.support.util;

import java.util.Random;

public class CommonUtil {

    /**
     * 生成字母数字
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 随机数
     *
     * @return
     */
    public static String getNumberRandom(int min, int max) {
        int ran2 = (int) (Math.random() * (max - min) + min);
        return ran2 + "";
    }

}
