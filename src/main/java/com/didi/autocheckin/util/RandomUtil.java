package com.didi.autocheckin.util;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author didi
 */
public class RandomUtil {

    /**
     * 生成主键id
     * @return
     */
    public static String uuId() {
        String uuid = UUID.randomUUID().toString();
        String temp = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
        return temp;
    }

    /**
     * 生成6位数随机英文
     * @return
     */
    public static String enUuId() {
        String str="";
        for(int i=0;i<6;i++){
            //你想生bai成几个字符的，du就把3改成zhi几，dao如果改成１,那就生成一个1653随机字母．
            str= str+(char) (Math.random ()*26+'a');
        }
        return str;
    }

    public static String randomPassword () {
        String str = "";
        for(int i=0;i<6;i++){
            double tmp = Math.random ()*36;
            if (tmp >= 26) {
                str = str + (int)(tmp - 26);
            }else {
                //你想生bai成几个字符的，du就把3改成zhi几，dao如果改成１,那就生成一个1653随机字母．
                str = str + (char) (tmp + 'a');
            }
        }
        return str;
    }

    /**
     * 生成排序编码
     * @return
     */
    public static Long parses() {
        Long time = 0L;
        return time;
    }

}
