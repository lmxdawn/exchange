package com.lmxdawn.ws.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;


public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //15 秒客户端没有向服务器发送心跳则关闭连接
        pipeline.addLast(new IdleStateHandler(15, 0, 0));
        // HTTP请求的解码和编码
        pipeline.addLast(new HttpServerCodec());
        // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
        // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
        pipeline.addLast(new ChunkedWriteHandler());
        // WebSocket数据压缩
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // 协议包长度限制
        pipeline.addLast(new WebSocketServerProtocolHandler("/wsroute", null, true));

        // 自定义handler，处理业务逻辑
        pipeline.addLast(new WSServerHandler());

    }
}