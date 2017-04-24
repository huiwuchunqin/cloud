package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="sys_message")
@Entity
public class SysMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String messageId;
    private String messageType;
    private Timestamp createTime;
    private String creatorIP;
    private String createPgm;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public String getCreatorIP() {
        return creatorIP;
    }
    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
    }
    public String getCreatePgm() {
        return createPgm;
    }
    public void setCreatePgm(String createPgm) {
        this.createPgm = createPgm;
    }
    
}
