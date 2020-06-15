package com.gamecat.baselib.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : 字符串相关工具类
 * </pre>
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param string 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param string 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String string) {
        return (string == null || string.trim().length() == 0);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param string 待转字符串
     * @return string为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String string) {
        return string == null ? "" : string;
    }

    /**
     * 返回字符串长度
     *
     * @param string 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence string) {
        return string == null ? 0 : string.length();
    }

    /**
     * 首字母大写
     *
     * @param string 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String string) {
        if (isEmpty(string) || !Character.isLowerCase(string.charAt(0))) {
            return string;
        }
        return String.valueOf((char) (string.charAt(0) - 32)) + string.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param string 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String string) {
        if (isEmpty(string) || !Character.isUpperCase(string.charAt(0))) {
            return string;
        }
        return String.valueOf((char) (string.charAt(0) + 32)) + string.substring(1);
    }

    /**
     * 转化为半角字符
     *
     * @param string 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String string) {
        if (isEmpty(string)) {
            return string;
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param string 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String string) {
        if (isEmpty(string)) {
            return string;
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[1,8,9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /*
     * 身份证
     * */
    public static boolean isIDCard(String idCard) {
        String regex = "^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\\d{4}(([1][9]\\d{2})|([2]\\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\\d{3}[0-9xX]$";//身份证
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /*
     * 护照
     * */
    public static boolean isPassport(String passport) {
        String regex = "^1[45][0-9]{7}$|(^[P|p|S|s]\\d{7}$)|(^[S|s|G|g|E|e]\\d{8}$)|(^[Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\\d{8}$)|(^[H|h|M|m]\\d{8,10}$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passport);
        return m.matches();
    }

    /*
    港澳通行证
    * */
    public static boolean isHMCard(String str) {
        String regex = "^[HMhm]{1}([0-9]{10}|[0-9]{8})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // 回乡证
    public static boolean checkReturnHome(String str) {
        return false;
    }

    //台湾通行证验证
    public static boolean checkTaiwan() {
        String regex = "^[a-zA-Z][0-9]{9}$";
        return false;
    }

    // 台胞证
    public static boolean checkIdTaiwan(String str) {
        String regex = "^([0-9]{8}|[0-9]{10})$";
        return false;
    }
}