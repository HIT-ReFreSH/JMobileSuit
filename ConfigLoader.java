package com.issue;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
public class ConfigLoader {

        public static appsettings loadConfig(String filePath) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(filePath);
            return objectMapper.readValue(file, appsettings.class);
        }
    }
