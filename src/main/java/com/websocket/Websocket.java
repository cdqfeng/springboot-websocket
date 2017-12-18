package com.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by fengqing on 2017/12/18.
 */
@ServerEndpoint(value = "/ws")
@Component
public class Websocket {

    // 存放客户端websocket对象
    public static CopyOnWriteArraySet<Websocket> websocketSet = new CopyOnWriteArraySet<>();

    // 连接会话
    private Session session;

    /**
     * 开启连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSet.add(this);
        System.out.println("有一个客户端连接");
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        websocketSet.remove(this);
        System.out.println("有一个客户断开连接");
    }

    /**
     * 收到消息
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息：" + message);
        this.session.getBasicRemote().sendText("我是服务器");
    }

    /**
     * 发生错误
     * @param error
     * @param session
     */
    @OnError
    public void onError(Throwable error, Session session) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送文本消息
     * @param message
     * @throws IOException
     */
    public void sendText(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
