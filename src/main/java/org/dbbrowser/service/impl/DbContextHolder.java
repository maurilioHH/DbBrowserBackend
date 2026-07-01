package org.dbbrowser.service.impl;

public class DbContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setCurrentDb(String dbKey) {
        CONTEXT.set(dbKey);
    }

    public static String getCurrentDb() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}