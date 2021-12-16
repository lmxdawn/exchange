package com.lmxdawn.ws.ws;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 操作 在线用户的 Channel
 */
public class WSSocketHolder {

    private static final String KEY_ID = "id";
    private static final String KEY_WS_ID = "ws_id";

    private static final Map<String, Map<String, Channel>> CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static String put(String id, String oldWsId, Channel channel) {
        Map<String, Channel> stringChannelMap = CHANNEL_MAP.computeIfAbsent(id, k -> new ConcurrentHashMap<>());
        String wsId = oldWsId;
        if (StringUtils.isBlank(oldWsId) || stringChannelMap.containsKey(oldWsId)) {
            wsId = UUID.randomUUID().toString();
            channel.attr(AttributeKey.valueOf(KEY_ID)).set(id);
            channel.attr(AttributeKey.valueOf(KEY_WS_ID)).set(wsId);
            stringChannelMap.put(wsId, channel);
        }
        return wsId;
    }

    public static Map<String, Channel> get(String id) {
        return CHANNEL_MAP.get(id);
    }

    public static Map<String, Map<String, Channel>> getMAP() {
        return CHANNEL_MAP;
    }

    public static String remove(Channel channel) {
        String id = channel.hasAttr(AttributeKey.valueOf(KEY_ID)) ? (String) channel.attr(AttributeKey.valueOf(KEY_ID)).get() : "";
        String wsId = channel.hasAttr(AttributeKey.valueOf(KEY_WS_ID)) ? (String) channel.attr(AttributeKey.valueOf(KEY_WS_ID)).get() : "";
        if (StringUtils.isBlank(id) || StringUtils.isBlank(wsId)) {
            return "";
        }
        Map<String, Channel> stringChannelMap = CHANNEL_MAP.get(id);
        int size = stringChannelMap != null ? stringChannelMap.size() : 0;
        if (size > 0) {
            Channel remove = stringChannelMap.remove(wsId);
            // 如果删除成功，并且大小为 1。当前map为空
            if (remove != null && size == 1) {
                CHANNEL_MAP.remove(id);
            }
            return id;
        }

        return "";
    }

}
