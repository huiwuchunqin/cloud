package com.baizhitong.resource.manage.furtherEducation.action;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketTest")
public class WebSocket {
   public  static Session session=null;
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }
    
    @OnMessage
    public void onMessage(String message) {
    System.out.println(message);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
}
