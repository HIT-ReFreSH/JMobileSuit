package com.issue;

import picocli.CommandLine;

import java.io.IOException;


@CommandLine.Command(name = "myapp", description = "My command line application")
public class MyApp implements Runnable {

    @CommandLine.Option(names = {"-c", "--config"}, description = "Path to the configuration file", required = true)
    private String configFilePath;


        private appsettings appSettings;

        public static void main(String[] args) {
            CommandLine.run(new MyApp(), args);
        }

        @Override
        public void run() {
            try {
                appSettings = ConfigLoader.loadConfig(configFilePath);
                // Now you can use appSettings in your application
                System.out.println("Setting1: " + appSettings.getSetting1());
                System.out.println("Setting2: " + appSettings.getSetting2());
            } catch (IOException e) {
                System.err.println("Error loading configuration file: " + e.getMessage());
                System.exit(1);
            }
        }
    }
