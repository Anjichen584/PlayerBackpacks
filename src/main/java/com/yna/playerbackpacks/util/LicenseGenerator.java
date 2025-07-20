package com.yna.playerbackpacks.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class LicenseGenerator {

    private static final String SECRET_KEY = "1234567890123456"; // 16 字节的密钥，保持一致

    public static void generateLicense(String ip, String deviceId) {
        try {
            // 创建 License 对象
            License license = new License(ip, deviceId);

            // 序列化 License 对象为 JSON 字符串
            String licenseJson = "{ \"ip\": \"" + license.getIp() + "\", \"deviceId\": \"" + license.getDeviceId() + "\" }";

            // 加密 License 内容
            String encryptedLicense = LicenseEncryptor.encrypt(licenseJson, SECRET_KEY);

            // 保存到文件
            Path licenseFile = Paths.get("plugins/PlayerBackpacks/license.dat");
            Files.write(licenseFile, encryptedLicense.getBytes());

            System.out.println("许可证文件已生成并加密！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 示例：为服务器生成许可证
        generateLicense("127.0.0.1", "unique-device-id");
    }
}

