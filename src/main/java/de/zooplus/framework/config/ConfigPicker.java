package de.zooplus.framework.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigPicker {
    private static ConfigPicker configPicker = null;
    private static Map<String, String> configuration;

    private ConfigPicker() {
        String environment = System.getProperty("env");

        if (environment == null) {
            environment = "production";
        }

        String fileName = null;
        if (environment.equalsIgnoreCase("production")) {
            fileName = "src/main/resources/configs/prod-config.json";
        } else if (environment.equalsIgnoreCase("qa")) {
            fileName = "src/main/resources/configs/qa-config.json";
        } else {
            fileName = "src/main/resources/configs/prod-config.json";
        }

        File file = new File(fileName);
        try {
            configuration = new ObjectMapper().readValue(file, HashMap.class);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigPicker getInstance() {
        if(configPicker == null) {
            return new ConfigPicker();
        }
        return configPicker;
    }

    public static Map<String, String> getConfiguration() {
        return configuration;
    }
}
