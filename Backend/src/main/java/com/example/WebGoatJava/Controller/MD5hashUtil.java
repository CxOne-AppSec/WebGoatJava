package com.example.WebGoatJava.Controller;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Component
public class MD5hashUtil {
    // Método para obtener el hash MD5 de una cadena
    public static String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para verificar si una contraseña coincide con su hash MD5
    public static boolean verifyPassword(String password, String hashedPassword) {
        String inputHash = getMD5Hash(password);
        return inputHash.equals(hashedPassword);
    }
}
