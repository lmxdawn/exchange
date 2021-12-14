package com.lmxdawn.ws.ws;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.ws.res.WSBaseRes;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.Map;

@Component
@Slf4j
public class WSServer {

    @Value("${ws.port}")
    private int wsPort;

    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup work = new NioEventLoopGroup();

    /**
     * 启动 ws server
     *
     * @return
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(wsPort))
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new WSServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("启动 ws server 成功");
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        log.info("关闭 ws server 成功");
    }

    /**
     * 全量发送消息
     * @param wsBaseRes 消息
     * @return
     */
    public Boolean full(WSBaseRes wsBaseRes) {

        String s = JSON.toJSONString(wsBaseRes);

        Map<String, Map<String, Channel>> map = WSSocketHolder.getMAP();
        for (Map.Entry<String, Map<String, Channel>> next : map.entrySet()) {
            Map<String, Channel> channels = next.getValue();
            for (Map.Entry<String, Channel> channelEntry : channels.entrySet()) {
                Channel channel = channelEntry.getValue();
                channel.writeAndFlush(new TextWebSocketFrame(s));
            }
        }

        return true;
    }

    /**
     * 发送消息
     * @param memberId 发送给谁
     * @param wsBaseRes 消息
     * @return
     */
    public Boolean sendMsg(String memberId, WSBaseRes wsBaseRes) {
        Map<String, Channel> channels = WSSocketHolder.get(memberId);

        if (null == channels || channels.size() == 0) {
            log.info("用户ID[" + memberId + "]不在线！");
            return false;
        }

        String s = JSON.toJSONString(wsBaseRes);

        for (Map.Entry<String, Channel> channelEntry : channels.entrySet()) {
            Channel channel = channelEntry.getValue();
            channel.writeAndFlush(new TextWebSocketFrame(s));
        }
        return true;
    }

}
