package com.jfbueno.httpserver.config;

public class AppConfig {
    private static AppConfig appConfig = null;
    private Configuration config = null;

    public AppConfig(Configuration config) {
        config.init();
        this.config = config;
    }

    public static AppConfig getInstance() {
        return appConfig;
    }

    public static void init(Configuration config) {
        appConfig = new AppConfig(config);
    }

    public Configuration getConfig() {
        return this.config;
    }
}