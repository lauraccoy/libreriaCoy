package com.libreriacoy.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        // Cambia aquí la contraseña que quieras hashear
        String raw = "Emma-060304";

        String hash = enc.encode(raw);
        System.out.println("RAW  = " + raw);
        System.out.println("HASH = " + hash);
    }
}
