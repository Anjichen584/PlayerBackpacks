package com.yna.playerbackpacks.util;

import java.util.List;

public class License {
    private List<String> ip;          // 支持多个 IP 地址
    private List<String> deviceId;    // 支持多个设备 ID

    public License(List<String> ip, List<String> deviceId) {
        this.ip = ip;
        this.deviceId = deviceId;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }
}
