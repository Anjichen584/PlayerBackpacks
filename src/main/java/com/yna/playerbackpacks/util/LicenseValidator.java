package com.yna.playerbackpacks.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;

public class LicenseValidator {

    private static final String LICENSE_FILE_PATH = "plugins/PlayerBackpacks/license.dat"; // 许可证文件路径
    private static final String SECRET_KEY = "1234567890123456"; // 16 字节密钥，必须与你加密时的密钥一致

    public static boolean validateLicense(String serverIp, String deviceId) {
        try {
            // 读取加密的许可证文件
            Path path = Paths.get(LICENSE_FILE_PATH);
            byte[] encryptedLicense = Files.readAllBytes(path);

            // 解密许可证
            String decryptedLicense = LicenseEncryptor.decrypt(new String(encryptedLicense), SECRET_KEY);

            // 提取 IP 和设备 ID 列表
            String[] ipList = extractFromJson(decryptedLicense, "ips");
            String[] deviceIdList = extractFromJson(decryptedLicense, "deviceIds");

            // 检查服务器 IP 是否在许可证中的 IP 列表中
            if (Arrays.asList(ipList).contains(serverIp) && Arrays.asList(deviceIdList).contains(deviceId)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 从 JSON 格式的字符串中提取出指定字段的数组
    private static String[] extractFromJson(String json, String key) {
        String keyWithQuotes = "\"" + key + "\": ";
        int startIdx = json.indexOf(keyWithQuotes) + keyWithQuotes.length();
        int endIdx = json.indexOf("]", startIdx);

        if (startIdx > 0 && endIdx > 0) {
            String arrayStr = json.substring(startIdx, endIdx + 1);
            arrayStr = arrayStr.replace("[", "").replace("]", "").replace("\"", "").trim();
            return arrayStr.split(", ");
        }

        return new String[0]; // 返回空数组，如果没有找到相应的字段
    }
}
