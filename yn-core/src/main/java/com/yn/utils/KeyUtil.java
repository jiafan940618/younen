package com.yn.utils;

import java.util.Random;

/**
 * Created by Xiang on 2017/7/21.
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间戳 + 6位随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
