package com.example.huaweiot.api.Beans;

import java.util.Map;

public class CommandBody {

    /**
     * method : home_mode
     * paras : {}
     */

    private String method;
    private Map<String, String> paras;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParas() {
        return paras;
    }

    public void setParas(Map<String, String> paras) {
        this.paras = paras;
    }


}
