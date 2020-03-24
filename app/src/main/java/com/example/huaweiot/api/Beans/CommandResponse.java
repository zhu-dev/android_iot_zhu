package com.example.huaweiot.api.Beans;

public class CommandResponse {

    /**
     * code : 200
     * data : {"appId":"WHkdwPLjoWB7iFCOX_X_c65AUtka","commandId":"c141654910184508b052636439f586bd","deviceId":"ce598cf9-942e-4fd2-814e-f4ca3659632a","method":"home_mode","paras":{"open":"O"},"status":"SENT"}
     * message : success
     * status : 201
     */

    private int code;
    private DataBean data;
    private String message;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * appId : WHkdwPLjoWB7iFCOX_X_c65AUtka
         * commandId : c141654910184508b052636439f586bd
         * deviceId : ce598cf9-942e-4fd2-814e-f4ca3659632a
         * method : home_mode
         * paras : {"open":"O"}
         * status : SENT
         */

        private String appId;
        private String commandId;
        private String deviceId;
        private String method;
        private ParasBean paras;
        private String status;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCommandId() {
            return commandId;
        }

        public void setCommandId(String commandId) {
            this.commandId = commandId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public ParasBean getParas() {
            return paras;
        }

        public void setParas(ParasBean paras) {
            this.paras = paras;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class ParasBean {
            /**
             * open : O
             */

            private String open;

            public String getOpen() {
                return open;
            }

            public void setOpen(String open) {
                this.open = open;
            }
        }
    }
}
