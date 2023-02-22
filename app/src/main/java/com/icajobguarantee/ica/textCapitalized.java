package com.icajobguarantee.ica;

public class textCapitalized {
    public static String changeCapitalized(String textviewText) {
        String[] strArray = textviewText.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builder.append(cap + " ");
        }
        return builder.toString();
    }
}
