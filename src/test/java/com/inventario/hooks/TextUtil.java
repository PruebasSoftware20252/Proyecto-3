package com.inventario.hooks;


import java.text.Normalizer;

public class TextUtil {
    public static String normalizeAsciiLower(String s) {
        if (s == null) return null;
        String nfd = Normalizer.normalize(s, Normalizer.Form.NFD);
        String noMarks = nfd.replaceAll("\\p{M}+", "");
        return noMarks.toLowerCase();
    }
    }


