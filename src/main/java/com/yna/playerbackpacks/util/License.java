package com.yna.playerbackpacks.util;

public class License {
    private String ip;
    private String deviceId;

    public License(String ip, String deviceId) {
        this.ip = ip;
        this.deviceId = deviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

