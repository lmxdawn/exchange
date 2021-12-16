package com.lmxdawn.ws.ws;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.ws.constant.MqTopicConstant;
import com.lmxdawn.ws.mq.WsOfflineMq;
import com.lmxdawn.ws.req.WSBaseReq;
import com.lmxdawn.ws.res.WSBaseRes;
import com.lmxdawn.ws.service.UserLoginService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ChannelHandler.Sharable
@Slf4j
@Component
public class WSServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private StreamBridge streamBridge;

    private static WSServerHandler wsServerHandler;

    @PostConstruct
    public void init() {
        wsServerHandler = this;
    }

    /**
     * 取消绑定
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 可能出现业务判断离线后再次触发 channelInactive
        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
        userOffline(ctx);
    }

    /**
     * 心跳检查
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 关闭用户的连接
                userOffline(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 用户下线
     */
    private void userOffline(ChannelHandlerContext ctx) {
        String id = WSSocketHolder.remove(ctx.channel());
        ctx.channel().close();
        if (!StringUtils.isBlank(id)) {
            WsOfflineMq wsOfflineMq = new WsOfflineMq();
            wsOfflineMq.setMemberId(id);
            wsServerHandler.streamBridge.send(MqTopicConstant.WS_OFFLINE_TOPIC, wsOfflineMq);
        }
    }

    /**
     * 读到客户端的内容
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.info("接收消息：{}", msg.text());
        WSBaseReq wsBaseReq = JSON.parseObject(msg.text(), WSBaseReq.class);
        Integer type = wsBaseReq.getType();
        String memberId = wsBaseReq.getMemberId();
        String wsId = wsBaseReq.getWsId();
        String data = wsBaseReq.getData();
        if (type == 0) { // 心跳
            log.info("客户端心跳");
        } else if (type == 1) { // 用户登录
            log.info("用户登录");
            userLogin(ctx, memberId, wsId, data);
        } else if (type == 2) { // 游客登录
            log.info("游客登录");
            touristLogin(ctx, memberId, wsId, data);
        } else if (type == 3) { // 行情推送
            log.info("行情推送");
            marketPush(ctx, data);
        } else { // 委托订单变化通知
            log.info("委托订单变化");
            entrustOrderPush(ctx, data);
        }

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if ("Connection reset by peer".equals(cause.getMessage())) {
            log.error("连接出现问题");
            return;
        }

        log.error(cause.getMessage(), cause);
    }


    /**
     * 游客登录
     */
    private void touristLogin(ChannelHandlerContext ctx, String memberId, String oldWsId, String token) {
        if (StringUtils.isBlank(memberId)) {
            ctx.channel().close();
            return;
        }
        // 加入 map 中
        String wsId = WSSocketHolder.put(memberId, oldWsId, ctx.channel());
        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setWsId(wsId);
        wsBaseRes.setType(1);
        wsBaseRes.setData(memberId);
        String s = JSON.toJSONString(wsBaseRes);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(s));
    }

    /**
     * 用户登录
     */
    private void userLogin(ChannelHandlerContext ctx, String memberId, String oldWsId, String token) {
        if (StringUtils.isBlank(memberId)) {
            ctx.channel().close();
            return;
        }
        Long login = null;
        if (!StringUtils.isBlank(token)) {
            login = wsServerHandler.userLoginService.Login(token);
        }
        if (login == null || login <= 0) {
            log.info("非法登录: {}", token);
            // 登录异常，变成游客登录模式
            touristLogin(ctx, memberId, oldWsId, token);
            return;
        }

        memberId = login.toString();

        // 加入 在线 map 中
        String wsId = WSSocketHolder.put(memberId, oldWsId, ctx.channel());

        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(1);
        wsBaseRes.setWsId(wsId);
        wsBaseRes.setData(memberId);
        String s = JSON.toJSONString(wsBaseRes);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(s));

    }

    /**
     * 行情推送
     */
    private void marketPush(ChannelHandlerContext ctx, String data) {

        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(2);
        wsBaseRes.setData(data);
        String s = JSON.toJSONString(wsBaseRes);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(s));
    }

    /**
     * 委托订单变化
     */
    private void entrustOrderPush(ChannelHandlerContext ctx, String data) {

        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(3);
        wsBaseRes.setData(data);
        String s = JSON.toJSONString(wsBaseRes);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(s));

    }

}
