package com.gamecat.baselib.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtil {

    public static String md5(File file) {
        MessageDigest messageDigest = null;
        DigestInputStream digestInputStream = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(
                    new FileInputStream(file), messageDigest);
            while (true) {
                if (digestInputStream.read() == -1)
                    break;
            }
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtils.closeQuietly(digestInputStream);
        }
        return null;
    }

    public static String md5(String src) {
        if (src == null) {
            return null;
        }
        String ret = null;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(getBytes(src));
            ret = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String sha1(String data) {
        if (data == null)
            return null;
        String ret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(getBytes(data));
            ret = bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String convertTime(long time, String format) {
        if (format == null) {
            format = "yyyy-MM-dd_HHmmss";
        }
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(time);
        return df.format(date);
    }

    /**
     * Check if the specified string is empty. If an string contains any
     * character other than ' '(blank space), it is NOT empty
     *
     * @param s
     * @return
     */
    public static boolean isEmptyString(String s) {
        if (s == null || s.equals("")) {
            return true;
        }
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (s.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static byte[] getBytes(String s) {
        if (s == null)
            return null;
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fromBytes(byte[] data) {
        String ret = null;
        try {
            if (data != null)
                ret = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
