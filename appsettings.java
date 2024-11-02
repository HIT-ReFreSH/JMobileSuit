package com.issue;
import com.fasterxml.jackson.annotation.JsonProperty;
public class appsettings {

        @JsonProperty("setting1")
        private String setting1;

        @JsonProperty("setting2")
        private int setting2;

        // Getters and Setters
        public String getSetting1() {
            return setting1;
        }

        public void setSetting1(String setting1) {
            this.setting1 = setting1;
        }

        public int getSetting2() {
            return setting2;
        }

        public void setSetting2(int setting2) {
            this.setting2 = setting2;
        }
    }
