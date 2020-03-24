package com.example.huaweiot.api.Beans;

public class HomeData {

    /**
     * code : 200
     * data : {"humidity":"85","smoke":"850","temperature":"30","time":"2020-03-19 13:40:50"}
     * message : query success
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * humidity : 85
         * smoke : 850
         * temperature : 30
         * time : 2020-03-19 13:40:50
         */

        private String humidity;
        private String smoke;
        private String temperature;
        private String time;

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getSmoke() {
            return smoke;
        }

        public void setSmoke(String smoke) {
            this.smoke = smoke;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
