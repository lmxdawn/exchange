package com.lmxdawn.other.util;

/**
 * 分页工具类
 */
public class UploadUrlUtils {

    /**
     * 格式化上传url
     * @return
     */
    public static String format(String url, String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            return "";
        }

        if (filePath.indexOf("http") == 0 || filePath.indexOf("/") == 0) {
            return filePath;
        }

        return String.format(url, filePath);

    }

}
