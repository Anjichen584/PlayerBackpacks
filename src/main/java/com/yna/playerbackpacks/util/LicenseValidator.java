package com.yna.playerbackpacks.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class LicenseValidator {

    private static final String LICENSE_FILE_PATH = "plugins/PlayerBackpacks/license.dat"; // 许可证文件路径
    private static final String SECRET_KEY = "1234567890123456"; // 16 字节密钥，必须与你加密时的密钥一致

    public static boolean validateLicense() {
        try {
            // 读取加密的许可证文件
            Path path = Paths.get(LICENSE_FILE_PATH);
            byte[] encryptedLicense = Files.readAllBytes(path);

            // 解密许可证
            String decryptedLicense = LicenseEncryptor.decrypt(new String(encryptedLicense), SECRET_KEY);

            // 在解密后的许可证字符串中，进行 IP 和 deviceId 的检查
            if (decryptedLicense.contains("127.0.0.1") && decryptedLicense.contains("unique-device-id")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
