package com.yna.playerbackpacks.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class LicenseGenerator {

    private static final String SECRET_KEY = "1234567890123456"; // 16 字节的密钥，保持一致

    public static void generateLicense(List<String> ipList, List<String> deviceIdList) {
        try {
            // 创建 License 对象，传入多个 IP 和设备 ID
            License license = new License(ipList, deviceIdList);

            // 序列化 License 对象为 JSON 字符串
            StringBuilder licenseJson = new StringBuilder("{ ");
            licenseJson.append("\"ips\": [");
            for (String ip : ipList) {
                licenseJson.append("\"").append(ip).append("\", ");
            }
            if (!ipList.isEmpty()) {
                licenseJson.delete(licenseJson.length() - 2, licenseJson.length()); // 去掉最后的逗号
            }
            licenseJson.append("], ");

            licenseJson.append("\"deviceIds\": [");
            for (String deviceId : deviceIdList) {
                licenseJson.append("\"").append(deviceId).append("\", ");
            }
            if (!deviceIdList.isEmpty()) {
                licenseJson.delete(licenseJson.length() - 2, licenseJson.length()); // 去掉最后的逗号
            }
            licenseJson.append("] ");
            licenseJson.append("}");

            // 加密 License 内容
            String encryptedLicense = LicenseEncryptor.encrypt(licenseJson.toString(), SECRET_KEY);

            // 保存到文件
            Path licenseFile = Paths.get("plugins/PlayerBackpacks/license.dat");
            Files.write(licenseFile, encryptedLicense.getBytes());

            System.out.println("许可证文件已生成并加密！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 示例：为服务器生成许可证，传入多个 IP 和设备 ID
        List<String> ips = List.of("127.0.0.1", "192.168.1.1"); // 可以根据实际情况添加更多 IP
        List<String> deviceIds = List.of("device-id-1", "device-id-2"); // 可以根据实际情况添加更多设备 ID
        generateLicense(ips, deviceIds);
    }
}
