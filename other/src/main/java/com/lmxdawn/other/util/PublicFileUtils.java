package com.lmxdawn.other.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共文件管理工具
 */
public class PublicFileUtils {

    public static final String STYLE_THUMB = "style-thumb";

    public static String createUploadUrlThumb(String domain, String filePath, String style) {
        return createUploadUrl(domain, filePath, style) + (!StringUtils.isBlank(style) ? "?x-image-process=style/" + style : "");
    }

    public static String createUploadUrl(String domain, String filePath) {
        return createUploadUrl(domain, filePath, null);
    }

    public static String createUploadUrl(String domain, String filePath, String style) {

        if (filePath == null || filePath.isEmpty()) {
            return "";
        }

        if (filePath.indexOf("http") == 0 || filePath.indexOf("/") == 0) {
            return filePath;
        }

        return domain + "/" + filePath;
    }

}
