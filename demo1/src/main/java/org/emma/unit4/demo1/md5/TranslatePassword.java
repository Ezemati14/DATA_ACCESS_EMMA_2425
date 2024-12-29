package org.emma.unit4.demo1.md5;

public class TranslatePassword {
    public static String trasPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("ERROR GENERATING MD5", e);
        }
    }
}
