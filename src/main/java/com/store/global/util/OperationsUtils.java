package com.store.global.util;

public class OperationsUtils {
    public static String trimBrackets(String message){
        return message.replaceAll("[\\[\\]]", "");
    }
}
